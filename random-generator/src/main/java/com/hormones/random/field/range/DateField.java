package com.hormones.random.field.range;


import com.hormones.random.field.RangeField;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DateField extends RangeField<LocalDate> {

    public DateField(String name) {
        this(name, LocalDate.MIN, LocalDate.MAX, 0);
    }

    public DateField(String name, LocalDate from, LocalDate to) {
        this(name, from, to, 0);
    }

    public DateField(String name, LocalDate from, LocalDate to, int auto) {
        super(name, from, to, auto);
    }

    @Override
    protected LocalDate getIncrement() {
        return this.previous.plusDays(this.auto);
    }

    @Override
    protected LocalDate getRandom() {
        long between = ChronoUnit.DAYS.between(this.from, this.to);
        return this.from.plusDays((long) (Math.random() * between));
    }
}
