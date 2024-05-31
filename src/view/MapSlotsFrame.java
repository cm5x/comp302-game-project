package view;

import javax.swing.*;

import gameComponents.Player;
import utilities.FrameCloseListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.*;
import java.util.ArrayList;
import utilities.FrameCloseListener;

public class MapSlotsFrame extends JFrame {
    
    private ArrayList<Integer> gameSaves = new ArrayList<Integer>();
    private int index = 1;
    private JButton mapSlot1;
    private JButton mapSlot2;
    private JButton mapSlot3;
    private JButton mapSlot4;
    private JButton mapSlot5;
    private ArrayList<int[]> mapArrayList;
    private FrameCloseListener listener;
    private int loadMapIndex;

    Player p = new Player("name","pass");
    
    public void closepanel(){
        this.dispose();
    }


    public MapSlotsFrame(ArrayList<int[]> mapArrayList, FrameCloseListener listener) {
        
        setTitle("Map Slots");
        setSize(500,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        this.mapArrayList = mapArrayList;
        this.listener = listener;

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10)); // 5 rows, 1 column

        Path directory = Paths.get("src/gameMapSaves");

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
            // Iterate over the files and subdirectories in the directory
            for (Path file : stream) {
                
                int fileName = Integer.parseInt(Character.toString(file.getFileName().toString().charAt(10)));
                gameSaves.add(fileName);
                // Perform operations on the file, such as reading its contents or processing it
            }
        } catch (IOException | DirectoryIteratorException e) {
            e.printStackTrace();
        }

        if (gameSaves.contains(1)) {
            JButton mapSlot1 = new JButton("Map 1");
            mapSlot1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String[] options = {"Override", "Cancel"};
                    int defaultOptionIndex = 0; // Index of the default button
                    int choice = JOptionPane.showOptionDialog(null, "The selected map will be overwritten. Do you want to continue?", "Override Map",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[defaultOptionIndex]);

                    // Handle the user's choice
                    if (choice != JOptionPane.CLOSED_OPTION) {
                        System.out.println("You chose: " + options[choice]);

                        if (choice == 0) {
                            saveGame(mapArrayList, 1);
                            JOptionPane.showMessageDialog(null, "Map Saved");
                            dispose();
                        } else {
                        }

                    } else {
                        System.out.println("Dialog closed without selection.");
                    }
                }
            });
            panel.add(mapSlot1);
        } else {
            JButton mapSlot1 = new JButton("Empty Slot");
            mapSlot1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    saveGame(mapArrayList, 1);
                    JOptionPane.showMessageDialog(null, "Map Saved");
                    dispose();
                }
            });
            panel.add(mapSlot1);
        }

        if (gameSaves.contains(2)) {
            JButton mapSlot2 = new JButton("Map 2");
            mapSlot2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String[] options = {"Override", "Cancel"};
                    int defaultOptionIndex = 0; // Index of the default button
                    int choice = JOptionPane.showOptionDialog(null, "The selected map will be overwritten. Do you want to continue?", "Override Map",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[defaultOptionIndex]);

                    // Handle the user's choice
                    if (choice != JOptionPane.CLOSED_OPTION) {
                        System.out.println("You chose: " + options[choice]);

                        if (choice == 0) {
                            saveGame(mapArrayList, 2);
                            JOptionPane.showMessageDialog(null, "Map Saved");
                            dispose();
                        } else {
                        }

                    } else {
                        System.out.println("Dialog closed without selection.");
                    }
                }
            });
            panel.add(mapSlot2);
        } else {
            JButton mapSlot2 = new JButton("Empty Slot");
            mapSlot2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    saveGame(mapArrayList, 2);
                    JOptionPane.showMessageDialog(null, "Map Saved");
                    dispose();
                }
            });
            panel.add(mapSlot2);
        }

        if (gameSaves.contains(3)) {
            JButton mapSlot3 = new JButton("Map 3");
            mapSlot3.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String[] options = {"Override", "Cancel"};
                    int defaultOptionIndex = 0; // Index of the default button
                    int choice = JOptionPane.showOptionDialog(null, "The selected map will be overwritten. Do you want to continue?", "Override Map",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[defaultOptionIndex]);

                    // Handle the user's choice
                    if (choice != JOptionPane.CLOSED_OPTION) {
                        System.out.println("You chose: " + options[choice]);

                        if (choice == 0) {
                            saveGame(mapArrayList, 3);
                            JOptionPane.showMessageDialog(null, "Map Saved");
                            dispose();
                        } else {
                        }

                    } else {
                        System.out.println("Dialog closed without selection.");
                    }
                }
            });
            panel.add(mapSlot3);
        } else {
            JButton mapSlot3 = new JButton("Empty Slot");
            mapSlot3.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    saveGame(mapArrayList, 3);
                    JOptionPane.showMessageDialog(null, "Map Saved");
                    dispose();
                }
            });
            panel.add(mapSlot3);
        }

        if (gameSaves.contains(4)) {
            JButton mapSlot4 = new JButton("Map 4");
            mapSlot4.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String[] options = {"Override", "Cancel"};
                    int defaultOptionIndex = 0; // Index of the default button
                    int choice = JOptionPane.showOptionDialog(null, "The selected map will be overwritten. Do you want to continue?", "Override Map",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[defaultOptionIndex]);

                    // Handle the user's choice
                    if (choice != JOptionPane.CLOSED_OPTION) {
                        System.out.println("You chose: " + options[choice]);

                        if (choice == 0) {
                            saveGame(mapArrayList, 4);
                            JOptionPane.showMessageDialog(null, "Map Saved");
                            dispose();
                        } else {
                        }

                    } else {
                        System.out.println("Dialog closed without selection.");
                    }
                }
            });
            panel.add(mapSlot4);
        } else {
            JButton mapSlot4 = new JButton("Empty Slot");
            mapSlot4.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    saveGame(mapArrayList, 4);
                    JOptionPane.showMessageDialog(null, "Map Saved");
                    dispose();
                }
            });
            panel.add(mapSlot4);
        }
        
        if (gameSaves.contains(5)) {
            JButton mapSlot5 = new JButton("Map 5");
            mapSlot5.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String[] options = {"Override", "Cancel"};
                    int defaultOptionIndex = 0; // Index of the default button
                    int choice = JOptionPane.showOptionDialog(null, "The selected map will be overwritten. Do you want to continue?", "Override Map",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[defaultOptionIndex]);

                    // Handle the user's choice
                    if (choice != JOptionPane.CLOSED_OPTION) {
                        System.out.println("You chose: " + options[choice]);

                        if (choice == 0) {
                            saveGame(mapArrayList, 5);
                            JOptionPane.showMessageDialog(null, "Map Saved");
                            dispose();
                        } else {
                        }

                    } else {
                        System.out.println("Dialog closed without selection.");
                    }
                }
            });
            panel.add(mapSlot5);
        } else {
            JButton mapSlot5 = new JButton("Empty Slot");
            mapSlot5.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    saveGame(mapArrayList, 5);
                    JOptionPane.showMessageDialog(null, "Map Saved");
                    dispose();
                }
            });
            panel.add(mapSlot5);
        }
        
        add(panel);
        setVisible(true);
    }

    public MapSlotsFrame(FrameCloseListener listener) {
        
        setTitle("Map Slots");
        setSize(500,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        this.listener = listener;

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10)); // 5 rows, 1 column

        Path directory = Paths.get("src/gameMapSaves");

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
            // Iterate over the files and subdirectories in the directory
            for (Path file : stream) {
                
                int fileName = Integer.parseInt(Character.toString(file.getFileName().toString().charAt(10)));
                gameSaves.add(fileName);
                // Perform operations on the file, such as reading its contents or processing it
            }
        } catch (IOException | DirectoryIteratorException e) {
            e.printStackTrace();
        }

        if (gameSaves.contains(1)) {
            JButton mapSlot1 = new JButton("Map 1");
            mapSlot1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    loadMapIndex = 1;
                    dispose();
                }
            });
            panel.add(mapSlot1);
        } else {
            JButton mapSlot1 = new JButton("Empty Slot");
            mapSlot1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    
                }
            });
            panel.add(mapSlot1);
        }

        if (gameSaves.contains(2)) {
            JButton mapSlot2 = new JButton("Map 2");
            mapSlot2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    loadMapIndex = 2;
                    dispose();
                }
            });
            panel.add(mapSlot2);
        } else {
            JButton mapSlot2 = new JButton("Empty Slot");
            mapSlot2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    
                }
            });
            panel.add(mapSlot2);
        }

        if (gameSaves.contains(3)) {
            JButton mapSlot3 = new JButton("Map 3");
            mapSlot3.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    loadMapIndex = 3;
                    dispose();
                }
            });
            panel.add(mapSlot3);
        } else {
            JButton mapSlot3 = new JButton("Empty Slot");
            mapSlot3.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    
                }
            });
            panel.add(mapSlot3);
        }

        if (gameSaves.contains(4)) {
            JButton mapSlot4 = new JButton("Map 4");
            mapSlot4.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    loadMapIndex = 4;
                    dispose();
                }
            });
            panel.add(mapSlot4);
        } else {
            JButton mapSlot4 = new JButton("Empty Slot");
            mapSlot4.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    saveGame(mapArrayList, 4);
                    JOptionPane.showMessageDialog(null, "Map Saved");
                    dispose();
                }
            });
            panel.add(mapSlot4);
        }
        
        if (gameSaves.contains(5)) {
            JButton mapSlot5 = new JButton("Map 5");
            mapSlot5.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    loadMapIndex = 5;
                    dispose();
                }
            });
            panel.add(mapSlot5);
        } else {
            JButton mapSlot5 = new JButton("Empty Slot");
            mapSlot5.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                        
                }
            });
            panel.add(mapSlot5);
        }
        
        add(panel);
        setVisible(true);
    }

    public MapSlotsFrame() {
        
        setTitle("Map Slots");
        setSize(500,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10)); // 5 rows, 1 column

        Path directory = Paths.get("src/gameMapSaves");

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
            // Iterate over the files and subdirectories in the directory
            for (Path file : stream) {
                
                int fileName = Integer.parseInt(Character.toString(file.getFileName().toString().charAt(10)));
                gameSaves.add(fileName);
                // Perform operations on the file, such as reading its contents or processing it
            }
        } catch (IOException | DirectoryIteratorException e) {
            e.printStackTrace();
        }

        if (gameSaves.contains(1)) {
            JButton mapSlot1 = new JButton("Map 1");
            mapSlot1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    RunningMode run = new RunningMode(1,p);
                    run.setVisible(true);
                    closepanel();
                    
                }
            });
            panel.add(mapSlot1);
        } else {
            JButton mapSlot1 = new JButton("Empty Slot");
            mapSlot1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    closepanel();
                }
            });
            panel.add(mapSlot1);
        }

        if (gameSaves.contains(2)) {
            JButton mapSlot2 = new JButton("Map 2");
            mapSlot2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    RunningMode run = new RunningMode(2,p);
                    run.setVisible(true);
                    closepanel();
                    
                }
            });
            panel.add(mapSlot2);
        } else {
            JButton mapSlot2 = new JButton("Empty Slot");
            mapSlot2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    closepanel();
                }
            });
            panel.add(mapSlot2);
        }

        if (gameSaves.contains(3)) {
            JButton mapSlot3 = new JButton("Map 3");
            mapSlot3.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    RunningMode run = new RunningMode(3,p);
                    run.setVisible(true);
                    closepanel();
                }
            });
            panel.add(mapSlot3);
        } else {
            JButton mapSlot3 = new JButton("Empty Slot");
            mapSlot3.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    closepanel();
                }
            });
            panel.add(mapSlot3);
        }

        if (gameSaves.contains(4)) {
            JButton mapSlot4 = new JButton("Map 4");
            mapSlot4.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    RunningMode run = new RunningMode(4,p);
                    run.setVisible(true);
                    closepanel();
                }
            });
            panel.add(mapSlot4);
        } else {
            JButton mapSlot4 = new JButton("Empty Slot");
            mapSlot4.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    closepanel();
                }
            });
            panel.add(mapSlot4);
        }
        
        if (gameSaves.contains(5)) {
            JButton mapSlot5 = new JButton("Map 5");
            mapSlot5.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    RunningMode run = new RunningMode(5,p);
                    run.setVisible(true);
                    closepanel();
                }
            });
            panel.add(mapSlot5);
        } else {
            JButton mapSlot5 = new JButton("Empty Slot");
            mapSlot5.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    closepanel();
                }
            });
            panel.add(mapSlot5);
        }
        
        add(panel);
        setVisible(true);
    }

    public void saveGame(ArrayList<int[]> barrierList, int mapSlot) {
        File fileToSave = new File("src/gameMapSaves/exampleMap" + mapSlot + ".dat");
            
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileToSave))) {
                oos.writeObject(barrierList);
                
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    @Override
    public void dispose() {
        super.dispose();
        // Send data back to MainFrame
        listener.onFrameClosed(loadMapIndex);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MapSlotsFrame frame = new MapSlotsFrame();
            frame.setVisible(true);
        });
    }
}
