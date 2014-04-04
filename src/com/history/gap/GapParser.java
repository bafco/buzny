package com.history.gap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author Matúš
 */
public class GapParser {

    private static final int DATE = 0;
    private static final int OPEN = 1;
    private static final int HIGH = 2;
    private static final int LOW = 3;
    private static final int CLOSE = 4;

    private static final int ROW_LENGTH = 7;

    private static final String TAB = "\t";

    private GapParser() {
    }

    public static Stock generateStockFromFile(String fileName, boolean detailed) {
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
                gap = gimmeGap(line1, line2, detailed);
                stock.addGap(gap);
                input.readLine();
            }
            return stock;
        } catch (IOException | IllegalStateException e) {
            throw new RuntimeException(e);
        }
    }

    private static Gap gimmeGap(String line1, String line2, boolean detailed) {
        String[] secondDay = line1.split(TAB, ROW_LENGTH);
        String[] firstDay = line2.split(TAB, ROW_LENGTH);
        return detailed ?
                new DetailedGap(gimmeNumber(firstDay[CLOSE]), gimmeNumber(secondDay[OPEN]), gimmeNumber(secondDay[HIGH]), gimmeNumber(secondDay[LOW]), gimmeNumber(firstDay[CLOSE]), gimmeDate(firstDay[DATE]), gimmeDate(secondDay[DATE])) :
                new Gap(gimmeNumber(firstDay[CLOSE]), gimmeNumber(secondDay[OPEN]));
    }

    private static Float gimmeNumber(String s) {
        return Float.parseFloat(s);
    }

    private static Date gimmeDate(String s) {
        try {
            return new SimpleDateFormat("MMM d, yyyy", Locale.ENGLISH).parse(s);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
