package com.history.gap;

import java.text.DecimalFormat;

/**
 *
 * @author Matúš
 */
public class Gap {

    // gap (in %) needed for profit
    private static final int TRESHOLD_GAP = 5;
    private static final int FRACTION_DIGITS = 2;

    static DecimalFormat df = new DecimalFormat() {
        {
            setMinimumFractionDigits(FRACTION_DIGITS);
            setMaximumFractionDigits(FRACTION_DIGITS);
        };
    };

    final float firstDayClose;
    final float secondDayOpen;
    /**
     * gap in %, can be negative
     */
    final float gap;
    final float profit;

    final Float profitOnBuy;
    final Float profitOnSell;

    public Gap(float close, float open) {
        firstDayClose = close;
        secondDayOpen = open;
        gap = (secondDayOpen - firstDayClose) / firstDayClose * 100;
        profit = Math.max(Math.abs(gap) - TRESHOLD_GAP, 0);

        profitOnBuy = Math.max(gap, -5f);
        profitOnSell = Math.max(-gap, -5f);
    }

    @Override
    public String toString() {
        return format(gap);
    }

    public static String format(Float f) {
        // 4 stands for potential minus sign, 2 digits and decimal point
        return String.format("%1$" + (FRACTION_DIGITS + 4) + "s", f == null ? f : df.format(f));
    }
}
