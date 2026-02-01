package org.example.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Scanner;

import org.junit.jupiter.api.Test;

public class SingletonScannerTest {
    
    @Test
    public void testPositiveSingletonScannerAssertSame() {

        Scanner sc1 = SingletonScanner.getInstance().getScanner();
        Scanner sc2 = SingletonScanner.getInstance().getScanner();

        assertEquals(sc1, sc2);
    }
}
