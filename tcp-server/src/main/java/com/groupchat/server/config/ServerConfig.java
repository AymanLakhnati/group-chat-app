package com.groupchat.server.config;

import com.groupchat.server.model.Protocol;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ServerConfig {
    private static final String CONFIG_FILE = "config.properties";
    private int port;

    public void load() throws IOException {
        Properties props = new Properties();
        try (InputStream in = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (in == null) throw new IOException("Config file not found: " + CONFIG_FILE);
            props.load(in);
        }
        int p = Integer.parseInt(props.getProperty("server.port", "3000").trim());
        if (p < Protocol.MIN_PORT || p > Protocol.MAX_PORT)
            throw new IOException("Invalid port in config: " + p + " (must be " + Protocol.MIN_PORT + "-" + Protocol.MAX_PORT + ")");
        port = p;
    }

    public int getPort() {
        return port;
    }
}
