/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SudokuFX3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MultiClient {

    final static int ServerPort = 1234;
    static InetAddress ip;
    static Socket s;
    static DataInputStream dis;
    static DataOutputStream dos;

    public static void main(String[] args) throws UnknownHostException, IOException {
        ip = InetAddress.getByName("localhost");
        s = new Socket(ip, ServerPort);
        dis = new DataInputStream(s.getInputStream());
        dos = new DataOutputStream(s.getOutputStream());
    }

    public static void stop() {
        try {
            dos.writeUTF("logout");
            s.close();
            dis.close();
            dos.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
