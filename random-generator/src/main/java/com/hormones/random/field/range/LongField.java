package com.hormones.random.field.range;


import com.hormones.random.field.RangeField;

import java.util.Random;

public class LongField extends RangeField<Long> {

    public LongField(String name) {
        this(name, Long.MIN_VALUE, Long.MAX_VALUE, 0L);
    }

    public LongField(String name, Long from, Long to) {
        this(name, from, to, 0L);
    }

    public LongField(String name, Long from, Long to, Long auto) {
        super(name, from, to, auto);
    }

    @Override
    protected Long getIncrement() {
        // 边界值处理,防止越界
        if (this.auto > 0L && Long.MAX_VALUE - this.auto < this.value) {
            return this.from;
        }
        if (this.auto < 0L && Long.MIN_VALUE - this.auto > this.value) {
            return this.from;
        }

        return this.value + this.auto;
    }

    @Override
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    protected Long getRandom() {
        if (this.from.equals(this.to)) {
            return this.from;
        }
        Random random = new Random();
        return random.longs(Math.min(this.from, this.to), Math.max(this.from, this.to)).findFirst().getAsLong();
    }
}
