package ru.geekbrains.homework6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {

public static void main(String[] args) {
    Socket socket = null;
    DataInputStream in;
    DataOutputStream out;
    Scanner scanner = new Scanner(System.in);
    try {
        socket = new Socket ("localhost", 7777);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        final DataInputStream threadIn = in;
        new Thread(new Runnable() {
          @Override
        public void run() {
            while(true){
                try {
                    System.out.println("Сервер: " + threadIn.readUTF());
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
    }

}
}

