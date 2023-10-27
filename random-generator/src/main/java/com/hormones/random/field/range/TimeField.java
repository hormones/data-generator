package com.hormones.random.field.range;

import com.hormones.random.field.abs.RangeField;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class TimeField extends RangeField<LocalTime> {

    public TimeField(String name) {
        this(name, LocalTime.MIN, LocalTime.MAX, 0L);
    }

    public TimeField(String name, LocalTime from, LocalTime to) {
        this(name, from, to, 0L);
    }

    public TimeField(String name, LocalTime from, LocalTime to, long auto) {
        super(name, from, to, auto);
    }

    @Override
    protected LocalTime getIncrement() {
        return this.value.plusSeconds(this.auto.longValue());
    }

    @Override
    protected LocalTime getRandom() {
        long between = ChronoUnit.SECONDS.between(this.from, this.to);
        return this.from.plusSeconds((long) (Math.random() * between));
    }
}
