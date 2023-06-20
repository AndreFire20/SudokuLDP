/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SudokuFX3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;

public class Server {

    static Vector<ClientHandler> ar = new Vector<>();
    static int i = 0;

    public static void main(String[] args) throws IOException {
        System.out.println("Servidor aceita conexões.");
        ServerSocket ss = new ServerSocket(1234);

        Socket s;
        while (ar.size() < 2) {
            s = ss.accept();
            System.out.println("Novo client recebido : " + s);

            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

            ClientHandler mtch = new ClientHandler(s, "client " + i, dis, dos);

            System.out.println("Adiciona client " + i + " à lista ativa.");
            ar.add(mtch);

            i++;
        }

        System.out.println("Game starts..");
        for (ClientHandler mtch : ar) {
            Thread t = new Thread(mtch);
            t.start();
        }
    }

    private static class ClientHandler implements Runnable {

        private String name;
        final DataInputStream dis;
        final DataOutputStream dos;
        Socket s;
        boolean isloggedin;
        boolean completed;
        long time;

        private ClientHandler(Socket s, String string,
                DataInputStream dis, DataOutputStream dos) {
            this.s = s;
            this.dis = dis;
            this.dos = dos;
            this.name = string;
            this.isloggedin = true;
            this.completed = false;
        }

        @Override
        public void run() {
            String recebido;
            int i = -1;
            try {
                while (s.isConnected()) {
                    if (i++ == -1) {
                        dos.writeUTF("start");
                    }
                    recebido = dis.readUTF();
                    System.out.println(recebido);

                    if (recebido.endsWith("logout")) {
                        this.isloggedin = false;
                        this.s.close();
                        break; // while
                    }

                    String data[] = recebido.split("#");
                    if (data[0].equals("finish")) {
                        completed = true;
                        time = Long.parseLong(data[1]);
                        for (ClientHandler mtch : Server.ar) {
                            if (mtch != this) {
                                mtch.dos.writeUTF("stop");
                            }
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println(name + " exit");
                Server.ar.remove(this);
            }

        }
    }
}
