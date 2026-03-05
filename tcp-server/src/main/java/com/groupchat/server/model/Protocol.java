package com.groupchat.server.model;

public final class Protocol {
    public static final String READ_ONLY = "READ_ONLY";
    public static final String OK = "OK";
    public static final String USERS_PREFIX = "USERS:";
    public static final String CMD_BYE = "bye";
    public static final String CMD_END = "end";
    public static final String CMD_ALL_USERS = "allUsers";
    public static final int MAX_USERNAME_LENGTH = 25;
    public static final int MIN_PORT = 1;
    public static final int MAX_PORT = 65535;

    private Protocol() {}
}
