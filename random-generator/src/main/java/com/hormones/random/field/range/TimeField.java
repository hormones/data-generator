package com.hormones.random.field.range;

import com.hormones.random.field.RangeField;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class TimeField extends RangeField<LocalTime> {

    public TimeField(String name) {
        this(name, LocalTime.MIN, LocalTime.MAX, 0);
    }

    public TimeField(String name, LocalTime from, LocalTime to) {
        this(name, from, to, 0);
    }

    public TimeField(String name, LocalTime from, LocalTime to, int auto) {
        super(name, from, to, auto);
    }

    @Override
    protected LocalTime getIncrement() {
        return this.previous.plusSeconds(this.auto);
    }

    @Override
    protected LocalTime getRandom() {
        long between = ChronoUnit.SECONDS.between(this.from, this.to);
        return this.from.plusSeconds((long) (Math.random() * between));
    }
}
