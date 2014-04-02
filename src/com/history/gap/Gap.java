package com.history.gap;

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
        gap = (firstDayClose - secondDayOpen) / firstDayClose * 100;
        profit = Math.max(Math.abs(gap) - 5, 0);
    }
}
