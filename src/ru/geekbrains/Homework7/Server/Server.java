package ru.geekbrains.Homework7.Server;

import ru.geekbrains.Homework7.ServerPort;
import ru.geekbrains.Homework7.Server_API;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server implements ServerPort, Server_API {
    private Vector<ClientHandler> clients;
    private UserAuth authService;
    public UserAuth getAuthService(){
        return authService;
    }
    public Server(){
        ServerSocket server = null;
        Socket socket = null;
        clients = new Vector<>();
        try{
            server = new ServerSocket(PORT);
            authService = new AuthService();
            authService.start(); //placeholder
            System.out.println("Сервер запущен! Ожидание подключений");
            while(true){
                socket = server.accept();
                clients.add(new ClientHandler(this, socket));
                System.out.println("Подключился клиент!");
            }
        }catch(IOException e){
        }finally{
        }
    }
    public void broadcast(String msg){
        for(ClientHandler client : clients){
            client.sendMessage(msg);
        }
    }
    public void privateMessage(ClientHandler from, String to, String msg){
        boolean nickFound = false;
        for(ClientHandler client : clients){
            if(client.getNick().equals(to)){
                nickFound = true;
                client.sendMessage("from " + from.getNick() + ": " + msg);
                from.sendMessage("to " + to + " msg: " + msg);
                break;
            }
        }
        if(!nickFound)
            from.sendMessage("Пользователь не найден");
    }
    public void usersPanel(){
        StringBuffer sb = new StringBuffer(USERS_LIST);
        for(ClientHandler client : clients){
            sb.append(" " + client.getNick());
        }
        for(ClientHandler client : clients){
            client.sendMessage(sb.toString());
        }
    }
    public void remuvUser(ClientHandler c){
        clients.remove(c);
        usersPanel();
    }
    public boolean busyNickName(String nick){
        for(ClientHandler client : clients){
            if(client.getNick().equals(nick)) return true;
        }
        return false;
    }
}