package com.hormones.random.field;

public class ConstantField<T> extends Field<T> {

    private final T value;

    public ConstantField(String name, T value) {
        super(name);
        this.value = value;
    }

    @Override
    public T get() {
        return this.value;
    }
}
