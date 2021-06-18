package banking.logic;

import org.sqlite.SQLiteDataSource;

import java.sql.SQLException;

public class User {
    private final SQLiteDataSource dataSource;
    private final int id;


    User(SQLiteDataSource dataSource, int id){
        this.dataSource = dataSource;
        this.id = id;
    }
    public void closeAccount() {

        try (var c = dataSource.getConnection()) {
            try (var st = c.createStatement()) {
                st.executeUpdate("DELETE FROM card WHERE id = " + id);
            }
        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean setTransfer(String account, String value) {

        final String toNumber = account;

        if (!cardCheck(toNumber)) {
            // Invalid card number
            return false;
        }

        final int amount = Integer.parseInt(value);

        if (getBalance() < amount) {
            // Not enough money
            return false;
        }

        try (var c = dataSource.getConnection()) {
            try (var st = c.createStatement()) {
                st.executeUpdate("UPDATE card SET balance = balance + " + amount + " WHERE number = " + toNumber);
                st.executeUpdate("UPDATE card SET balance = balance - " + amount + " WHERE id = " + id);
                System.out.println("Success!\n");
            }
        } catch (final SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    private boolean cardCheck(String toNumber) {

        if (toNumber.length() != 16 || !CardNumber.generateChecksum(toNumber.substring(0, 15)).equals(toNumber)) {

            System.out.println("Probably you made mistake in the card number. Please try again!\n");
            return false;
        }

        try (var c = dataSource.getConnection()) {
            try (var st = c.createStatement()) {
                final var ans = st.executeQuery("select * from card WHERE number = " + toNumber);

                if (!ans.next()) {
                    System.out.println("Such a card does not exist.\n");
                    return false;
                }

                if (id == ans.getInt("id")) {
                    System.out.println("You can't transfer money to the same account!\n");
                    return false;
                }
            }
        } catch (final SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    public void setIncome(String strIncome) {
        final int income = Integer.parseInt(strIncome);

        try (var c = dataSource.getConnection()) {
            try (var st = c.createStatement()) {
                st.executeUpdate("UPDATE card SET balance = balance + " + income + " WHERE id = " + id);
            }
        } catch (final SQLException e) {
            e.printStackTrace();
        }

    }

    public int getBalance() {

        try (var c = dataSource.getConnection()) {
            try (var st = c.createStatement()) {
                try (var card = st.executeQuery("SELECT * FROM card WHERE id = '" + id + "'")) {
                    if (card.next()) {
                        return card.getInt("balance");
                    }
                }
            }
        } catch (final SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
