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
        ReportGenerator.generateReport();
        Stock stock = GapParser.generateStockFromStockName("arun", DETAILED);
        System.out.println(stock);
    }
}
