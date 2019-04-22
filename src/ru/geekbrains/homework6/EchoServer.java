package ru.geekbrains.homework6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class EchoServer {

public static void main(String[] args) {
    ServerSocket serverSocket = null;
    Socket socket = null;
    DataOutputStream out = null;
    DataInputStream in = null;
    Scanner scanner = new Scanner(System.in);

    try {
        serverSocket = new ServerSocket(7777);
        System.out.println("Сервер запущен, ожидает подключения!");
        socket = serverSocket.accept();
        System.out.println("Клиент подключился");
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        final DataInputStream threadIn = in;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        System.out.println("Клиент: " + threadIn.readUTF());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        while (true){
            out.writeUTF(scanner.nextLine());
            out.flush();
        }
    } catch (IOException e) {
        e.printStackTrace();
    }finally {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
}
