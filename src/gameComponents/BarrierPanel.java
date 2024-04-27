package gameComponents;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class BarrierPanel extends JPanel{
    int x;
    int y;
    Image img;
    BarrierPanel(String imgpath, int x, int y){

        //horizontal distance (y) between barriers should be approximately 40
        this.setSize(86,26);

        if (imgpath.equals("assets/images/200iconredgem.png") || imgpath.equals("assets/images/200icongreengem.png")){
            //explosive and rewarding barrier are slightly large
            this.setSize(86,36);
        }
        this.setBackground(Color.GRAY);
        img = new ImageIcon(imgpath).getImage();

        //since sizes are different on some barriers, location is adjusted
        this.setLocation(x, y+10);

        if (imgpath.equals("assets/images/200iconbluegem.png") || imgpath.equals("assets/images/200iconfirm.png")){
            this.setLocation(x, y+15);;
        }

        if (imgpath.equals("assets/images/200iconredgem.png")){
            this.setLocation(x, y+8);
        }

    }

    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(img, 0, 0, null);
    }
}
