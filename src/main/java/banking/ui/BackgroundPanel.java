package banking.ui;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {
    private Image img;
    BackgroundPanel(int width, int height){
        String filepath = "src/main/java/banking/images/";
        this.setPreferredSize(new Dimension(width,height));
        this.img = new ImageIcon(filepath + "pig.jpg").getImage();
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(img,0,0,null);

    }
}
