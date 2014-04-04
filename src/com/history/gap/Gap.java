package com.history.gap;

import java.text.DecimalFormat;

/**
 *
 * @author Matúš
 */
public class Gap {

    final float firstDayClose;
    final float secondDayOpen;
    /**
     * gap in %, can be negative
     */
    final float gap;
    final float profit;

    public Gap(float close, float open) {
        firstDayClose = close;
        secondDayOpen = open;
        gap = (secondDayOpen - firstDayClose) / firstDayClose * 100;
        profit = Math.max(Math.abs(gap) - 5, 0);
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat();
        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(2);
        return String.format("%1$6s", df.format(gap));
    }
}
