package client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    public static void main(String[] args) {
        try{
            Socket clientSocket = new Socket("localhost", 12345);
            Thread readerThread = new Thread(new ClientReader(clientSocket));
            Thread writerThread = new Thread(new ClientWriter(clientSocket));
            readerThread.start();
            writerThread.start();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    
}
