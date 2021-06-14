package banking.ui;

import banking.logic.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserFrame extends JFrame {
    private BackgroundPanel backgroundPanel;
    private User user;
    private Label balance;
    private TextField income;
    private Button incomeButton;

    private TextField transferAccountTextField;
    private TextField transferAmountTextField;
    private Button transferButton;
    private Button closeAccountButton;
    private Button logoutButton;
    UserFrame(int width, int height, User _user){
        super();
        this.user = _user;
        // Background image
        backgroundPanel = new BackgroundPanel(width,height);

        // Balance label
        balance = new Label("Balance : "+ user.getBalance(),50,50,400,45);
        this.add(balance);

        // Income button & textfield
        incomeUI();

        // Transfer UI
        transferUI();

        closeAccountUI();

        logoutUI();

        setDefaultValues();

        this.add(backgroundPanel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    private void transferUI(){
        transferAccountTextField = new TextField("",50,200,200,45);
        this.add(transferAccountTextField);
        transferAmountTextField = new TextField("",270,200,80,45);
        this.add(transferAmountTextField);
        transferButton = new Button("Transfer",360,200,120,45);
        transferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(!user.setTransfer(transferAccountTextField.getText(),transferAmountTextField.getText())){
                    messageDialog("Invalid transfer credentials.\nCheck if you have enough funds or if account number is correct");
                }
                else {
                    setDefaultValues();
                    refreshBalanceLabel();
                }
            }
        });
        this.add(transferButton);
    }
    private void incomeUI(){
        income = new TextField("",50,120,300,45);
        this.add(income);
        incomeButton = new Button("Add income",360,120,120,45);
        incomeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                user.setIncome(income.getText());
                income.setText("");
                setDefaultValues();
                refreshBalanceLabel();
            }
        });
        this.add(incomeButton);
    }
    private void refreshBalanceLabel(){
        balance.setText("Balance : "+ user.getBalance());
        SwingUtilities.updateComponentTreeUI(this);
    }
    private void closeAccountUI(){
        closeAccountButton = new Button("Close account",50,300,400,45);
        closeAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                user.closeAccount();
                UserFrame.super.dispose();
            }
        });
        this.add(closeAccountButton);
    }
    private void logoutUI(){
        logoutButton = new Button("Logout",50,360,400,45);
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                UserFrame.super.dispose();
            }
        });
        this.add(logoutButton);
    }
    private void messageDialog(String message){
        JOptionPane.showMessageDialog(this, message);
    }
    private void setDefaultValues(){
        income.setText("0");
        transferAccountTextField.setText("4000000000000000");
        transferAmountTextField.setText("0");
    }
}
