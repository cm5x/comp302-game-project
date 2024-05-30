package view;

import javax.swing.*;

import gameComponents.Barrier;
import gameComponents.ExplosiveBarrier;
import gameComponents.RewardingBarrier;
import gameComponents.SimpleBarrier;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
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
import utilities.FrameCloseListener;




    public class MapDesigner extends JFrame implements ActionListener{
        private final MapPanel mapPanel;
        private final JPanel blockChooserPanel;
        private final JTextArea infoTextArea;

        JButton randomButton;
        JTextField redinput;
        JTextField blueinput;
        JTextField greeninput;
        JTextField rewardingb;

        JLabel redlab;
        JLabel bluelab;
        JLabel greenlab;
        JLabel rewlab;
        JPanel randomPanel;
        int limitcounter;

        public MapDesigner() {
            super("Map Designer");
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setLayout(new BorderLayout());
            this.setSize(1920, 1080);
            limitcounter = 0;

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
            rewardingb = new JTextField();

            randomButton = new JButton("Build");
            redlab = new JLabel("Simple barrier");
            greenlab = new JLabel("Reinforced barrier");
            bluelab = new JLabel("Explosive barrier");
            rewlab = new JLabel("Rewarding barrier");
            randomPanel = new JPanel();

            redlab.setSize(170, 40);
            redlab.setLocation(10, 0);
            greenlab.setSize(170, 40);
            greenlab.setLocation(10, 50);
            bluelab.setSize(170, 40);
            bluelab.setLocation(10, 100);
            rewlab.setSize(170, 40);
            rewlab.setLocation(10, 150);

            redinput.setSize(50,40);
            greeninput.setSize(50,40);
            blueinput.setSize(50,40);
            rewardingb.setSize(50, 40);
        
            redinput.setLocation(180, 0);
            greeninput.setLocation(180, 50);
            blueinput.setLocation(180, 100);
            rewardingb.setLocation(180, 150);

            randomPanel.setLayout(null);
            randomPanel.setPreferredSize(new Dimension(230, 400));;
            randomPanel.setBackground(Color.LIGHT_GRAY);
            randomPanel.setOpaque(true);
            randomPanel.setLocation(0, 700);

            randomButton.setSize(50,50);  
            randomButton.setLocation(90, 200); 
            randomButton.addActionListener(this);

            randomPanel.add(redlab);
            randomPanel.add(greenlab);
            randomPanel.add(bluelab);
            randomPanel.add(rewlab);

            randomPanel.add(randomButton);

            randomPanel.add(redinput);
            randomPanel.add(greeninput);
            randomPanel.add(blueinput);
            randomPanel.add(rewardingb);
    
            blockChooserPanel.add(randomPanel);
            this.setVisible(true);
        }

        private void initializeBlockChooser() {
            String[] barrs = {"simple", "reinforced", "explosive", "rewarding"};
            for (String barrier : barrs) {
                JButton button = new JButton("Place " + barrier + " Block");
                // button.addActionListener(e -> mapPanel.setSelectedColor(color.toLowerCase()));
                button.addActionListener(e -> {
                    mapPanel.setSelectedColor(barrier.toLowerCase());
                    infoTextArea.append("Selected " + barrier + " block.\n");  // Update text area
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
            loadButton.addActionListener(e -> {
                MapSlotsFrame mapSlotsFrame = new MapSlotsFrame(mapPanel);
                mapSlotsFrame.setVisible(true);
            });
            buttonPanel.add(loadButton);

            JButton contButton = new JButton("Continue");
            contButton.addActionListener(this);
            //bunun içine de yazabilirsin actionperformed kısmına da 
            buttonPanel.add(contButton);

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
                int rewnum = Integer.parseInt(rewardingb.getText());
                if (rednum < 75){
                    JOptionPane.showMessageDialog(null, "Simple barriers should be more than or equal to 75", "Message", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                if (rednum > 120){
                    JOptionPane.showMessageDialog(null, "Too much simple barriers!", "Message", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                if (greennum < 10){
                    JOptionPane.showMessageDialog(null, "Reinforced barriers should be more than or equal to 10", "Message", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                if (greennum > 120){
                    JOptionPane.showMessageDialog(null, "Too much reinforced barriers!", "Message", JOptionPane.PLAIN_MESSAGE);
                    return;
                }

                if (bluenum < 5){
                    JOptionPane.showMessageDialog(null, "Explosive barriers should be more than or equal to 5", "Message", JOptionPane.PLAIN_MESSAGE);
                    return;
                }

                if (bluenum > 40){
                    JOptionPane.showMessageDialog(null, "Too much explosive barriers!", "Message", JOptionPane.PLAIN_MESSAGE);
                    return;
                }

                if (rewnum < 10){
                    JOptionPane.showMessageDialog(null, "Rewarding barriers should be more than or equal to 10", "Message", JOptionPane.PLAIN_MESSAGE);
                    return;
                }

                if (rewnum > 40){
                    JOptionPane.showMessageDialog(null, "Too much rewarding barriers!", "Message", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                if (limitcounter > 150){
                    JOptionPane.showMessageDialog(null, "Barrier limit reached", "Message", JOptionPane.PLAIN_MESSAGE);
            
                }
                
                mapPanel.setSelectedColor("simple");
                for (int i = 0; i < rednum; i++) {
                    if (limitcounter > 150){
                        break;
                    }
                    boolean blockPlaced = false;
                    while (!blockPlaced) {
                        
                        int x = (int) (Math.random() * 1250); 
                        int y = (int) (Math.random() * 700); 
                        blockPlaced = mapPanel.addBlock(x, y,"simple");
                        
                    }
                    limitcounter++;
                }
                mapPanel.setSelectedColor("reinforced");
                for (int i = 0; i < greennum; i++) {
                    if (limitcounter > 150){
                        break;
                    }
                    boolean blockPlaced = false;
                    while (!blockPlaced) {
                        
                    
                        int x = (int) (Math.random() * 1250); 
                        int y = (int) (Math.random() * 700); 
                        blockPlaced = mapPanel.addBlock(x, y,"reinforced");
                    }
                    limitcounter++;
                }
                mapPanel.setSelectedColor("explosive");
                for (int i = 0; i < bluenum; i++) {
                    if (limitcounter > 150){
                        break;
                    }
                    boolean blockPlaced = false;
                    while (!blockPlaced) {
                        
                    
                        int x = (int) (Math.random() * 1250); 
                        int y = (int) (Math.random() * 700); 
                        blockPlaced = mapPanel.addBlock(x, y,"explosive");
                    }
                    limitcounter++;
                }

                mapPanel.setSelectedColor("rewarding");
                for (int i = 0; i < rewnum; i++) {
                    if (limitcounter > 150){
                        break;
                    }
                    boolean blockPlaced = false;
                    while (!blockPlaced) {
                        
                    
                        int x = (int) (Math.random() * 1250); 
                        int y = (int) (Math.random() * 700); 
                        blockPlaced = mapPanel.addBlock(x, y,"rewarding");
                    }
                    limitcounter++;
                }
                
                }
                catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Please enter valid numbers", "Message", JOptionPane.PLAIN_MESSAGE);
                }

                
             }
        }
    }


    class MapPanel extends JPanel implements FrameCloseListener {
        private ArrayList<ColoredBlock> blocks;
        private ArrayList<int[]> barrierList;
        private String selectedColor = "simple";  // Default color
        private static final int BLOCK_WIDTH = 86; // Width of the block
        private static final int BLOCK_HEIGHT = 26; // Height of the block
        private final MapDesigner frame;

        String imgpath1 = "assets/images/200iconbluegem.png";
        String imgpath2 = "assets/images/200iconfirm.png";
        String imgpath3 = "assets/images/200iconredgem.png";
        String imgpath4 = "assets/images/200icongreengem.png";
        String backgroundpath = "assets/images/200background.png";

        Image img1 = new ImageIcon(imgpath1).getImage();
        Image img2 = new ImageIcon(imgpath2).getImage();
        Image img3 = new ImageIcon(imgpath3).getImage();
        Image img4 = new ImageIcon(imgpath4).getImage();
        Image backimg = new ImageIcon(backgroundpath).getImage();

        public MapPanel(MapDesigner frame) {
            this.frame = frame;
            this.blocks = new ArrayList<>();
            this.barrierList = new ArrayList<int[]>();
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
                        if (addBlock(e.getX(), e.getY(),selectedColor)) {
                            frame.appendInfoText("Placed " + selectedColor + " barrier at (" + e.getX() + ", " + e.getY() + ")");
                        } else {
                            frame.appendInfoText("Failed to place barrier at (" + e.getX() + ", " + e.getY() + ") - Overlap");
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
                        frame.appendInfoText("Deleted " + block.color + " barrier at (" + block.rectangle.x + ", " + block.rectangle.y + ")");
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

        public boolean addBlock(int x, int y, String selectedColor) {
            int gridX = x - (x % BLOCK_WIDTH);
            int gridY = y - (y % BLOCK_HEIGHT);
            
            for (ColoredBlock block : blocks) {
                if (block.rectangle.intersects(new Rectangle(gridX, gridY+10, BLOCK_WIDTH, BLOCK_HEIGHT))) {
                    return false;
                }

                if (block.rectangle.intersects(new Rectangle(gridX, gridY-10, BLOCK_WIDTH, BLOCK_HEIGHT))) {
                    return false;
                }

                if (block.rectangle.intersects(new Rectangle(gridX, gridY, BLOCK_WIDTH, BLOCK_HEIGHT))) {
                    return false; // Block already exists or overlaps in this area
                }
            }

        

            switch (selectedColor) {
                case "simple":
                    int[] simpleBarrierArray = new int[]{gridX,gridY,1,1}; //simple barrier
                    barrierList.add(simpleBarrierArray);
                    break;
                case "reinforced":
                    int[] reinforcedBarrierArray = new int[]{gridX,gridY,2,1}; //reinforced barrier
                    barrierList.add(reinforcedBarrierArray);
                    break;
                case "explosive":
                    int[] explosiveBarrierArray = new int[]{gridX,gridY,3,1}; //explosive barrier
                    barrierList.add(explosiveBarrierArray);
                    break;
                case "rewarding":
                    int[] rewardingBarrierArray = new int[]{gridX,gridY,4,1}; //rewarding barrier
                    barrierList.add(rewardingBarrierArray);
                    break;
                default:
                    break;
            }

            blocks.add(new ColoredBlock(new Rectangle(gridX, gridY, BLOCK_WIDTH, BLOCK_HEIGHT), selectedColor));
            return true;
        }

        @Override
        protected void paintComponent(Graphics g) {
            
            super.paintComponent(g);
            g.drawImage(backimg, 0, 0, this.getWidth(), this.getHeight(), this);
            for (ColoredBlock block : blocks) {
                switch (block.color) {
                    case "simple":
                        //g.setColor(Color.RED);
                        g.drawImage(img1, block.rectangle.x, block.rectangle.y, null);
                        break;
                    case "reinforced":
                        //g.setColor(Color.BLUE);
                        g.drawImage(img2, block.rectangle.x, block.rectangle.y, null);
                        break;
                    case "explosive":
                        //g.setColor(Color.GREEN);
                        g.drawImage(img3, block.rectangle.x, block.rectangle.y, null);
                        break;
                    case "rewarding":
                        g.drawImage(img4, block.rectangle.x, block.rectangle.y, null);

                    default:
                        // Default case
                }
              
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

            MapSlotsFrame mapSlotsFrame = new MapSlotsFrame(barrierList, this);
            mapSlotsFrame.setVisible(true);
        
        }
    
        public void loadMap(int mapIndex) {

            this.blocks = new ArrayList<>();
            this.barrierList = new ArrayList<int[]>();

            File fileToLoad = new File("src/gameMapSaves/exampleMap" + mapIndex + ".dat");
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileToLoad))) {

                    barrierList = (ArrayList<int[]>) ois.readObject();
                    System.out.println(barrierList.get(2)[3]);

                    for (int i = 0; i < barrierList.size(); i++) {
                        int[] currentBarrier = barrierList.get(i);
                        switch (barrierList.get(i)[2]) {
                            case 1:
                                addBlock(currentBarrier[0], currentBarrier[1],"simple");
                                break;
                            case 2:
                                addBlock(currentBarrier[0], currentBarrier[1],"reinforced");
                                break;
                            case 3:
                                addBlock(currentBarrier[0], currentBarrier[1],"rewarding");
                                break;
                            case 4:
                                addBlock(currentBarrier[0], currentBarrier[1],"explosive");
                                break;

                            default:
                                break;
                        }
                    }


                    repaint();

                    frame.appendInfoText("Map loaded successfully from " + fileToLoad.getAbsolutePath());
            } catch (IOException | ClassNotFoundException e) {
                    frame.appendInfoText("Error loading map: " + e.getMessage());
                    e.printStackTrace();
            }
        }

        @Override
        public void onFrameClosed(int data) {
            System.out.println("Received data from SecondaryFrame: " + data);
            loadMap(data);
        }
    }