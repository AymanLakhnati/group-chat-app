package com.groupchat.client.model;

public final class Protocol {
    public static final String READ_ONLY = "READ_ONLY";
    public static final String USERS_PREFIX = "USERS:";
    public static final int MAX_USERNAME_LENGTH = 25;
    public static final int CONNECT_TIMEOUT_MS = 5000;
    public static final int MIN_PORT = 1;
    public static final int MAX_PORT = 65535;

    private Protocol() {}
}
