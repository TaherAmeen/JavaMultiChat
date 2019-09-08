/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Taher
 */
import java.io.*;
import java.util.*;
import java.net.*;

public class Server {

    static String chat = "";
    static List<Socket> clients = new ArrayList<>();
    static int counter = 0;

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(8000);

        while (true) {
            Socket s = null;

            try {
                s = ss.accept();

                clients.add(s);
                System.out.println("A new client is connected : " + s);

                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                counter++;
                dos.writeUTF("Client " + counter);
                dos.writeUTF(chat);
                System.out.println("Assigning new thread for this client");

                Thread t = new ClientHandler(s, dis, dos);

                t.start();

            } catch (Exception e) {
                s.close();
            }
        }
    }

    public static void cast() {
        try {
            for (int i = 0; i < clients.size(); i++) {
                DataOutputStream dos = new DataOutputStream(clients.get(i).getOutputStream());
                dos.writeUTF(chat);
            }
        } catch (Exception e) {
        }
    }
}

class ClientHandler extends Thread {

    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;

    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
    }

    @Override
    public void run() {
        String received;
        String toreturn;
        while (true) {
            try {

                received = dis.readUTF();
                Server.chat += received + "\n";
                Server.cast();

            } catch (IOException e) {
            }
        }

    }
}
