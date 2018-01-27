/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 *
 * @author Kuriz
 */
public class Client {
    
    PrintStream streamToServer;
    BufferedReader streamFromServer;
    Socket toServer;
    public static PrintStream cetak;
    public static DataInputStream DIS;
    
    public Client()
    {
        connectToServer();
    }
    
    private void connectToServer() {
        try{      
            toServer = new Socket("127.0.0.1",1601);
            cetak = new PrintStream(toServer.getOutputStream());
            DIS = new DataInputStream(toServer.getInputStream());
            
            BufferedReader temp = new BufferedReader(new InputStreamReader(System.in));
            String input;
            String katadariserver;
            
            do {
                System.out.print("Client : ");
                input = temp.readLine();
                
                cetak.println(input);
                
                katadariserver = DIS.readLine();
                System.out.println("Server : " + katadariserver);
            } while (!input.equals("quit"));
            
            cetak.close();
            toServer.close();
        }
        catch(Exception e)
        {
                System.out.println("Exception "+e);
        }       
    }
    public static void main(String args[]) {
        new Client();
    }
}
