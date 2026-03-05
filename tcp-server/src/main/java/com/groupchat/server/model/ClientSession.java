package com.groupchat.server.model;

import java.io.PrintWriter;
import java.net.Socket;

public class ClientSession {
    private final String username;
    private final Socket socket;
    private final PrintWriter out;

    public ClientSession(String username, Socket socket, PrintWriter out) {
        this.username = username;
        this.socket = socket;
        this.out = out;
    }

    public String getUsername() {
        return username;
    }

    public Socket getSocket() {
        return socket;
    }

    public PrintWriter getOut() {
        return out;
    }
}
