package com.hormones.random.field;

import java.util.Objects;

public abstract class RangeField<T extends Comparable<? super T>> extends Field<T> {

    /**
     * 自增或自减的量，值为0标识不自增或自减，返回随机值
     */
    protected final long auto;

    protected final T from;

    protected final T to;

    protected T previous;

    public RangeField(String name, T from, T to, long auto) {
        super(name);
        this.from = from;
        this.to = to;
        this.auto = auto;
    }

    protected abstract T getIncrement();

    protected abstract T getRandom();

    protected boolean isOutOfRange(T next) {
        if (this.auto == 0) {
            return false;
        }
        int compare = next.compareTo(this.to);
        return (this.auto > 0 && compare > 0) || (this.auto < 0 && compare < 0);
    }

    @Override
    public T generate() {
        if (this.auto != 0L) {
            T next;
            if (Objects.isNull(this.previous)) {
                next = this.from;
            } else {
                next = this.getIncrement();
            }
            if (this.isOutOfRange(next)) {
                next = this.from;
            }
            this.previous = next;
            return next;
        }
        return this.getRandom();
    }
}
