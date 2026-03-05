package com.groupchat.server.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ClientHandler {
    private final Socket socket;
    private final ServerModel model;
    private PrintWriter out;

    ClientHandler(Socket socket, ServerModel model) {
        this.socket = socket;
        this.model = model;
    }

    PrintWriter getWriter() {
        return out;
    }

    String readUsername() throws IOException {
        out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line = in.readLine();
        return line != null ? line.trim() : null;
    }

    void send(String message) {
        if (out != null) {
            out.println(message);
            out.flush();
        }
    }

    void run(String username) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            line = line.trim();
            if (line.equalsIgnoreCase(Protocol.CMD_BYE) || line.equalsIgnoreCase(Protocol.CMD_END)) break;
            if (line.equalsIgnoreCase(Protocol.CMD_ALL_USERS)) {
                model.sendUserList(username);
                continue;
            }
            if (!line.isEmpty()) model.broadcast(username, line);
        }
    }
}
