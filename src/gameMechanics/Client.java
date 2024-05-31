package gameMechanics;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import gameComponents.Player;
import view.RunningMode;
import view.MultiplayerWaitingScreen;

public class Client {
    private String serverAddress;
    private int port;
    private Socket socket;
    private ArrayList<int[]> barrierList;

    public Client(String serverAddress, int port) {
        this.serverAddress = serverAddress;
        this.port = port;
    }

    public void connectToServer() {
        try {
            System.out.println("Connecting to " + serverAddress + " on port " + port);
            socket = new Socket(serverAddress, port);
            System.out.println("Connected to " + socket.getRemoteSocketAddress());

            receiveMap();

            Player player = new Player("uname", "pass");
            RunningMode runningMode = new RunningMode(1, player, barrierList, this);
            runningMode.setVisible(true);

            MultiplayerWaitingScreen waitingScreen = MultiplayerWaitingScreen.getInstance();
            if (waitingScreen != null) {
                waitingScreen.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void receiveMap() throws IOException {
        DataInputStream in = new DataInputStream(socket.getInputStream());
        int arrayListSize = in.readInt();
        barrierList = new ArrayList<>(arrayListSize);
        for (int i = 0; i < arrayListSize; i++) {
            int arraySize = in.readInt();
            int[] array = new int[arraySize];
            for (int j = 0; j < arraySize; j++) {
                array[j] = in.readInt();
            }
            barrierList.add(array);
        }
    }
}
