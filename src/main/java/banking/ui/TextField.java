package banking.ui;

import javax.swing.*;

public class TextField extends JTextField {
    TextField(String text, int x, int y, int width, int height){
        super(text);
        this.setBounds(x,y,width,height);
    }
}
