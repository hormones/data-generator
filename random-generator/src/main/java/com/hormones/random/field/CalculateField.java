package com.hormones.random.field;

import com.hormones.random.field.base.ConvertField;

public abstract class CalculateField<T> extends Field<T> {

    protected final ConvertField<?, T>[] fields;

    @SafeVarargs
    public CalculateField(String name, ConvertField<?, T>... fields) {
        super(name);
        this.fields = fields;
    }

    public ConvertField<?, T>[] getFields() {
        return fields;
    }
}
