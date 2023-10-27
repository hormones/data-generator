package com.hormones.random.field.range;


import com.hormones.random.field.abs.RangeField;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DateTimeField extends RangeField<LocalDateTime> {

    public DateTimeField(String name) {
        this(name, LocalDateTime.MIN, LocalDateTime.MAX, 0L);
    }

    public DateTimeField(String name, LocalDateTime from, LocalDateTime to) {
        this(name, from, to, 0L);
    }

    public DateTimeField(String name, LocalDateTime from, LocalDateTime to, long auto) {
        super(name, from, to, auto);
    }

    @Override
    protected LocalDateTime getIncrement() {
        return this.value.plusSeconds(this.auto.longValue());
    }

    @Override
    protected LocalDateTime getRandom() {
        long between = ChronoUnit.SECONDS.between(this.from, this.to);
        return this.from.plusSeconds((long) (Math.random() * between));
    }
}
