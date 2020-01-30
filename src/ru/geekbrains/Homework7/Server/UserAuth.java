package ru.geekbrains.Homework7.Server;

public interface UserAuth {
    void start();
    void stop();
    String getNickByLoginPass(String login, String pass);
}
