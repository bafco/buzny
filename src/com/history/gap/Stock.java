package com.history.gap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Matúš
 */
public class Stock {

    public static final String newline = System.getProperty("line.separator");

    final String name;

    private List<Gap> gaps = new ArrayList<>();

    private float averageGap = 0;

    public Stock(String name) {
        this.name = name;
    }

    public Stock(String name, List<Gap> gaps) {
        this.name = name;
        this.gaps = gaps;
    }

    public void addGap(Gap gap) {
        gaps.add(gap);
    }

    public List<Gap> getGaps() {
        return Collections.unmodifiableList(gaps);
    }

    /**
     * Compute expected profit in % (single profit = max(|gap| - 5, 0))
     */
    public float getAverageProfit() {
        float result = 0;
        for (Gap gap : gaps) {
            result += gap.profit;
        }
        result = result / gaps.size();
        return result;
    }

    /**
     * @return average gap (can be negative)
     */
    public float getAverageGap() {
        if (averageGap == 0) {
            for (Gap gap : gaps) {
                averageGap += gap.gap;
            }
            averageGap = averageGap / gaps.size();
        }
        return averageGap;
    }

    public double getStandardDeviation() {
        return Math.sqrt(getVariance());
    }

    public double getVariance() {
        double variance = 0;
        for (Gap gap : gaps) {
            variance += Math.pow(gap.gap - averageGap, 2);
        }
        variance = variance / gaps.size();
        return variance;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(name).append(newline);
        sb.append("Average profit: ").append(getAverageProfit()).append(newline);
//        sb.append("Average gap: ").append(getAverageGap()).append(newline);
//        sb.append("Standard deviation: ").append(getStandardDeviation()).append(newline);
        sb.append("Gaps:").append(newline);
        for (Gap gap : gaps) {
            sb.append(gap.toString()).append(newline);
        }
        return sb.toString();
    }
}
