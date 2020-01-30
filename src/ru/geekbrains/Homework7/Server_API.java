package ru.geekbrains.Homework7;

public interface Server_API {
    String CLOSE_CONNECTION = "/end";
    String PRIVATE_MESSAGE = "/w";
    String AUTH_SUCCESSFUL = "/authok";
    String SYSTEM_SYMBOL = "/";
    String USERS_LIST = "/userslist";
    String AUTH = "/auth";
}