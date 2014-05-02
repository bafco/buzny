package com.history.gap;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Matúš
 */
public class DetailedGap  extends Gap {

    // if gap is less than abs(TRESHOLD) (in %), second day profit/loss is irrelevant and set to null
    public static final float TRESHOLD = 5;

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
    final Float sdMaxProfit;
    //negative to indicate it is a loss
    final Float sdMaxLoss;
    // second day close relative to second day open, negative if against the direction of the gap
    final Float sdProfitInClose;

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
            sdMaxProfit = sdMaxLoss = sdProfitInClose = null;
        } else {
            sdMaxProfit = gap > 0 ? sdHighInPercent : -sdLowInPercent;
            sdMaxLoss = gap < 0 ? -sdHighInPercent : sdLowInPercent;
            sdProfitInClose = gap > 0 ? sdCloseInPercent : -sdCloseInPercent;
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
        return dateToString(firstDay, secondDay);
    }

    private String getHLCtoString() {
        return getHLCtoString(sdHighInPercent, sdLowInPercent, sdCloseInPercent);
    }

    private String getGainLossCloseProfitToString() {
        return getGainLossCloseProfitToString(sdMaxProfit, sdMaxLoss, sdProfitInClose);
    }

    public static String dateToString(Date firstDay, Date secondDay) {
        return " (date: " + SDF.format(firstDay) + " - " + SDF.format(secondDay) + ")";
    }

    public static String getHLCtoString(float h, float l, float c) {
        return " # HLC: H = " + format(h) + " | L = " + format(l) + " | C = " + format(c);
    }

    public static String getGainLossCloseProfitToString(Float g, Float l, Float cp) {
        return " # GLCp: G = " + format(g) + " | L = " + format(l) + " | Cp = " + format(cp);
    }
}
