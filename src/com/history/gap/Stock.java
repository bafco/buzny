package com.history.gap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Matúš
 */
public class Stock {

    public static final String NEW_LINE = System.getProperty("line.separator");

    public static final int AVERAGE_INDEX = 0;
    public static final int STANDARD_DEVIATION_INDEX = 1;
    // first two indices are reserved for average and standard deviation
    public static final int NUMBER_OF_INDICES_RESERVED = 2;

    public static final int GAP_INDEX = 0;
    public static final int PROFIT_INDEX = 1;
    public static final int PROFIT_ON_BUY_INDEX = 2;
    public static final int PROFIT_ON_SELL_INDEX = 3;
    public static final int SD_HIGH_INDEX = 4;
    public static final int SD_LOW_INDEX = 5;
    public static final int SD_CLOSE_INDEX = 6;
    public static final int SD_MAX_PROFIT_INDEX = 7;
    public static final int SD_MAX_LOSS_INDEX = 8;
    public static final int SD_PROFIT_IN_CLOSE_INDEX = 9;
    public static final int SD_TOTAL_INDEX = 10;
    public static final int MAX_INDEX = SD_TOTAL_INDEX;

    public final String name;
    public final String symbol;

    private final List<Gap> gaps = new ArrayList<>();

    /**
     * Float[i][j]:
     * - i from 0 to MAX_INDEX: gap, profit, profitOnBuy, profitOnSell, sdHighInP, sdLowInP, sdCloseInP, sdMaxP, sdMaxL, sdProfitInC, sdTotal
     * - j from 0 to (gaps.size() + 1) - first two numbers are average and standard deviation
     */
    public Float[][] gapsResults;

    public Stock(String name) {
        this.name = name;
        symbol = name.substring(name.indexOf(':') + 1, name.length() - 1);
    }

    public void addGap(Gap gap) {
        gaps.add(gap);
    }

    public List<Gap> getGaps() {
        return Collections.unmodifiableList(gaps);
    }

    public void computeGapsResults() {
        if (gapsResults != null) {
            return;
        }
        gapsResults = new Float[MAX_INDEX + 1][NUMBER_OF_INDICES_RESERVED + gaps.size()];
        int j = NUMBER_OF_INDICES_RESERVED - 1;
        for (Gap gap : gaps) {
            j++;
            gapsResults[GAP_INDEX][j] = gap.gap;
            gapsResults[PROFIT_INDEX][j] = gap.profit;
            gapsResults[PROFIT_ON_BUY_INDEX][j] = gap.profitOnBuy;
            gapsResults[PROFIT_ON_SELL_INDEX][j] = gap.profitOnSell;

            if (gap instanceof DetailedGap) {
                DetailedGap dgap = (DetailedGap) gap;
                gapsResults[SD_HIGH_INDEX][j] = dgap.sdHighInPercent;
                gapsResults[SD_LOW_INDEX][j] = dgap.sdLowInPercent;
                gapsResults[SD_CLOSE_INDEX][j] = dgap.sdCloseInPercent;
                gapsResults[SD_MAX_PROFIT_INDEX][j] = dgap.sdMaxProfit;
                gapsResults[SD_MAX_LOSS_INDEX][j] = dgap.sdMaxLoss;
                gapsResults[SD_PROFIT_IN_CLOSE_INDEX][j] = dgap.sdProfitInClose;
                gapsResults[SD_TOTAL_INDEX][j] = dgap.sdTotal;
            }
        }

        for (int i = 0; i <= MAX_INDEX; i++) {
            computeAverageAndDeviation(gapsResults[i]);
        }
    }

    private void computeAverageAndDeviation(Float[] array) {
        float average = 0;
        int notNull = 0;
        for (int i = NUMBER_OF_INDICES_RESERVED; i < array.length; i++) {
            if (array[i] != null) {
                average += array[i];
                notNull++;
            }
        }
        average = average/notNull;
        array[AVERAGE_INDEX] = average;

        float variance = 0;
        for (int i = NUMBER_OF_INDICES_RESERVED; i < array.length; i++) {
            if (array[i] != null) {
                variance += Math.pow(array[i] - average, 2);
            }
        }
        variance = variance/notNull;
        array[STANDARD_DEVIATION_INDEX] = new Float(Math.sqrt(variance));
    }

    @Override
    public String toString() {
        computeGapsResults();
        StringBuilder sb = new StringBuilder(name).append(NEW_LINE);
        sb.append("Gaps:").append(NEW_LINE);
        for (Gap gap : gaps) {
            sb.append(gap.toString()).append(NEW_LINE);
        }
        sb.append(NEW_LINE);
        sb.append("Average gap: ").append(NEW_LINE).append(averageValuesToString()).append(NEW_LINE);
        sb.append("Standard deviation: ").append(NEW_LINE).append(standardDeviationValuesToString()).append(NEW_LINE);

        sb.append(NEW_LINE);
        sb.append("Average profit on buy: ").append(gapsResults[2][0]).append(NEW_LINE);
        sb.append("Average profit on sell: ").append(gapsResults[3][0]).append(NEW_LINE);

        sb.append(NEW_LINE);
        sb.append("Average profit on gap: ").append(gapsResults[1][0]).append(NEW_LINE);

        return sb.toString();
    }

    private String averageValuesToString() {
        return statisticValuesToString(AVERAGE_INDEX);
    }

    private String standardDeviationValuesToString() {
        return statisticValuesToString(STANDARD_DEVIATION_INDEX);
    }

    private String statisticValuesToString(int index) {
        return Gap.format(gapsResults[0][index]) + DetailedGap.exampleDateToString() +
                DetailedGap.getHLCtoString(gapsResults[SD_HIGH_INDEX][index], gapsResults[SD_LOW_INDEX][index], gapsResults[SD_CLOSE_INDEX][index]) +
                DetailedGap.getGainLossCloseProfitToString(gapsResults[SD_MAX_PROFIT_INDEX][index], gapsResults[SD_MAX_LOSS_INDEX][index], gapsResults[SD_PROFIT_IN_CLOSE_INDEX][index]) +
                DetailedGap.getSDTotalToString(gapsResults[SD_TOTAL_INDEX][index]);
    }
}
