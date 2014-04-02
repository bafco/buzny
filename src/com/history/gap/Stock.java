package com.history.gap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Matúš
 */
public class Stock {

    final String name;

    private List<Gap> gaps = new ArrayList<>();

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
    public float getAverageReturn() {
        float result = 0;
        for (Gap gap : gaps) {
            result += gap.profit;
        }
        result = result / gaps.size();
        return result;
    }
}
