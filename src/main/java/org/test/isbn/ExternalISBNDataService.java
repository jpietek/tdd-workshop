package org.test.isbn;

import java.util.Optional;

public interface ExternalISBNDataService {
    Optional<Book> lookup(String isbn);
}
