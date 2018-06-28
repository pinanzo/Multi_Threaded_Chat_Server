package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author samiu
 */
public class ClientReader implements Runnable{
    Socket clientSocket=null;
    BufferedReader inputBuffer = null;
    public ClientReader(Socket clientSocket){
        try{
            this.clientSocket = clientSocket;
            inputBuffer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException ex) {
            Logger.getLogger(ClientReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run() {
        try {
            while(true){
                System.out.println(inputBuffer.readLine());
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
