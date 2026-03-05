package com.groupchat.client.model;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientModel {
    private final ObservableList<String> messages = FXCollections.observableArrayList();
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private volatile boolean connected;
    private volatile boolean readOnly;
    private String username;
    private Runnable onDisconnect;

    public ObservableList<String> getMessages() {
        return messages;
    }

    public boolean isConnected() {
        return connected;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public String getUsername() {
        return username;
    }

    public void setOnDisconnect(Runnable r) {
        onDisconnect = r;
    }

    public boolean connect(String host, int port, String name) {
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(host, port), Protocol.CONNECT_TIMEOUT_MS);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String sendName = (name != null && !name.isBlank()) ? name.trim() : "";
            if (sendName.length() > Protocol.MAX_USERNAME_LENGTH)
                sendName = sendName.substring(0, Protocol.MAX_USERNAME_LENGTH);
            out.println(sendName);
            String response = in.readLine();
            if (response == null) {
                disconnect();
                return false;
            }
            readOnly = Protocol.READ_ONLY.equals(response);
            username = sendName.isEmpty() ? "Guest" : sendName;
            connected = true;
            Thread t = new Thread(this::readLoop, "client-read");
            t.setDaemon(true);
            t.start();
            return true;
        } catch (IOException e) {
            String msg = e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName();
            if (msg.contains("Connection refused"))
                msg = "Server not reachable at " + host + ":" + port + ". Is the server running?";
            else if (msg.contains("timed out"))
                msg = "Connection timed out. Check host and port.";
            final String display = msg;
            Platform.runLater(() -> messages.add("Connection failed: " + display));
            return false;
        }
    }

    private void readLoop() {
        try {
            String line;
            while (connected && (line = in.readLine()) != null) {
                final String msg = line.startsWith(Protocol.USERS_PREFIX)
                        ? "Active users: " + line.substring(Protocol.USERS_PREFIX.length()).replace(",", ", ")
                        : line;
                Platform.runLater(() -> messages.add(msg));
            }
        } catch (IOException ignored) {
        } finally {
            connected = false;
            Platform.runLater(() -> {
                messages.add("Disconnected from server.");
                if (onDisconnect != null) onDisconnect.run();
            });
        }
    }

    public void send(String text) {
        if (!connected || out == null) return;
        if (readOnly) return;
        text = text.trim();
        if (text.isEmpty()) return;
        out.println(text);
        out.flush();
    }

    public void disconnect() {
        connected = false;
        if (out != null) {
            try {
                out.println("bye");
                out.flush();
            } catch (Exception ignored) {}
        }
        try {
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException ignored) {}
        out = null;
        in = null;
        socket = null;
    }
}
