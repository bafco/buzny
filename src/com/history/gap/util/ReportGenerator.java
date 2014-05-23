package com.history.gap.util;

import com.history.gap.DetailedGap;
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

    public static void generateStatistics() {
        final float tresholdForGap = 3;
        final float tresholdForHold = 0;
        // 2 for last gap, 3 for the last but one gap, ...
        final int which_gap = 2;

        int counterGap = 0;
        float gainGap = 0f;
        int counterHold = 0;
        float gainHold = 0f;
        for (Stock stock : getAllStocks()) {
            if (stock.getGaps().size() < 6) {
                continue;
            }
            stock.computeGapsResults();
            float averageGap = stock.gapsResults[Stock.GAP_INDEX][Stock.AVERAGE_INDEX];
            if (Math.abs(averageGap) > tresholdForGap) {
                System.out.println(stock.symbol);
                counterGap++;
                gainGap += (averageGap > 0 ? 1 : -1) * stock.gapsResults[Stock.GAP_INDEX][which_gap];
            }

            float averageGainOnHold = stock.gapsResults[Stock.SD_PROFIT_IN_CLOSE_INDEX][Stock.AVERAGE_INDEX];
            if (//averageGainOnHold > tresholdForHold &&
                    Math.abs(stock.gapsResults[Stock.GAP_INDEX][which_gap]) > DetailedGap.TRESHOLD) {
                counterHold++;
                gainHold += stock.gapsResults[Stock.SD_PROFIT_IN_CLOSE_INDEX][which_gap];
            }


        }
        System.out.println("Cummulative gain on gap: " + gainGap + " (counter = " + counterGap + ")");
        System.out.println("Average gain: " + gainGap/counterGap);
        System.out.println();
        System.out.println("Cummulative gain on hold: " + gainHold + " (counter = " + counterHold + ")");
        System.out.println("Average gain: " + gainHold/counterHold);
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
