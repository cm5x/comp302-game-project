package utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import gameComponents.*;

// Class to read a txt file and get x and y data.
public class BarrierReader {

    public static List<List<Barrier>> readBarriers(String filePath) throws FileNotFoundException {

        // Create list barrier
        List<Barrier> simpleBarriers = new ArrayList<>(); // Simple Barriers
        List<Barrier> firmBarriers = new ArrayList<>(); // Frim Barrierss
        List<Barrier> explosiveBarriers = new ArrayList<>(); // Explosive Barriers
        List<Barrier> rewardingBarriers = new ArrayList<>(); // Rewarding Barriers
        List<List<Barrier>> allBarriers = new ArrayList<>(); // List for keeping all barriers

        File file = new File(filePath); // File path should be in String data
        Scanner scanner = new Scanner(file); // scan the file

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(","); // Split by comma (",")

            // Check for valid format (x,y,id,life)
            if (parts.length != 4) {
                System.err.println("Invalid line format in file: " + line);
                continue;
            }

            int x; // x and y should be converted to integers.
            int y;
            int id; // ID to distinguish barriers.
            int hit; //life of barrier

            try {
                x = Integer.parseInt(parts[0]);
                y = Integer.parseInt(parts[1]);
                id = Integer.parseInt(parts[2]);
                hit = Integer.parseInt(parts[3]);
            } catch (NumberFormatException e) {
                System.err.println("Invalid number format in line: " + line);
                continue;
            }

            // Create barrier instances to add to the barrier class
            switch (id) { 
                case 1:
                    SimpleBarrier bar1 = new SimpleBarrier(86, x, y);
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
}