package com.hormones.random.field.range;


import com.hormones.random.field.abs.RangeField;

import java.util.Random;

public class LongField extends RangeField<Long> {

    public LongField(String name) {
        this(name, Long.MIN_VALUE, Long.MAX_VALUE, 0L);
    }

    public LongField(String name, Long from, Long to) {
        this(name, from, to, 0L);
    }

    public LongField(String name, Long from, Long to, long auto) {
        super(name, from, to, auto);
    }

    @Override
    protected Long getIncrement() {
        // 边界值处理,防止越界
        long autoValue = this.auto.longValue();
        if (autoValue > 0L && Long.MAX_VALUE - autoValue < this.value) {
            return this.from;
        }
        if (autoValue < 0L && Long.MIN_VALUE - autoValue > this.value) {
            return this.from;
        }

        return this.value + autoValue;
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
