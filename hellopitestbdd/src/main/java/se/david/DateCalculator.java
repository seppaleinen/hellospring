package se.david;

import java.util.Date;

public class DateCalculator {
    private Date now;

    DateCalculator(Date now) {
        this.now = now;
    }

    String isDateInThePast(Date date) {
        return (date.before(now)) ? "yes" : "no";
    }
}
