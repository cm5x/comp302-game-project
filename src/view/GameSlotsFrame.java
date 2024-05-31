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

public class GameSlotsFrame extends JFrame {
    
    private ArrayList<Integer> gameSaves = new ArrayList<Integer>();
    private int index = 1;
    private JButton gameSlot1;
    private JButton gameSlot2;
    private JButton gameSlot3;
    private JButton gameSlot4;
    private JButton gameSlot5;
    private ArrayList<int[]> mapArrayList;
    private FrameCloseListener listener;
    private int loadGameIndex;
    Player p = new Player("uname", "pass");


    public GameSlotsFrame(ArrayList<int[]> mapArrayList, FrameCloseListener listener, Timer timer, ArrayList<Integer> playerInventory) {
        
        setTitle("Game Slots");
        setSize(500,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        this.mapArrayList = mapArrayList;
        this.listener = listener;

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10)); // 5 rows, 1 column

        Path directory = Paths.get("src/gameSaves");

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
            // Iterate over the files and subdirectories in the directory
            for (Path file : stream) {
                
                int fileName = Integer.parseInt(Character.toString(file.getFileName().toString().charAt(8)));
                gameSaves.add(fileName);
                // Perform operations on the file, such as reading its contents or processing it
            }
        } catch (IOException | DirectoryIteratorException e) {
            e.printStackTrace();
        }

        if (gameSaves.contains(1)) {
            JButton gameSlot1 = new JButton("Game 1");
            gameSlot1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String[] options = {"Override", "Cancel"};
                    int defaultOptionIndex = 0; // Index of the default button
                    int choice = JOptionPane.showOptionDialog(null, "The selected game will be overwritten. Do you want to continue?", "Override Game",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[defaultOptionIndex]);

                    // Handle the user's choice
                    if (choice != JOptionPane.CLOSED_OPTION) {
                        System.out.println("You chose: " + options[choice]);

                        if (choice == 0) {
                            saveGame(mapArrayList, 1, playerInventory);
                            JOptionPane.showMessageDialog(null, "Game Saved");
                            dispose();
                            timer.start();
                        } else {
                        }

                    } else {
                        System.out.println("Dialog closed without selection.");
                    }
                }
            });
            panel.add(gameSlot1);
        } else {
            JButton gameSlot1 = new JButton("Empty Slot");
            gameSlot1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    saveGame(mapArrayList, 1, playerInventory);
                    JOptionPane.showMessageDialog(null, "Game Saved");
                    dispose();
                    timer.start();
                }
            });
            panel.add(gameSlot1);
        }

        if (gameSaves.contains(2)) {
            JButton gameSlot2 = new JButton("Game 2");
            gameSlot2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String[] options = {"Override", "Cancel"};
                    int defaultOptionIndex = 0; // Index of the default button
                    int choice = JOptionPane.showOptionDialog(null, "The selected game will be overwritten. Do you want to continue?", "Override Game",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[defaultOptionIndex]);

                    // Handle the user's choice
                    if (choice != JOptionPane.CLOSED_OPTION) {
                        System.out.println("You chose: " + options[choice]);

                        if (choice == 0) {
                            saveGame(mapArrayList, 2, playerInventory);
                            JOptionPane.showMessageDialog(null, "Game Saved");
                            dispose();
                            timer.start();
                        } else {
                        }

                    } else {
                        System.out.println("Dialog closed without selection.");
                    }
                }
            });
            panel.add(gameSlot2);
        } else {
            JButton gameSlot2 = new JButton("Empty Slot");
            gameSlot2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    saveGame(mapArrayList, 2, playerInventory);
                    JOptionPane.showMessageDialog(null, "Game Saved");
                    dispose();
                    timer.start();
                }
            });
            panel.add(gameSlot2);
        }

        if (gameSaves.contains(3)) {
            JButton gameSlot3 = new JButton("Game 3");
            gameSlot3.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String[] options = {"Override", "Cancel"};
                    int defaultOptionIndex = 0; // Index of the default button
                    int choice = JOptionPane.showOptionDialog(null, "The selected game will be overwritten. Do you want to continue?", "Override Game",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[defaultOptionIndex]);

                    // Handle the user's choice
                    if (choice != JOptionPane.CLOSED_OPTION) {
                        System.out.println("You chose: " + options[choice]);

                        if (choice == 0) {
                            saveGame(mapArrayList, 3, playerInventory);
                            JOptionPane.showMessageDialog(null, "Game Saved");
                            dispose();
                            timer.start();
                        } else {
                        }

                    } else {
                        System.out.println("Dialog closed without selection.");
                    }
                }
            });
            panel.add(gameSlot3);
        } else {
            JButton gameSlot3 = new JButton("Empty Slot");
            gameSlot3.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    saveGame(mapArrayList, 3, playerInventory);
                    JOptionPane.showMessageDialog(null, "Game Saved");
                    dispose();
                    timer.start();
                }
            });
            panel.add(gameSlot3);
        }

        if (gameSaves.contains(4)) {
            JButton gameSlot4 = new JButton("Game 4");
            gameSlot4.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String[] options = {"Override", "Cancel"};
                    int defaultOptionIndex = 0; // Index of the default button
                    int choice = JOptionPane.showOptionDialog(null, "The selected game will be overwritten. Do you want to continue?", "Override Game",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[defaultOptionIndex]);

                    // Handle the user's choice
                    if (choice != JOptionPane.CLOSED_OPTION) {
                        System.out.println("You chose: " + options[choice]);

                        if (choice == 0) {
                            saveGame(mapArrayList, 4, playerInventory);
                            JOptionPane.showMessageDialog(null, "Game Saved");
                            dispose();
                            timer.start();
                        } else {
                        }

                    } else {
                        System.out.println("Dialog closed without selection.");
                    }
                }
            });
            panel.add(gameSlot4);
        } else {
            JButton gameSlot4 = new JButton("Empty Slot");
            gameSlot4.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    saveGame(mapArrayList, 4, playerInventory);
                    JOptionPane.showMessageDialog(null, "Game Saved");
                    dispose();
                    timer.start();
                }
            });
            panel.add(gameSlot4);
        }
        
        if (gameSaves.contains(5)) {
            JButton gameSlot5 = new JButton("Game 5");
            gameSlot5.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String[] options = {"Override", "Cancel"};
                    int defaultOptionIndex = 0; // Index of the default button
                    int choice = JOptionPane.showOptionDialog(null, "The selected game will be overwritten. Do you want to continue?", "Override Game",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[defaultOptionIndex]);

                    // Handle the user's choice
                    if (choice != JOptionPane.CLOSED_OPTION) {
                        System.out.println("You chose: " + options[choice]);

                        if (choice == 0) {
                            saveGame(mapArrayList, 5, playerInventory);
                            JOptionPane.showMessageDialog(null, "Game Saved");
                            dispose();
                            timer.start();
                        } else {
                        }

                    } else {
                        System.out.println("Dialog closed without selection.");
                    }
                }
            });
            panel.add(gameSlot5);
        } else {
            JButton gameSlot5 = new JButton("Empty Slot");
            gameSlot5.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    saveGame(mapArrayList, 5, playerInventory);
                    JOptionPane.showMessageDialog(null, "Game Saved");
                    dispose();
                    timer.start();
                }
            });
            panel.add(gameSlot5);
        }
        
        add(panel);
        setVisible(true);
    }

    public GameSlotsFrame(FrameCloseListener listener, Timer timer) {
        
        setTitle("Game Slots");
        setSize(500,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        this.listener = listener;

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10)); // 5 rows, 1 column

        Path directory = Paths.get("src/gameSaves");

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
            // Iterate over the files and subdirectories in the directory
            for (Path file : stream) {
                
                int fileName = Integer.parseInt(Character.toString(file.getFileName().toString().charAt(8)));
                gameSaves.add(fileName);
                // Perform operations on the file, such as reading its contents or processing it
            }
        } catch (IOException | DirectoryIteratorException e) {
            e.printStackTrace();
        }

        if (gameSaves.contains(1)) {
            JButton gameSlot1 = new JButton("Game 1");
            gameSlot1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    loadGameIndex = 1;
                    dispose();
                    timer.start();
                }
            });
            panel.add(gameSlot1);
        } else {
            JButton gameSlot1 = new JButton("Empty Slot");
            gameSlot1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    
                }
            });
            panel.add(gameSlot1);
        }

        if (gameSaves.contains(2)) {
            JButton gameSlot2 = new JButton("Game 2");
            gameSlot2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    loadGameIndex = 2;
                    dispose();
                }
            });
            panel.add(gameSlot2);
        } else {
            JButton gameSlot2 = new JButton("Empty Slot");
            gameSlot2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    
                }
            });
            panel.add(gameSlot2);
        }

        if (gameSaves.contains(3)) {
            JButton gameSlot3 = new JButton("Game 3");
            gameSlot3.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    loadGameIndex = 3;
                    dispose();
                }
            });
            panel.add(gameSlot3);
        } else {
            JButton gameSlot3 = new JButton("Empty Slot");
            gameSlot3.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    
                }
            });
            panel.add(gameSlot3);
        }

        if (gameSaves.contains(4)) {
            JButton gameSlot4 = new JButton("Game 4");
            gameSlot4.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    loadGameIndex = 4;
                    dispose();
                }
            });
            panel.add(gameSlot4);
        } else {
            JButton gameSlot4 = new JButton("Empty Slot");
            gameSlot4.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    
                }
            });
            panel.add(gameSlot4);
        }
        
        if (gameSaves.contains(5)) {
            JButton gameSlot5 = new JButton("Game 5");
            gameSlot5.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    loadGameIndex = 5;
                    dispose();
                }
            });
            panel.add(gameSlot5);
        } else {
            JButton gameSlot5 = new JButton("Empty Slot");
            gameSlot5.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                        
                }
            });
            panel.add(gameSlot5);
        }
        
        add(panel);
        setVisible(true);
    }

    public GameSlotsFrame() {
        
        setTitle("Game Slots");
        setSize(500,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10)); // 5 rows, 1 column

        Path directory = Paths.get("src/gameSaves");

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
            // Iterate over the files and subdirectories in the directory
            for (Path file : stream) {
                
                int fileName = Integer.parseInt(Character.toString(file.getFileName().toString().charAt(8)));
                gameSaves.add(fileName);
                // Perform operations on the file, such as reading its contents or processing it
            }
        } catch (IOException | DirectoryIteratorException e) {
            e.printStackTrace();
        }

        if (gameSaves.contains(1)) {
            JButton gameSlot1 = new JButton("Game 1");
            gameSlot1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    RunningMode run = new RunningMode(1,p);
                    run.setVisible(true);
                }
            });
            panel.add(gameSlot1);
        } else {
            JButton gameSlot1 = new JButton("Empty Slot");
            gameSlot1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    
                }
            });
            panel.add(gameSlot1);
        }

        if (gameSaves.contains(2)) {
            JButton gameSlot2 = new JButton("Game 2");
            gameSlot2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    RunningMode run = new RunningMode(2,p);
                    run.setVisible(true);
                }
            });
            panel.add(gameSlot2);
        } else {
            JButton gameSlot2 = new JButton("Empty Slot");
            gameSlot2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    
                }
            });
            panel.add(gameSlot2);
        }

        if (gameSaves.contains(3)) {
            JButton gameSlot3 = new JButton("Game 3");
            gameSlot3.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    RunningMode run = new RunningMode(3,p);
                    run.setVisible(true);
                }
            });
            panel.add(gameSlot3);
        } else {
            JButton gameSlot3 = new JButton("Empty Slot");
            gameSlot3.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    
                }
            });
            panel.add(gameSlot3);
        }

        if (gameSaves.contains(4)) {
            JButton gameSlot4 = new JButton("Game 4");
            gameSlot4.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    RunningMode run = new RunningMode(4,p);
                    run.setVisible(true);
                }
            });
            panel.add(gameSlot4);
        } else {
            JButton gameSlot4 = new JButton("Empty Slot");
            gameSlot4.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    
                }
            });
            panel.add(gameSlot4);
        }
        
        if (gameSaves.contains(5)) {
            JButton gameSlot5 = new JButton("Game 5");
            gameSlot5.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    RunningMode run = new RunningMode(5,p);
                    run.setVisible(true);
                }
            });
            panel.add(gameSlot5);
        } else {
            JButton gameSlot5 = new JButton("Empty Slot");
            gameSlot5.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    
                }
            });
            panel.add(gameSlot5);
        }
        
        add(panel);
        setVisible(true);
    }

    public void saveGame(ArrayList<int[]> barrierList, int mapSlot, ArrayList<Integer> playerInventory) {
        File fileToSave = new File("src/gameSaves/gameSave" + mapSlot + ".dat");
        File inventoryFileToSave = new File("src/inventorySaves/inventorySave" + mapSlot + ".dat");
            
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileToSave))) {
            oos.writeObject(barrierList);
                
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(inventoryFileToSave))) {
            oos.writeObject(playerInventory);
                
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        // Send data back to MainFrame
        listener.onFrameClosed(loadGameIndex);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameSlotsFrame frame = new GameSlotsFrame();
            frame.setVisible(true);
        });
    }
}
