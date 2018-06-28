/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author samiu
 */
public class Server {
    public static ArrayList<ClientInformation> clientList = new ArrayList<ClientInformation>();
    
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            while(true){
                Socket newClientSocket = serverSocket.accept();
                Thread clientHandlerThread = new Thread(new ClientHandler(newClientSocket));
                clientHandlerThread.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
