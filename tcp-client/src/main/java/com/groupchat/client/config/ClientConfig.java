package com.groupchat.client.config;

import com.groupchat.client.model.Protocol;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ClientConfig {
    private static final String CONFIG_FILE = "config.properties";
    private String host = "localhost";
    private int port = 3000;

    public void load() throws IOException {
        Properties props = new Properties();
        try (InputStream in = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (in != null) {
                props.load(in);
                host = props.getProperty("server.host", host).trim();
                int p = Integer.parseInt(props.getProperty("server.port", String.valueOf(port)).trim());
                if (p >= Protocol.MIN_PORT && p <= Protocol.MAX_PORT) port = p;
            }
        }
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
