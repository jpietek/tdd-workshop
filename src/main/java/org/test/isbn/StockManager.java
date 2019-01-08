package org.test.isbn;

import java.security.InvalidParameterException;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.*;

public class StockManager {

    private ExternalISBNDataService webService;

    private ExternalISBNDataService dbService;

    public void setWebService(ExternalISBNDataService webService) {
        this.webService = webService;
    }

    public void setDbService(ExternalISBNDataService dbService) {
        this.dbService = dbService;
    }

    public Optional<String> getLocatorCode(String isbn) throws InvalidParameterException {
        Optional<Book> optionalBook = dbService.lookup(isbn).or(() -> webService.lookup(isbn));
        if(!optionalBook.isPresent()) {
            return Optional.empty();
        }

        Book book = optionalBook.get();

        StringBuilder locator = new StringBuilder();
        locator.append(isbn.substring(isbn.length() - 4));

        String author = book.getAuthor();
        if(isEmpty(author)) {
            throw new InvalidParameterException("can't calculate locator code out of null/empty author");
        }

        locator.append(author.charAt(0));
        String title = book.getTitle();
        if(isEmpty(title)) {
            throw new InvalidParameterException("can't calculate locator code out of null/empty title");
        }

        locator.append(title.split("\\s+").length);

        return Optional.of(locator.toString());
    }
}
