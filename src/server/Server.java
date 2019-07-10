package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.Vector;

public class Server {
    Vector <ClientHandler> clients;

    public Server() throws SQLException {
        AuthService.connect();

        ServerSocket server = null;
        Socket socket = null;


        try {
            clients = new Vector<>();
            server = new ServerSocket(8189);

            System.out.println("Сервер запущен");

            while(true){
                socket = server.accept();
                System.out.println("Клиент подключился");
                new ClientHandler(this,socket);

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            AuthService.disconnect();
        }
    }

    public void broadcastMsg(String str, String sender){
        for (ClientHandler o: clients) {
            o.sendMsg(sender + ": "+ str);
        }
    }

    public void broadcastMsg(String str, String sender, String receiver){
        for (ClientHandler o: clients) {
            if(o.getNick().equals(receiver) || o.getNick().equals(sender)){
                o.sendMsg("private [ "+ sender +" ] to [ "+receiver+" ]: "+ str);
            }
        }
    }

    public void subscribe(ClientHandler clientHandler){
        clients.add(clientHandler);

    }

    public void unsubscribe(ClientHandler clientHandler){
        clients.remove(clientHandler);
    }

    public boolean authorized(String nick){
        for (ClientHandler u:clients) {
            if (u.getNick().equals(nick)){
                return true;
            }
        }
        return false;
    }
}
