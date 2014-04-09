package com.history.gap;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Matúš
 */
public class DetailedGap  extends Gap {

    // if gap is less than abs(TRESHOLD), second day profit/loss is irrelevant and set to null
    public static final float TRESHOLD = 3;

    public static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");

    final float secondDayHigh;
    final float secondDayLow;
    final float secondDayClose;
    final Date firstDay;
    final Date secondDay;

    final float sdHighInPercent;
    // negative
    final float sdLowInPercent;
    final float sdCloseInPercent;

    // second day extremes in %
    final Float secondDayMaxProfit;
    //negative to indicate it is a loss
    final Float secondDayMaxLoss;
    // second day close relative to second day open, negative if against the direction of the gap
    final Float secondDayProfitInClose;

    public DetailedGap(float fdClose, float sdOpen, float sdHigh, float sdLow, float sdClose, Date firstDay, Date secondDay) {
        super(fdClose, sdOpen);
        secondDayHigh = sdHigh;
        secondDayLow = sdLow;
        secondDayClose = sdClose;
        this.firstDay = firstDay;
        this.secondDay = secondDay;
        sdHighInPercent = getRelativeToSecondDayOpenInPercent(secondDayHigh);
        sdLowInPercent = getRelativeToSecondDayOpenInPercent(secondDayLow);
        sdCloseInPercent = getRelativeToSecondDayOpenInPercent(secondDayClose);
        if (Math.abs(gap) < TRESHOLD) {
            secondDayMaxProfit = secondDayMaxLoss = secondDayProfitInClose = null;
        } else {
            secondDayMaxProfit = gap > 0 ? sdHighInPercent : -sdLowInPercent;
            secondDayMaxLoss = gap < 0 ? -sdHighInPercent : sdLowInPercent;
            secondDayProfitInClose = gap > 0 ? sdCloseInPercent : -sdCloseInPercent;
        }
    }

    private float getRelativeToSecondDayOpenInPercent(float f) {
        return (f - secondDayOpen) / secondDayOpen * 100;
    }

    @Override
    public String toString() {
        return super.toString() + dateToString() + getHLCtoString() + getGainLossCloseProfitToString();
    }

    private String dateToString() {
        return " (date: " + SDF.format(firstDay) + " - " + SDF.format(secondDay) + ")";
    }

    private String getHLCtoString() {
        return " # HLC: H = " + format(sdHighInPercent) + " | L = " + format(sdLowInPercent) + " | C = " + format(sdCloseInPercent);
    }

    private String getGainLossCloseProfitToString() {
        return " # GLCp: G = " + format(secondDayMaxProfit) + " | L = " + format(secondDayMaxLoss) + " | Cp = " + format(secondDayProfitInClose);
    }
}
