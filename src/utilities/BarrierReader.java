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

            // Check for valid format (x,y)
            if (parts.length != 3) {
                System.err.println("Invalid line format in file: " + line);
                continue;
            }

            int x; // x and y should be converted to integers.
            int y;
            int id; // ID to distinguish barriers.

            try {
                x = Integer.parseInt(parts[0]);
                y = Integer.parseInt(parts[1]);
                id = Integer.parseInt(parts[2]);
            } catch (NumberFormatException e) {
                System.err.println("Invalid number format in line: " + line);
                continue;
            }

            // Create barrier instances to add to the barrier class

            
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