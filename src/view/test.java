package view;
//delete this later
import javax.swing.JFrame;
import javax.swing.JPanel;

import gameComponents.ExplosiveBarrier;
import gameComponents.ReinforcedBarrier;
import gameComponents.RewardingBarrier;
import gameComponents.SimpleBarrier;

public class test extends JFrame{
    SimpleBarrier barrier = new SimpleBarrier(0,0,0);
    JPanel panel = barrier.getJPanel();
    ReinforcedBarrier barrier2 = new ReinforcedBarrier(0, 258, 0);
    JPanel panel2 = barrier2.getJPanel();
    ExplosiveBarrier barrier3 = new ExplosiveBarrier(172, 0);
    JPanel panel3 = barrier3.getJPanel();
    RewardingBarrier barrier4 = new RewardingBarrier(86, 0);
    JPanel panel4 = barrier4.getJPanel();


    SimpleBarrier arrier = new SimpleBarrier(0,0,40);
    JPanel anel = arrier.getJPanel();
    ReinforcedBarrier arrier2 = new ReinforcedBarrier(0, 258, 40);
    JPanel anel2 = arrier2.getJPanel();
    ExplosiveBarrier arrier3 = new ExplosiveBarrier(172, 40);
    JPanel anel3 = arrier3.getJPanel();
    RewardingBarrier arrier4 = new RewardingBarrier(86, 40);
    JPanel anel4 = arrier4.getJPanel();

    test(){
        this.setSize(1000,700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.add(panel);
        this.add(panel2);
        this.add(panel3);
        this.add(panel4);

        this.add(anel);
        this.add(anel2);
        this.add(anel3);
        this.add(anel4);
        this.setVisible(true);

    }
}
