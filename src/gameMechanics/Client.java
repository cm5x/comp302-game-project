package gameMechanics;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

import gameComponents.Player;
import view.RunningMode;

public class Client {
   
   private String serverAdress;
   private Socket socket;
   private int port;
   private ArrayList<int[]> barrierList;
   private Client clientInstance;

   public Client(String serverAdress, int port) {
      this.serverAdress = serverAdress;
      this.port = port;
      this.clientInstance = this;
   }

   public void connectToServer(){
      try {
         System.out.println("Connecting to " + serverAdress + " on port " + port);
         Socket client = new Socket(serverAdress, port);
         socket = client;

         System.out.println("Just connected to " + client.getRemoteSocketAddress());

         OutputStream outToServer = client.getOutputStream();
         DataOutputStream out = new DataOutputStream(outToServer);
           
         InputStream inFromServer = client.getInputStream();
         DataInputStream inputBarrierList = new DataInputStream(inFromServer);
               
         System.out.println("Server sent map");

         int arrayListSize = inputBarrierList.readInt();

         ArrayList<int[]> arrayList = new ArrayList<>(arrayListSize);

            // Read each array's size and contents
            for (int i = 0; i < arrayListSize; i++) {
                int arraySize = inputBarrierList.readInt(); // Read the size of the array
                int[] array = new int[arraySize];
                for (int j = 0; j < arraySize; j++) {
                    array[j] = inputBarrierList.readInt(); // Read each element of the array
                }
                arrayList.add(array);
            }

            for (int[] array : arrayList) {
               for (int value : array) {
                   System.out.print(value + " ");
               }
               System.out.println();
            }

            barrierList = arrayList;

            Player p = new Player("uname", "pass");
            RunningMode run = new RunningMode(6,p,barrierList,clientInstance);
            run.setVisible(true);
        
         //client.close();
         
         
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   public void refreshInfo(ArrayList<int[]> barrierList) throws IOException {
      DataOutputStream out = new DataOutputStream(socket.getOutputStream());
      out.writeInt(barrierList.size());
        
      InputStream inFromServer = socket.getInputStream();
      DataInputStream inputBarrierList = new DataInputStream(inFromServer);
      System.out.println(inputBarrierList.readInt());
      //return inputBarrierList.readInt();

      
      
   }
}

