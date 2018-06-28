/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author samiu
 */
public class ClientWriter implements Runnable{
    Socket clientSocket=null;
    Scanner sc = new Scanner(System.in);
    public ClientWriter(Socket clientSocket){
        this.clientSocket = clientSocket;
    }
    
    @Override
    public void run() {
        try {
            while(true){
                DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());
                outputStream.writeBytes(sc.nextLine()+"\n");
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
