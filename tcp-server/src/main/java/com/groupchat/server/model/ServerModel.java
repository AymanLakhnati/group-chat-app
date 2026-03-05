package com.groupchat.server.model;

import com.groupchat.server.config.ServerConfig;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ServerModel {
    private final ObservableList<String> usernames = FXCollections.observableArrayList();
    private final ObservableList<String> logMessages = FXCollections.observableArrayList();
    private final List<ClientSession> sessions = new CopyOnWriteArrayList<>();
    private ServerSocket serverSocket;
    private volatile boolean running;
    private final ServerConfig config;
    private int guestCount;
    private int actualPort = -1;

    public ServerModel(ServerConfig config) {
        this.config = config;
    }

    public ObservableList<String> getUsernames() {
        return usernames;
    }

    public ObservableList<String> getLogMessages() {
        return logMessages;
    }

    public int getActualPort() {
        return actualPort;
    }

    private static final DateTimeFormatter LOG_TIME = DateTimeFormatter.ofPattern("HH:mm:ss");

    public void log(String message) {
        String line = "[" + LocalDateTime.now().format(LOG_TIME) + "] " + message;
        Platform.runLater(() -> logMessages.add(line));
    }

    public void start() throws IOException {
        config.load();
        int preferred = config.getPort();
        int maxTries = 10;
        for (int i = 0; i < maxTries; i++) {
            int port = preferred + i;
            if (port > Protocol.MAX_PORT) break;
            try {
                serverSocket = new ServerSocket(port);
                break;
            } catch (BindException e) {
                if (i == maxTries - 1) throw new IOException("Ports " + preferred + " to " + (preferred + i) + " are in use. Close the other server or free a port.");
                serverSocket = null;
            }
        }
        if (serverSocket == null) throw new IOException("Could not bind to any port");
        serverSocket.setSoTimeout(1000);
        running = true;
        actualPort = serverSocket.getLocalPort();
        log("Server Started on port " + actualPort + (actualPort != preferred ? " (port " + preferred + " was in use)" : ""));
        log("Waiting for Client");
        new Thread(this::acceptLoop, "server-accept").start();
    }

    private void acceptLoop() {
        while (running && serverSocket != null && !serverSocket.isClosed()) {
            try {
                Socket clientSocket = serverSocket.accept();
                new Thread(() -> handleClient(clientSocket), "client-" + clientSocket.getRemoteSocketAddress()).start();
            } catch (SocketTimeoutException e) {
                continue;
            } catch (IOException e) {
                if (running) log("Accept error: " + e.getMessage());
            }
        }
    }

    private void handleClient(Socket socket) {
        String username = null;
        try {
            ClientHandler handler = new ClientHandler(socket, this);
            String rawName = handler.readUsername();
            if (rawName == null || rawName.isBlank()) {
                handler.send(Protocol.READ_ONLY);
                username = "Guest_" + (++guestCount);
            } else {
                username = rawName.trim();
                if (username.length() > Protocol.MAX_USERNAME_LENGTH)
                    username = username.substring(0, Protocol.MAX_USERNAME_LENGTH);
                int n = 1;
                String base = username;
                while (sessionHasUsername(username)) {
                    username = base + " (" + (++n) + ")";
                }
            }
            handler.send(Protocol.OK);
            log("Welcome " + username);
            ClientSession session = new ClientSession(username, socket, handler.getWriter());
            sessions.add(session);
            final String nameToAdd = username;
            Platform.runLater(() -> usernames.add(nameToAdd));
            broadcastSystem(username + " joined");
            handler.run(username);
        } catch (IOException e) {
            log("Client error: " + e.getMessage());
        } finally {
            if (username != null) removeClient(username);
            try {
                socket.close();
            } catch (IOException ignored) {}
        }
    }

    private boolean sessionHasUsername(String name) {
        for (ClientSession s : sessions) {
            if (s.getUsername().equals(name)) return true;
        }
        return false;
    }

    void removeClient(String username) {
        boolean removed = sessions.removeIf(s -> s.getUsername().equals(username));
        Platform.runLater(() -> usernames.remove(username));
        if (removed) broadcastSystem(username + " left");
    }

    public void broadcast(String fromUsername, String message) {
        String formatted = String.format("[%s] %s: %s",
                LocalDateTime.now().format(LOG_TIME), fromUsername, message);
        for (ClientSession s : sessions) {
            try {
                s.getOut().println(formatted);
                s.getOut().flush();
            } catch (Exception ignored) {}
        }
    }

    private void broadcastSystem(String text) {
        String formatted = "[System] " + text;
        for (ClientSession s : sessions) {
            try {
                s.getOut().println(formatted);
                s.getOut().flush();
            } catch (Exception ignored) {}
        }
    }

    public void sendUserList(String toUsername) {
        StringBuilder sb = new StringBuilder(Protocol.USERS_PREFIX);
        for (int i = 0; i < sessions.size(); i++) {
            if (i > 0) sb.append(",");
            sb.append(sessions.get(i).getUsername());
        }
        String list = sb.toString();
        for (ClientSession s : sessions) {
            if (s.getUsername().equals(toUsername)) {
                s.getOut().println(list);
                s.getOut().flush();
                break;
            }
        }
    }

    public void stop() {
        running = false;
        for (ClientSession s : sessions) {
            try {
                s.getSocket().close();
            } catch (IOException ignored) {}
        }
        sessions.clear();
        Platform.runLater(usernames::clear);
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException ignored) {}
            serverSocket = null;
        }
    }
}
