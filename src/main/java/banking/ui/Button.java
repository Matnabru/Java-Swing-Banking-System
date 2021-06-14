package banking.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Button extends JButton {
    Button(String text, int x, int y, int width, int height){
        super();
        this.setText(text);
        this.setBounds(x,y,width,height);

    }
}
