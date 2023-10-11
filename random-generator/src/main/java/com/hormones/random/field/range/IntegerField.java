package com.hormones.random.field.range;


import com.hormones.random.field.RangeField;

import java.util.Random;

public class IntegerField extends RangeField<Integer> {

    public IntegerField(String name) {
        this(name, Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
    }

    public IntegerField(String name, Integer from, Integer to) {
        this(name, from, to, 0);
    }

    public IntegerField(String name, Integer from, Integer to, Integer auto) {
        super(name, from, to, auto);
    }

    @Override
    protected Integer getIncrement() {
        // 边界值处理,防止越界
        if (this.auto > 0L && Integer.MAX_VALUE - this.auto < this.value) {
            return this.from;
        }
        if (this.auto < 0L && Integer.MIN_VALUE - this.auto > this.value) {
            return this.from;
        }

        return Math.toIntExact(this.value + this.auto);
    }

    @Override
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    protected Integer getRandom() {
        if (this.from.equals(this.to)) {
            return this.from;
        }
        Random random = new Random();
        return random.ints(Math.min(this.from, this.to), Math.max(this.from, this.to)).findFirst().getAsInt();
    }
}
