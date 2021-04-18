package com.dkit.oopca5.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static com.dkit.oopca5.Constants.CAOService.*;

public class CAOServer {
    public static void main (String [] args)
    {
        CAOServer server = new CAOServer();
        server.start();
    }
    public void start()
    {
        try
        {
            ServerSocket ss = new ServerSocket(8080);
            System.out.println("Server: Server started. Listening for connections...");
            int numOfClient =0;

            while(true)
            {
                Socket s = ss.accept();
                numOfClient++;
                System.out.println("Server: Client "+numOfClient+" connected to the server");
                System.out.println("Server: Port# of remote client: " + s.getPort());
                System.out.println("Server: Port# of this server: " + s.getLocalPort());
                Thread t = new Thread(new CAOClientHandler(s,numOfClient));
                t.start();
                System.out.println("Server: ClientHandler started in thread for client " + numOfClient + ". ");
                System.out.println("Server: Listening for further connections...");
            }
        }
        catch (IOException e)
        {
            System.out.println("Server: IOException: "+e);
        }
        System.out.println("Server: Server exiting, Goodbye!");
    }

}

