package com.hormones.random.field.base;

import com.hormones.random.field.Field;

public class ConstantField<T> extends Field<T> {

    public ConstantField(String name, T value) {
        super(name);
        this.value = value;
    }

    @Override
    protected final T generate() {
        return this.value;
    }
}
