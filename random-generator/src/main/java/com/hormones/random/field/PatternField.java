package com.hormones.random.field;

public abstract class PatternField<T, K> extends Field<K> {

    protected final Field<T> holder;

    public PatternField(String name, Field<T> holder) {
        super(name);
        this.holder = holder;
    }

    public Field<T> getHolder() {
        return holder;
    }

    @Override
    public final K get() {
        T value = this.holder.get();
        return this.pattern(value);
    }

    protected abstract K pattern(T value);
}
