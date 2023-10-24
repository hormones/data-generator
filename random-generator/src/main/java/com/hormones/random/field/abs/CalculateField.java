package com.hormones.random.field.abs;

import com.hormones.random.field.Field;

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

    public static class ConvertField<T, K> extends Field<ConvertField.FunctionValue> {

        private final Function<T, K> function;

        protected final Field<T> field;

        public ConvertField(Field<T> field, Function<T, K> function) {
            super(field.getName());
            this.field = field;
            this.function = function;
        }

        @Override
        protected FunctionValue generate() {
            T value = this.field.get();
            return new FunctionValue(value, this.function.apply(value));
        }

        @Override
        public FunctionValue get() {
            return this.next();
        }

        @Override
        public int getIndex() {
            return this.field.getIndex();
        }

        public class FunctionValue {
            private T origin;

            private K value;

            public FunctionValue(T origin, K value) {
                this.origin = origin;
                this.value = value;
            }

            public T getOrigin() {
                return origin;
            }

            public void setOrigin(T origin) {
                this.origin = origin;
            }

            public K getValue() {
                return value;
            }

            public void setValue(K value) {
                this.value = value;
            }
        }
    }
}
