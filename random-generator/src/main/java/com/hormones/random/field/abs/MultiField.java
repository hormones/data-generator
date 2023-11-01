package com.hormones.random.field.abs;

import com.hormones.random.field.Field;

import java.util.List;

public abstract class MultiField<T> extends Field<List<T>> {
    public MultiField(String name) {
        super(name);
    }

    public abstract List<String> getFields();

    public final int getSize() {
        return this.getFields().size();
    }
}
