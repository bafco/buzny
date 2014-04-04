package com.history.gap;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author Matúš
 */
public class DetailedGap  extends Gap {

    final float secondDayHigh;
    final float secondDayLow;
    final float secondDayClose;
    final Date firstDay;
    final Date secondDay;

    public DetailedGap(float close, float open) {
        super(close, open);
        secondDayHigh = 0;
        secondDayLow = 0;
        secondDayClose = 0;
        firstDay = null;
        secondDay = null;
    }

    public DetailedGap(float fdClose, float sdOpen, float sdHigh, float sdLow, float sdClose, Date firstDay, Date secondDay) {
        super(fdClose, sdOpen);
        secondDayHigh = sdHigh;
        secondDayLow = sdLow;
        secondDayClose = sdClose;
        this.firstDay = firstDay;
        this.secondDay = secondDay;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("d/MM/yyyy");
        return super.toString() + " (date: " + sdf.format(firstDay) + " - " + sdf.format(secondDay) + ")";
    }
}
