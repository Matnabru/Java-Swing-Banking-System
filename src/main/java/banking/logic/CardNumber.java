package banking.logic;

import java.util.stream.IntStream;

public class CardNumber {
    private final String number;
    private final String pin;

    public CardNumber() {
        number = generateNumber();
        pin = generatePin();
    }

    private String generatePin() {
        return String.format("%4d", (int) (Math.random() * 10000)).replace(' ', '0');
    }

    private String generateNumber() {
        final String num = String.format("400000%9d", (long) (Math.random() * 1000000000L)).replace(' ', '0');
        return generateChecksum(num);
    }

    public static String generateChecksum(String num) {
        final int v = IntStream.rangeClosed(0, 14)
                .map(e -> e % 2 == 0 ? num.charAt(e) - '0' << 1 : num.charAt(e) - '0')
                .map(e -> e > 9 ? e - 9 : e)
                .sum();
        return num + (10 - v % 10) % 10;

    }

    public String getNumber() {
        return number;
    }

    public String getPin() {
        return pin;
    }
}