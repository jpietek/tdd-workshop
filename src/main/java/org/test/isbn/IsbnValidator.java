package org.test.isbn;

import java.util.stream.IntStream;

public class IsbnValidator {

    public static final int LONG_ISBN = 13;
    public static final int SHORT_ISBN = 10;
    public static final int SHORT_ISBN_MULTIPLIER = 11;
    public static final int LONG_ISBN_MULTIPLIER = 10;

    public boolean checkShortISBN(String isbn) {
        Integer.parseInt(isbn.substring(0, 9));

        int sum = IntStream.range(0, SHORT_ISBN).map(i ->
                isbn.charAt(i) == 'X' && i == 9 ? 10 : Character.getNumericValue(isbn.charAt(i)) * (SHORT_ISBN - i)
        ).sum();

        return sum % SHORT_ISBN_MULTIPLIER == 0;
    }

    public boolean checkLongISBN(String isbn) {
        int sum = IntStream.range(0, LONG_ISBN).map(i ->
                Character.getNumericValue(isbn.charAt(i)) * (i % 2 == 0 ? 1 : 3)
        ).sum();

        return sum % LONG_ISBN_MULTIPLIER == 0;
    }

    public boolean checkISBN(String isbn) {
        if (isbn.length() == SHORT_ISBN) {
            return checkShortISBN(isbn);
        } else if (isbn.length() == LONG_ISBN) {
            return checkLongISBN(isbn);
        }

        throw new NumberFormatException();
    }

}
