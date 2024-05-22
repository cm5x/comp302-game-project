package gameMechanics;

import java.net.*;
import java.util.ArrayList;
import java.io.*;

public class Server extends Thread {
    private ServerSocket serverSocket;
    private Socket socket;


    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(50000);

        InetAddress ip = InetAddress.getLocalHost();
        System.out.println(ip.toString());
        this.runServer();
    }

     public void runServer() {
       
           try {
               System.out.println("Waiting for client on port " + 
                 serverSocket.getLocalPort() + "...");
               Socket server = serverSocket.accept();
               this.socket = server;
                  
              //server.close();
              
           } catch (SocketTimeoutException s) {
              System.out.println("Socket timed out!");
              
           } catch (IOException e) {
              e.printStackTrace();
              
           }
        
     }

     public void sendMap(ArrayList<int[]> barrierList) throws IOException, InterruptedException {
         DataOutputStream out = new DataOutputStream(socket.getOutputStream());
         out.writeInt(barrierList.size());

            for (int[] array : barrierList) {
                out.writeInt(array.length);
                for (int value : array) {
                    out.writeInt(value);
                }
            }
     }

     public void refreshInfo(ArrayList<int[]> barrierList) throws IOException {

         DataOutputStream out = new DataOutputStream(socket.getOutputStream());
         out.writeInt(barrierList.size());

         InputStream inFromServer = socket.getInputStream();
         DataInputStream inputBarrierList = new DataInputStream(inFromServer);

     }

     public static void main(String [] args) {
        int port = 1001;
        try {
           Thread t = new Server(port);
           t.start();
        } catch (IOException e) {
           e.printStackTrace();
        }
     }

}
