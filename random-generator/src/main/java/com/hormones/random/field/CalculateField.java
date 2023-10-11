package com.hormones.random.field;

import com.hormones.random.field.base.ConvertField;

import java.util.function.Function;

public abstract class CalculateField<T> extends Field<T> {

    protected final ConvertField<?, T>[] fields;

    @SuppressWarnings("rawtypes")
    protected CalculateField(String name, Function<Object, T> function, Field<?>... fields) {
        super(name);
        this.fields = new ConvertField[fields.length];
        for (int i = 0; i < fields.length; i++) {
            Field<?> field = fields[i];
            if (field instanceof ConvertField) {
                this.fields[i] = (ConvertField) field;
                continue;
            }
            this.fields[i] = new ConvertField<>(field, function::apply);
        }
    }

    public ConvertField<?, T>[] getFields() {
        return fields;
    }
}
