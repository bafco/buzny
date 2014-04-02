package com.history.gap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Matúš
 */
public class GapParser {

    private GapParser() {
    }

    public static Stock generateStockFromFile(String fileName) {
        try {
            BufferedReader input = new BufferedReader(new FileReader(fileName));
            String name = input.readLine();
            Stock stock = new Stock(name);
            input.readLine();
            
            String line1;
            String line2;
            Gap gap;
            while( (line1 = input.readLine()) != null) {
                line2 = input.readLine();
                if (line2 == null) {
                    throw new IllegalStateException("Incorrect file format. Unexpected end of file.");
                }
                gap = gimmeGap(line1, line2);
                stock.addGap(gap);
                input.readLine();
            }
            return stock;
        } catch (IOException | IllegalStateException e) {
            throw new RuntimeException(e);
        }
    }

    private static Gap gimmeGap(String line1, String line2) {
        String tab = "\t";
        String[] array1 = line1.split(tab, 7);
        String[] array2 = line2.split(tab, 7);
        return new Gap(gimmeNumber(array2[6]), gimmeNumber(array1[1]));
    }

    private static Float gimmeNumber(String s) {
        return Float.parseFloat(s);
    }
}
