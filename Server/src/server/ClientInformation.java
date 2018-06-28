package server;

import java.net.Socket;

public class ClientInformation {
    Socket clientSocket=null;
    String clientName=null;
    String clientPassword=null;
    
    public ClientInformation(Socket clientSocket, String clientName, String clientPassword){
        this.clientSocket=clientSocket;
        this.clientName=clientName;
        this.clientPassword=clientPassword;
    }
}
