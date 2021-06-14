import banking.logic.BankDB;
import banking.ui.LoginFrame;

public class Main {
    public static void main(String[] args) {
        final BankDB bankDB = new BankDB("bank.sqlite");
        // Pass bankDB
        new LoginFrame(500,500, bankDB);
    }
}