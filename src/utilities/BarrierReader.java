package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;

import gameComponents.*;
import view.MapDesigner;

// Class to read a txt file and get x and y data.
public class BarrierReader {

    public static ArrayList<ArrayList<Barrier>> readBarriers(String filePath) throws FileNotFoundException {

        // Create list barrier
        ArrayList<Barrier> simpleBarriers = new ArrayList<>(); // Simple Barriers
        ArrayList<Barrier> firmBarriers = new ArrayList<>(); // Frim Barrierss
        ArrayList<Barrier> explosiveBarriers = new ArrayList<>(); // Explosive Barriers
        ArrayList<Barrier> rewardingBarriers = new ArrayList<>(); // Rewarding Barriers
        ArrayList<ArrayList<Barrier>> allBarriers = new ArrayList<ArrayList<Barrier>>(); // List for keeping all barriers

        ArrayList<int[]> barrierList = new ArrayList<int[]>();

        File file = new File(filePath); // File path should be in String data
        Scanner scanner = new Scanner(file); // scan the file
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {

            barrierList = (ArrayList<int[]>) ois.readObject(); //get the barrierList from saved map file

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        for (int[] i : barrierList) {

            int x; // x and y should be converted to integers.
            int y;
            int id; // ID to distinguish barriers.
            int hit; //life of barrier

            try {
                x = i[0];
                y = i[1];
                id = i[2];
                hit = i[3];
            } catch (NumberFormatException e) {
                System.err.println("Invalid number format in line: ");
                continue;
            }

            // Create barrier instances to add to the barrier class
            switch (id) { 
                case 1:
                    //SimpleBarrier bar1 = new SimpleBarrier(86, x, y);
                    SimpleBarrier bar1 = new SimpleBarrier(x, y);
                    simpleBarriers.add(bar1);
                    break;
                case 2:
                    ReinforcedBarrier bar2 = new ReinforcedBarrier(hit, x, y);
                    firmBarriers.add(bar2);
                    break;
                case 3:
                    ExplosiveBarrier bar3 = new ExplosiveBarrier(x, y);
                    explosiveBarriers.add(bar3);
                    break;
                case 4:
                    RewardingBarrier bar4 = new RewardingBarrier(x, y);
                    rewardingBarriers.add(bar4);
                    break;
                default:
                    System.err.println("Invalid barrier ID: " + id);
                    // Handle invalid IDs (optional)
            }

            
        }

        scanner.close();
        // Add each list to the main list
        allBarriers.add(simpleBarriers); 
        allBarriers.add(firmBarriers);
        allBarriers.add(explosiveBarriers);
        allBarriers.add(rewardingBarriers);

        return allBarriers;
    }

    public static void main(String[] args) throws FileNotFoundException {
            ArrayList<ArrayList<Barrier>> barrierList = readBarriers("/Users/idenizalan/Desktop/test.dat"); //give saved file path
            System.out.println(barrierList.get(1).size());
            
    }
}