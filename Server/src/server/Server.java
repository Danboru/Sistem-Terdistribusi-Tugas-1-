/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Kuriz
 */
public class Server implements Runnable {

    Socket fromClient;
    ServerSocket serverSocket;
    PrintStream streamToClient;
    BufferedReader streamFromClient;
    public static PrintStream cetak;
    public static DataInputStream DIS;
    Thread thread;
    String input;
    String kata;
    String kataoutput;
    BufferedReader temp;

    public Server() {
        try {
            serverSocket = new ServerSocket(1601);
        } catch (Exception e) {
            System.out.println("Socket could not be created" + e);
        }
        thread = new Thread(this);
        thread.start();
    }

    public void run() {
        try {
            while (true) {
                System.out.println("Server siap nungguin client .... :3");
                fromClient = serverSocket.accept();
                System.out.println("Client connected");
                cetak = new PrintStream(fromClient.getOutputStream());
                DIS = new DataInputStream(fromClient.getInputStream());
                temp = new BufferedReader(new InputStreamReader(System.in));

                do {
                    kata = DIS.readLine();
                    System.out.println("Client : " + kata);
                    System.out.print("Server : ");
                    kataoutput = temp.readLine();
                    cetak.println(kataoutput);
                } while (!kata.equals("quit"));

                cetak.close();
                DIS.close();
                fromClient.close();
                serverSocket.close();
            }
        } catch (Exception e) {
            System.out.println("Exception " + e);
        } 
        
    }

    public static void main(String args[]) {
        new Server();
    }
}
