package gameMechanics;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
   
   private String serverAdress;
   private int port;

   public Client(String serverAdress, int port) {
      this.serverAdress = serverAdress;
      this.port = port;
   }

   public void connectToServer(){
      try {
         System.out.println("Connecting to " + serverAdress + " on port " + port);
         Socket client = new Socket(serverAdress, port);

         while(!client.isClosed()) {
            System.out.println("Just connected to " + client.getRemoteSocketAddress());
            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);
            
            out.writeUTF("Hello from " + client.getLocalSocketAddress());
        
            InputStream inFromServer = client.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);
            
            System.out.println("Server says " + in.readUTF());
            client.close();
         }
         
         
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}

