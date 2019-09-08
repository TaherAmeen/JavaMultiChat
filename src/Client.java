/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Taher
 */
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class Client extends JFrame {

    public JTextArea txt;
    public JTextArea msg;
    Socket so;
    String name;

    Client() {
        setSize(315, 550);
        setLayout(null);

        txt = new JTextArea("");
        txt.setBounds(0, 0, 300, 400);
        txt.setVisible(true);
        txt.setEnabled(false);
        add(txt);

        msg = new JTextArea("");
        msg.setBounds(0, 410, 218, 80);
        msg.setVisible(true);
        add(msg);

        JButton btn = new JButton("Send");
        btn.addActionListener(this::send);
        btn.setBounds(219, 430, 80, 30);
        add(btn);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) throws IOException {
        Client c = new Client();
        try {

            InetAddress ip = InetAddress.getByName("localhost");

            Socket s = new Socket(ip, 8000);
            c.so = s;
            DataInputStream dis = new DataInputStream(s.getInputStream());
            c.name = dis.readUTF();
            c.setTitle(c.name);
            c.setVisible(true);
            while (true) {
                String chat = dis.readUTF();
                c.txt.setText("");
                c.txt.setText(chat);
            }
        } catch (Exception e) {
        }
    }

    public void send(ActionEvent e) {
        try {
            InetAddress ip = InetAddress.getByName("localhost");

            DataOutputStream dos = new DataOutputStream(so.getOutputStream());
            dos.writeUTF(name + ": " + msg.getText());
            msg.setText("");
        } catch (Exception ex) {
        }
    }
}
