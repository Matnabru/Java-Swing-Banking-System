package banking.ui;


import banking.logic.BankDB;
import banking.logic.CardNumber;
import banking.logic.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private BackgroundPanel backgroundPanel;
    private Button loginButton;
    private Button registerButton;
    private TextField cardTextField;
    private TextField pinTextField;
    private Label cardLabel;
    private Label pinLabel;
    private Label loginFailureLabel;

    private User user;
    private final BankDB bank;

    private int width;
    private int height;
    public LoginFrame(int _width,int _height, BankDB bank){
        super();
        this.bank = bank;
        this.width = _width;
        this.height = _height;

        backgroundPanel = new BackgroundPanel(width,height);

        loginUI();
        buttonUI();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(backgroundPanel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    private void loginUI(){
        // Add text fields
        cardTextField = new TextField("",110,150,280,45);
        this.add(cardTextField);
        pinTextField = new TextField("",110,260,280,45);
        this.add(pinTextField);

        // Add labels
        cardLabel = new Label("Card number",100,100,300,45);
        this.add(cardLabel);
        pinLabel = new Label("Pin",100,210,300,45);
        this.add(pinLabel);

        loginFailureLabel = new Label("Wrong credentials",100,50,300,45);
        loginFailureLabel.setVisible(false);
        loginFailureLabel.setForeground(Color.red);
        this.add(loginFailureLabel);
    }
    private void buttonUI(){
        // Add buttons
        loginButton = new Button("Login", 100,350,300,45);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                if(!bank.login(cardTextField.getText(),pinTextField.getText())){
                    // FAILURE
                    updateInvalidCredentials(true);
                }
                else {
                    // SUCCESS
                    updateInvalidCredentials(false);
                    user = bank.getUser(cardTextField.getText(),pinTextField.getText());
                    new UserFrame(width,height,user);
                }
                cardTextField.setText("");
                pinTextField.setText("");
            }
        });


        loginButton.getActionListeners();
        this.add(loginButton);
        registerButton = new Button("Register", 100,410,300,45);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                CardNumber card = bank.createCard();
                messageDialog("Your card has been created\n" + "Your card number:\n" + card.getNumber() + "\n" +
                        "Your card PIN:\n" + card.getPin() + "\n");
                System.out.println("Your card has been created");
                System.out.println("Your card number:\n" + card.getNumber() + "\n");
                System.out.println("Your card PIN:\n" + card.getPin() + "\n");
            }
        });
        this.add(registerButton);
    }
    private void updateInvalidCredentials(boolean isVisible){
        loginFailureLabel.setVisible(isVisible);
        SwingUtilities.updateComponentTreeUI(this);

    }
    private void messageDialog(String message){
        JOptionPane.showMessageDialog(this, message);
    }
}
