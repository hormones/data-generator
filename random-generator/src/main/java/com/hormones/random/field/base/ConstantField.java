package com.hormones.random.field.base;

import com.hormones.random.field.Field;

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
