package gameMechanics;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import gameComponents.Player;
import view.RunningMode;
import view.MultiplayerWaitingScreen;
import view.PlayGame;
import view.JoinGame;   

public class Client {
    private String serverAddress;
    private int port;
    private Socket socket;
    private ArrayList<int[]> barrierList;
    private boolean connected;
    private JoinGame joinGame;

    private RunningMode runningMode;
    private DataInputStream in;
    private DataOutputStream out;

    public boolean getConnected() {
        return connected;
    }



    public void setConnected(boolean connected) {
        this.connected = connected;
    }


    public Client(String serverAddress, int port, JoinGame joinGame) {
        this.serverAddress = serverAddress;
        this.port = port;
        this.connected = false;
        this.joinGame = joinGame;

    }

    

    public void connectToServer() {
        try {
            System.out.println("Connecting to " + serverAddress + " on port " + port);
            socket = new Socket(serverAddress, port);
            System.out.println("Connected to " + socket.getRemoteSocketAddress());


            this.connected = true;

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            receiveMap();

            Player player = new Player("uname", "pass");
            RunningMode runningMode = new RunningMode(1, player);
            runningMode.setVisible(true);

            new Thread(new ScoreUpdater()).start();

            MultiplayerWaitingScreen waitingScreen = MultiplayerWaitingScreen.getInstance();
            if (waitingScreen != null) {
                waitingScreen.setVisible(false);
            }
            joinGame.onConnectionSuccess();

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

    public void sendScore(int score) {
        try {
            out.writeInt(score);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ScoreUpdater implements Runnable {
        public void run() {
            try {
                while (true) {
                    int hostScore = in.readInt();
                    int clientScore = in.readInt();
                    runningMode.updateScores(hostScore, clientScore);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    
}
