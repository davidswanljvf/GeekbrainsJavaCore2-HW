package ru.geekbrains.homework7.Server;

import ru.geekbrains.homework7.Server_API;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Server_API{
    private Server server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String nick;

    public ClientHandler(Server server, Socket socket){
        try{
            this.server = server;
            this.socket = socket;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            nick = "...";
        }catch(IOException e){
            e.printStackTrace();
        }
        new Thread(() -> {
            try{
                while(true){
                    String msg = in.readUTF();
                    if(msg.startsWith(AUTH)){
                        String[] elements = msg.split(" ");
                        String nick = server.getAuthService().getNickByLoginPass(elements[1],elements[2]);
                        if(nick != null){
                            if(!server.busyNickName(nick)){
                                sendMessage(AUTH_SUCCESSFUL + " " + nick);
                                this.nick = nick;
                                server.usersPanel();
                                server.broadcast(this.nick + " присоединился к чату!");
                                break;
                            }else sendMessage("Этот аккаунт уже используется!");
                        }else sendMessage("Не верный login/password!");
                    }else sendMessage("Вы должны аторизоваться!");
                    if(msg.equalsIgnoreCase(CLOSE_CONNECTION)) disconnect();
                }
               while(true){
                    String msg = in.readUTF();
                    if(msg.startsWith(SYSTEM_SYMBOL)){
                        if(msg.equalsIgnoreCase(CLOSE_CONNECTION)) break;
                        else if(msg.startsWith(PRIVATE_MESSAGE)){
                            String nameTo = msg.split(" ")[1];
                            String message = msg.substring(nameTo.length() + 4);
                            server.privateMessage(this, nameTo, message);
                        }else {
                            sendMessage("Команды не существует!");
                        }
                    }else{
                        System.out.println("Клиент: " + msg);
                        server.broadcast(this.nick + " " + msg);
                    }
                }
            }catch(IOException e){
                e.printStackTrace();
            }finally{
                disconnect();
                try{
                    socket.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }).start();

    }
    public void sendMessage(String msg){
        try{
            out.writeUTF(msg);
            out.flush();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public String getNick(){
        return nick;
    }
    public void disconnect(){
        sendMessage("Вы были отключены!");
        server.remuvUser(this);
        try{
            socket.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
