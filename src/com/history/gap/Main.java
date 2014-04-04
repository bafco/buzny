package com.history.gap;

/**
 *
 * @author Matúš
 */
public class Main {

    private static final boolean DETAILED = true;

    public static void main(String[] args) {
        Stock stock = GapParser.generateStockFromFile("D:\\buzny\\nflx.txt", DETAILED);
        System.out.println(stock);
    }
}
