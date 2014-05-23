package com.history.gap.util;

import com.history.gap.Stock;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Matúš
 */
public class ReportGenerator {

    public static final String GENERATED = "generated\\";
    public static final String OUT = "_out";

    private ReportGenerator() {
    }

    public static void generateReport() {
        for (Stock stock : getAllStocks()) {
            try (PrintWriter writer = new PrintWriter(GapParser.FILES_LOCATION + GENERATED + stock.symbol + OUT + GapParser.TXT, "UTF-8")) {
                writer.write(stock.toString());
            } catch (FileNotFoundException | UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static List<Stock> getAllStocks() {
        List<Stock> stocks = new LinkedList<>();
        for (File file : listTextFiles()) {
            stocks.add(GapParser.generateStockFromFileName(file.getPath(), true));
        }
        return stocks;
    }

    private static File[] listTextFiles() {
        File dir = new File(GapParser.FILES_LOCATION);

        return dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                return filename.endsWith(GapParser.TXT);
            }
        });
    }
}
