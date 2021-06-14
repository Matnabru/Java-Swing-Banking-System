package banking.logic;

import org.sqlite.SQLiteDataSource;

import java.sql.SQLException;
import java.util.Scanner;

public class BankDB {
    Scanner sc = new Scanner(System.in);
    SQLiteDataSource dataSource;

    public BankDB(String _dbname){
        final String dbname = _dbname;
        createDB(dbname);
    }

    private void createDB(String dbname) {
        final String url = "jdbc:sqlite:" + dbname;

        dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);

        try (var c = dataSource.getConnection()) {
            try (var st = c.createStatement()) {
                st.executeUpdate("CREATE TABLE IF NOT EXISTS card (" +
                        "id INTEGER PRIMARY KEY, " +
                        "number TEXT, " +
                        "pin TEXT, " +
                        "balance INTEGER DEFAULT 0" + ")");
            }
        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean login(String cardNumber, String cardPIN){
        if(loginCheck(cardNumber,cardPIN) == -1 ){
            return false;
        }
        return true;
    }
    private int loginCheck(String cardNumber, String cardPIN) {
        try (var c = dataSource.getConnection()) {
            try (var st = c.createStatement()) {
                try (var card = st.executeQuery(
                        String.format("SELECT * FROM card WHERE number = '%s' and pin = '%s'", cardNumber, cardPIN))) {
                    if (card.next()) {
                        return card.getInt("id");
                    }
                }
            }
        } catch (final SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }
    public User getUser(String cardNumber, String cardPIN){
        User user = new User(dataSource,loginCheck(cardNumber,cardPIN));
        return user;
    }
    public CardNumber createCard() {
        final CardNumber card = new CardNumber();

        try (var c = dataSource.getConnection()) {
            try (var st = c.createStatement()) {
                st.executeUpdate(String.format("INSERT INTO card (number, pin) VALUES ('%s', '%s')",
                        card.getNumber(),
                        card.getPin()));
            }
        } catch (final SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Your card has been created");
        System.out.println("Your card number:\n" + card.getNumber() + "\n");
        System.out.println("Your card PIN:\n" + card.getPin() + "\n");
        return card;
    }
}
