package com.hormones.random.field.abs;

import com.hormones.random.field.Field;

import java.util.Objects;

public abstract class RangeField<T extends Comparable<? super T>> extends Field<T> {

    /**
     * 自增或自减的量，值为0标识不自增或自减，返回随机值
     */
    protected final Number auto;

    protected final T from;

    protected final T to;

    public RangeField(String name, T from, T to, Number auto) {
        super(name);
        this.from = from;
        this.to = to;
        this.auto = auto;
    }

    protected abstract T getIncrement();

    protected abstract T getRandom();

    protected boolean isOutOfRange(T next) {
        double autoValue = this.auto.doubleValue();
        if (autoValue == 0) {
            return false;
        }
        int compare = next.compareTo(this.to);
        return (autoValue > 0 && compare > 0) || (autoValue < 0 && compare < 0);
    }

    @Override
    protected T generate() {
        if (this.auto.doubleValue() != 0) {
            T next;
            if (Objects.isNull(this.value)) {
                next = this.from;
            } else {
                next = this.getIncrement();
            }
            if (this.isOutOfRange(next)) {
                next = this.from;
            }
            return next;
        }
        return this.getRandom();
    }
}
