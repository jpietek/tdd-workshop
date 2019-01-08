package org.test.isbn;

import org.junit.Test;
import static org.junit.Assert.*;

public class IsbnValidatorTest {

    @Test
    public void checkValid10ISBN() {
        IsbnValidator validator = new IsbnValidator();
        boolean res = validator.checkISBN("0140449116");
        assertTrue(res);
        boolean res2 = validator.checkISBN("0140177396");
        assertTrue(res2);
    }

    @Test
    public void checkValid13ISBN() {
        IsbnValidator validator = new IsbnValidator();
        boolean res = validator.checkISBN("9780306406157");
        assertTrue(res);
    }

    @Test
    public void checkInvalid13ISBN() {
        IsbnValidator validator = new IsbnValidator();
        boolean res = validator.checkISBN("9780306406158");
        assertFalse(res);
    }

    @Test
    public void checkISBNEndingWithX() {
        IsbnValidator validator = new IsbnValidator();
        boolean res = validator.checkISBN("012000030X");
        assertTrue(res);
    }

    @Test
    public void checkInvaild10ISBN() {
        IsbnValidator validator = new IsbnValidator();
        boolean res = validator.checkISBN("0140449117");
        assertFalse(res);
    }

    @Test(expected = NumberFormatException.class)
    public void invalidNumberOfDigits() {
        IsbnValidator validator = new IsbnValidator();
        validator.checkISBN("123456789");
    }

    @Test(expected = NumberFormatException.class)
    public void nonNumberISBN() {
        IsbnValidator validator = new IsbnValidator();
        validator.checkISBN("helloworld");
    }


}
