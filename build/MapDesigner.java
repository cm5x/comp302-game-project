    import javax.swing.*;
    import java.awt.*;
    import java.awt.event.MouseAdapter;
    import java.awt.event.MouseEvent;
    import java.util.ArrayList;

    public class MapDesigner extends JFrame {
        private final MapPanel mapPanel;
        private final JPanel blockChooserPanel;
        private final JTextArea infoTextArea;

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
            this.blockChooserPanel.setPreferredSize(new Dimension(250, 600));
            this.blockChooserPanel.setBackground(Color.LIGHT_GRAY);  // Differentiate by color
            initializeBlockChooser();
            this.add(blockChooserPanel, BorderLayout.WEST);

            // Info text area
    
            infoTextArea = new JTextArea(5, 20);
            infoTextArea.setEditable(false);  // Make text area non-editable
            infoTextArea.setLineWrap(rootPaneCheckingEnabled);
            JScrollPane scrollPane = new JScrollPane(infoTextArea);
            blockChooserPanel.add(scrollPane, BorderLayout.SOUTH);

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
        }

        public void appendInfoText(String text) {
            infoTextArea.append(text + "\n");
        }

        public static void main(String[] args) {
            SwingUtilities.invokeLater(MapDesigner::new);
        }
    }


    class MapPanel extends JPanel {
        private final ArrayList<ColoredBlock> blocks;
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
                    if (e.getY() < getHeight() / 1.2) {
                        if (addBlock(e.getX(), e.getY())) {
                            frame.appendInfoText("Placed " + selectedColor + " block at (" + e.getX() + ", " + e.getY() + ")");
                        } else {
                            frame.appendInfoText("Failed to place block at (" + e.getX() + ", " + e.getY() + ") - Overlap");
                        }
                        repaint();
                    }
                    else{

                        frame.appendInfoText("Placing Failed, please stay in restricted area.");
                    }
                }
            });
        }

        public void setSelectedColor(String color) {
            this.selectedColor = color;
        }

        private boolean addBlock(int x, int y) {
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

        private static class ColoredBlock {
            Rectangle rectangle;
            String color;

            ColoredBlock(Rectangle rectangle, String color) {
                this.rectangle = rectangle;
                this.color = color;
            }
        }
    }

