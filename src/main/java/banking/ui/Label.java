package banking.ui;

import javax.swing.*;
import java.awt.*;

public class Label extends JLabel {
    Label(String text, int x, int y, int width, int height){
        // Center text - SwingConstants.CENTER
        super(text,SwingConstants.CENTER);
        this.setFont(this.getFont ().deriveFont (34.0f));
        this.setForeground(Color.white);
        this.setVisible(true);
        this.setBounds(x,y,width,height);
    }
}
