/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import static server.Server.clientList;

/**
 *
 * @author samiu
 */
public class ClientHandler implements Runnable{
    Socket clientSocket = null;
    BufferedReader inputBuffer = null;
    DataOutputStream outputStream = null;
    String loggedInUserName = null;
    public ClientHandler(Socket clientSocket){
        try {
            this.clientSocket = clientSocket;
            inputBuffer = new BufferedReader ( new InputStreamReader (clientSocket.getInputStream()));
            outputStream = new DataOutputStream(clientSocket.getOutputStream());
            
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void run(){
        try {
            while(true){
                String clientMessage = inputBuffer.readLine();
                String[] parsedMessage = clientMessage.split(":");
                if(parsedMessage[0].equals("msg") && loggedInUserName!=null){
                    //outputStream.writeBytes(parsedMessage[1]+"\n");
                    sendMessage(parsedMessage,parsedMessage[parsedMessage.length-1]);
                }
                else if(parsedMessage[0].equals("reg")){
                    String clientName = collectClientName();
                    String clientPassword = collectClientPassword();
                    clientList.add(new ClientInformation(clientSocket, clientName, clientPassword));
                    System.out.println(clientList.get(0).clientName);
                }
                else if(parsedMessage[0].equals("login")){
                    for(ClientInformation clientInfo:clientList){
                        if(clientInfo.clientName.equals(parsedMessage[1])&&clientInfo.clientPassword.equals(parsedMessage[2])){
                            loggedInUserName = parsedMessage[1];
                        }
                    }
                }
                else if(parsedMessage[0].equals("showlist")){
                    showClientList();
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sendMessage(String parsedMessage[], String message)throws IOException{
        if(parsedMessage[1].equals("all")){
            for(ClientInformation clientInfo:clientList){
                if(!clientInfo.clientName.equals(loggedInUserName)){
                    DataOutputStream toDestinationStream = new DataOutputStream(clientInfo.clientSocket.getOutputStream());
                    toDestinationStream.writeBytes(loggedInUserName+": "+message+"\n");
                }
            }
        }
        else{
            for(int i=1; i<parsedMessage.length-1; i++){
                Socket destinationSocket = getDestinationSocket(parsedMessage[i]);
                DataOutputStream toDestinationStream = new DataOutputStream(destinationSocket.getOutputStream());
                toDestinationStream.writeBytes(loggedInUserName+": "+message+"\n");
            }
        }
            
    }
    
    Socket getDestinationSocket(String destinationUserName){
        for(ClientInformation clientInfo:clientList){
            if(clientInfo.clientName.equals(destinationUserName)){
                return clientInfo.clientSocket;
            }
        }
        return null;
    }
    
    String collectClientName() throws IOException{
        outputStream.writeBytes("userName"+"\n");
        return inputBuffer.readLine();
    }
    String collectClientPassword() throws IOException{
        outputStream.writeBytes("userPassword"+"\n");
        return inputBuffer.readLine();
    }
    
    void showClientList() throws IOException{
        for(ClientInformation clientInfo:clientList){
            outputStream.writeBytes(clientInfo.clientName+"\n");
        }
    }
}
