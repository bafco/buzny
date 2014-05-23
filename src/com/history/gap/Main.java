package com.history.gap;

import com.history.gap.util.GapParser;
import com.history.gap.util.ReportGenerator;

/**
 *
 * @author Matúš
 */
public class Main {

    private static final boolean DETAILED = true;

    public static void main(String[] args) {
//        showInfoForStock("arun");
//        ReportGenerator.generateReport();
        ReportGenerator.generateStatistics();
    }

    private static void showInfoForStock(String symbol) {
        System.out.println(GapParser.generateStockFromStockName(symbol, DETAILED));
    }
}
