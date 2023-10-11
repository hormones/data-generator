package com.hormones.random.field;

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
    public final K generate() {
        T value = this.holder.next();
        return this.pattern(value);
    }

    protected abstract K pattern(T value);
}
