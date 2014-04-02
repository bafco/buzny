package com.history.gap;

/**
 *
 * @author Matúš
 */
public class Main {

    public static void main(String[] args) {
        Stock stock = GapParser.generateStockFromFile("D:\\buzny\\p.txt");
        System.out.println(stock.name);
        System.out.println(stock.getAverageReturn());
    }
}
