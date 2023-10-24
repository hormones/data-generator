package com.hormones.random.field.abs;

import com.hormones.random.field.Field;

public abstract class PatternField<T, K> extends Field<K> {

    protected final Field<T> holder;

    public PatternField(Field<T> holder) {
        super(holder.getName());
        this.holder = holder;
    }

    public Field<T> getHolder() {
        return holder;
    }

    @Override
    protected final K generate() {
        T value = this.holder.next();
        return this.pattern(value);
    }

    protected abstract K pattern(T value);
}
