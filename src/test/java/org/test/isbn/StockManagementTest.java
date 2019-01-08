package org.test.isbn;

import org.junit.Before;
import org.junit.Test;

import java.security.InvalidParameterException;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class StockManagementTest {

    private static final String EXISTING_ISBN = "0140177396";
    private static final String NOT_EXISTING_ISBN = "0140177397";

    private ExternalISBNDataService webService;
    private ExternalISBNDataService databaseService;
    private StockManager stockManager;

    @Before
    public void setup() {
        webService = mock(ExternalISBNDataService.class);
        databaseService = mock(ExternalISBNDataService.class);

        stockManager = new StockManager();
        stockManager.setWebService(webService);
        stockManager.setDbService(databaseService);
    }

    @Test
    public void testCanGetALocatorCode() {
        when(webService.lookup(EXISTING_ISBN))
                .thenReturn(Optional.of(new Book(EXISTING_ISBN, "Of Mice And Men", "J. Steinback")));

        when(databaseService.lookup(anyString())).thenReturn(Optional.empty());

        Optional<String> codeOptional = stockManager.getLocatorCode(EXISTING_ISBN);
        assertTrue(codeOptional.isPresent());
        assertEquals("7396J4", codeOptional.get());
    }

    @Test
    public void nonExistantISBN() {
        when(webService.lookup(EXISTING_ISBN))
                .thenReturn(Optional.of(new Book(EXISTING_ISBN, "Of Mice And Men", "J. Steinback")));
        when(webService.lookup(NOT_EXISTING_ISBN)).thenReturn(Optional.empty());

        when(databaseService.lookup(anyString())).thenReturn(Optional.empty());

        assertFalse(stockManager.getLocatorCode(NOT_EXISTING_ISBN).isPresent());
    }

    @Test
    public void databaseIsUsedIfDataIsPresentInDatabase() {
        when(databaseService.lookup(EXISTING_ISBN))
                .thenReturn(Optional.of(new Book(EXISTING_ISBN, "abc", "abc")));

        stockManager.getLocatorCode(EXISTING_ISBN);
        verify(databaseService, times(1)).lookup(EXISTING_ISBN);
        verify(webService, never()).lookup(anyString());
    }

    @Test(expected = InvalidParameterException.class)
    public void lookupInvalidBook() {
        when(databaseService.lookup(EXISTING_ISBN))
                .thenReturn(Optional.of(new Book(EXISTING_ISBN, "", "")));

        stockManager.getLocatorCode(EXISTING_ISBN);
    }

    @Test
    public void webserviceIsUsedIfDataIsNotPresent() {
        when(databaseService.lookup(EXISTING_ISBN)).thenReturn(Optional.empty());
        when(webService.lookup(EXISTING_ISBN))
                .thenReturn(Optional.of(new Book(EXISTING_ISBN, "abc", "abc")));

        stockManager.getLocatorCode(EXISTING_ISBN);
        verify(databaseService).lookup(EXISTING_ISBN);
        verify(webService).lookup(anyString());
    }
}

