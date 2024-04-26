package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.List;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;




    public class MapDesigner extends JFrame implements ActionListener{
        private final MapPanel mapPanel;
        private final JPanel blockChooserPanel;
        private final JTextArea infoTextArea;
        JButton randomButton;
        JTextField redinput;
        JTextField blueinput;
        JTextField greeninput;

        JLabel redlab;
        JLabel bluelab;
        JLabel greenlab;

        JPanel randomPanel;

        public MapDesigner() {
            super("Map Designer");
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setLayout(new BorderLayout());
            this.setSize(1920, 1080);

            // Map panel
            this.mapPanel = new MapPanel(this);
            this.mapPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4   ));  // Add a black line border
            this.mapPanel.setBackground(Color.WHITE);  // Set a different background color
            this.add(mapPanel, BorderLayout.CENTER);

            // Block chooser panel
            this.blockChooserPanel = new JPanel();
            this.blockChooserPanel.setPreferredSize(new Dimension(230, 600));
            this.blockChooserPanel.setBackground(Color.LIGHT_GRAY);  // Differentiate by color
            initializeBlockChooser();
            this.add(blockChooserPanel, BorderLayout.WEST);


            // Info text area
    
            infoTextArea = new JTextArea(5, 20);
            infoTextArea.setEditable(false);  // Make text area non-editable
            infoTextArea.setLineWrap(rootPaneCheckingEnabled);
            JScrollPane scrollPane = new JScrollPane(infoTextArea);
            blockChooserPanel.add(scrollPane, BorderLayout.SOUTH);


            //random implementation
            redinput = new JTextField();
            greeninput = new JTextField();
            blueinput = new JTextField();
            randomButton = new JButton("Build");
            redlab = new JLabel("Enter red block number");
            greenlab = new JLabel("Enter green block number");
            bluelab = new JLabel("Enter blue block number");
            randomPanel = new JPanel();

            redlab.setSize(170, 40);
            redlab.setLocation(10, 0);
            greenlab.setSize(170, 40);
            greenlab.setLocation(10, 50);
            bluelab.setSize(170, 40);
            bluelab.setLocation(10, 100);

            redinput.setSize(50,40);
            greeninput.setSize(50,40);
            blueinput.setSize(50,40);
        
            redinput.setLocation(180, 0);
            greeninput.setLocation(180, 50);
            blueinput.setLocation(180, 100);

            randomPanel.setLayout(null);
            randomPanel.setPreferredSize(new Dimension(230, 200));;
            randomPanel.setBackground(Color.LIGHT_GRAY);
            randomPanel.setOpaque(true);
            randomPanel.setLocation(0, 700);

            randomButton.setSize(50,50);  
            randomButton.setLocation(90, 140); 
            randomButton.addActionListener(this);

            randomPanel.add(redlab);
            randomPanel.add(greenlab);
            randomPanel.add(bluelab);

            randomPanel.add(randomButton);

            randomPanel.add(redinput);
            randomPanel.add(greeninput);
            randomPanel.add(blueinput);
    
            blockChooserPanel.add(randomPanel);
            this.setVisible(true);
        }

        private void initializeBlockChooser() {
            String[] colors = {"Red", "Blue", "Green"};
            for (String color : colors) {
                JButton button = new JButton("Place " + color + " Block");
                // button.addActionListener(e -> mapPanel.setSelectedColor(color.toLowerCase()));
                button.addActionListener(e -> {
                    mapPanel.setSelectedColor(color.toLowerCase());
                    infoTextArea.append("Selected " + color + " block.\n");  // Update text area
                });
                blockChooserPanel.add(button);
            }

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridLayout(0, 1));  // Arrange buttons vertically

            // JButton saveButton = new JButton("Save Map");
            // saveButton.addActionListener(e -> mapPanel.saveMap("map_data.dat"));
            // buttonPanel.add(saveButton);

            // JButton loadButton = new JButton("Load Map");
            // loadButton.addActionListener(e -> mapPanel.loadMap("map_data.dat"));
            // buttonPanel.add(loadButton);

            
            JButton saveButton = new JButton("Save Map");
            saveButton.addActionListener(e -> mapPanel.saveMap());
            buttonPanel.add(saveButton);

            JButton loadButton = new JButton("Load Map");
            loadButton.addActionListener(e -> mapPanel.loadMap());
            buttonPanel.add(loadButton);

            blockChooserPanel.add(buttonPanel, BorderLayout.NORTH);
        }

        public void appendInfoText(String text) {
            infoTextArea.append(text + "\n");
        }

        public static void main(String[] args) {
            SwingUtilities.invokeLater(MapDesigner::new);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == randomButton){
                try{
                int rednum = Integer.parseInt(redinput.getText());
                int greennum = Integer.parseInt(greeninput.getText());
                int bluenum = Integer.parseInt(blueinput.getText());
                mapPanel.setSelectedColor("red");
                for (int i = 0; i < rednum; i++) {
                    boolean blockPlaced = false;
                    while (!blockPlaced) {
                    
                        int x = (int) (Math.random() * 1201); 
                        int y = (int) (Math.random() * 701); 
                        blockPlaced = mapPanel.addBlock(x, y);
                    }
                }
                mapPanel.setSelectedColor("green");
                for (int i = 0; i < greennum; i++) {
                    boolean blockPlaced = false;
                    while (!blockPlaced) {
                    
                        int x = (int) (Math.random() * 1201); 
                        int y = (int) (Math.random() * 701); 
                        blockPlaced = mapPanel.addBlock(x, y);
                    }
                }
                mapPanel.setSelectedColor("blue");
                for (int i = 0; i < bluenum; i++) {
                    boolean blockPlaced = false;
                    while (!blockPlaced) {
                    
                        int x = (int) (Math.random() * 1201); 
                        int y = (int) (Math.random() * 701); 
                        blockPlaced = mapPanel.addBlock(x, y);
                    }
                }
                
                }
                catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Please enter valid numbers", "Message", JOptionPane.PLAIN_MESSAGE);
                }

                
             }
        }
    }


    class MapPanel extends JPanel {
        private ArrayList<ColoredBlock> blocks;
        private String selectedColor = "red";  // Default color
        private static final int BLOCK_WIDTH = 100; // Width of the block
        private static final int BLOCK_HEIGHT = 20; // Height of the block
        private final MapDesigner frame;


        public MapPanel(MapDesigner frame) {
            this.frame = frame; 
            this.blocks = new ArrayList<>();
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Check if the click is within the upper half of the panel
                    // if (e.getY() < getHeight() / 1.3) {
                    //     addBlock(e.getX(), e.getY());
                    //     repaint();
                    // }
                    if (SwingUtilities.isRightMouseButton(e)) {
                        showContextMenu(e.getX(), e.getY(), e);
                    } else if (SwingUtilities.isLeftMouseButton(e) && e.getY() < getHeight() / 1.2) {
                        if (addBlock(e.getX(), e.getY())) {
                            frame.appendInfoText("Placed " + selectedColor + " block at (" + e.getX() + ", " + e.getY() + ")");
                        } else {
                            frame.appendInfoText("Failed to place block at (" + e.getX() + ", " + e.getY() + ") - Overlap");
                        }
                        repaint();
                    }

                }
            });
        }

        private void showContextMenu(int x, int y, MouseEvent e) {
            for (ColoredBlock block : blocks) {
                if (block.rectangle.contains(x, y)) {
                    JPopupMenu contextMenu = new JPopupMenu();
                    JMenuItem deleteItem = new JMenuItem("Delete");
                    deleteItem.addActionListener(ev -> {
                        blocks.remove(block);
                        repaint();
                        frame.appendInfoText("Deleted " + block.color + " block at (" + block.rectangle.x + ", " + block.rectangle.y + ")");
                    });
                    contextMenu.add(deleteItem);
                    contextMenu.show(this, x, y);
                    break;
                }
            }
        }

        public void setSelectedColor(String color) {
            this.selectedColor = color;
        }

        public boolean addBlock(int x, int y) {
            int gridX = x - (x % BLOCK_WIDTH);
            int gridY = y - (y % BLOCK_HEIGHT);
            for (ColoredBlock block : blocks) {
                if (block.rectangle.intersects(new Rectangle(gridX, gridY, BLOCK_WIDTH, BLOCK_HEIGHT))) {
                    return false; // Block already exists or overlaps in this area
                }
            }
            blocks.add(new ColoredBlock(new Rectangle(gridX, gridY, BLOCK_WIDTH, BLOCK_HEIGHT), selectedColor));
            return true;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (ColoredBlock block : blocks) {
                switch (block.color) {
                    case "red":
                        g.setColor(Color.RED);
                        break;
                    case "blue":
                        g.setColor(Color.BLUE);
                        break;
                    case "green":
                        g.setColor(Color.GREEN);
                        break;
                    default:
                        g.setColor(Color.BLACK); // Default case
                }
                g.fillRect(block.rectangle.x, block.rectangle.y, block.rectangle.width, block.rectangle.height);
            }
            // Draw a line indicating the maximum Y value for placing blocks
            int maxY = (int)(getHeight() / 1.2);
            g.setColor(Color.GRAY); // Set line color
            g.drawLine(0, maxY, getWidth(), maxY); // Draw line from the left to the right side of the panel

            // Optionally, add a label or some text to explain the line
            g.drawString("Designable area boundary", 5, maxY - 5); // Display text just above the line
        }

        private static class ColoredBlock implements Serializable {
            Rectangle rectangle;
            String color;
    
            ColoredBlock(Rectangle rectangle, String color) {
                this.rectangle = rectangle;
                this.color = color;
            }
        }

        // public void saveMap(String filePath) {
        //     try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
        //         oos.writeObject(blocks);
        //         frame.appendInfoText("Map saved successfully.");
        //     } catch (IOException e) {
        //         frame.appendInfoText("Error saving map: " + e.getMessage());
        //         e.printStackTrace();
        //     }
        // }

        // public void loadMap(String filePath) {
        //     try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
        //         blocks = (ArrayList<ColoredBlock>) ois.readObject();
        //         repaint();
        //         frame.appendInfoText("Map loaded successfully.");
        //     } catch (IOException | ClassNotFoundException e) {
        //         frame.appendInfoText("Error loading map: " + e.getMessage());
        //         e.printStackTrace();
        //     }
        // }

        public void saveMap() {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a file to save");
            int userSelection = fileChooser.showSaveDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileToSave))) {
                    oos.writeObject(blocks);
                    frame.appendInfoText("Map saved successfully to " + fileToSave.getAbsolutePath());
                } catch (IOException e) {
                    frame.appendInfoText("Error saving map: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    
        @SuppressWarnings("unchecked")
        public void loadMap() {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select a file to load");
            int userSelection = fileChooser.showOpenDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToLoad = fileChooser.getSelectedFile();
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileToLoad))) {
                    blocks = (ArrayList<ColoredBlock>) ois.readObject();
                    repaint();
                    frame.appendInfoText("Map loaded successfully from " + fileToLoad.getAbsolutePath());
                } catch (IOException | ClassNotFoundException e) {
                    frame.appendInfoText("Error loading map: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }
