package se.david.pitest;

import java.util.Date;

class DateCalculator {
    private final Date now;

    DateCalculator(Date now) {
        this.now = now;
    }

    String isDateInThePast(Date date) {
        return (date.before(now)) ? "yes" : "no";
    }
}
