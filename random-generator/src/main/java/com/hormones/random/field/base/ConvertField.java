package com.hormones.random.field.base;

import com.hormones.random.field.Field;
import com.hormones.random.field.base.ConvertField.FunctionValue;

import java.util.function.Function;

public class ConvertField<T, K> extends Field<FunctionValue> {

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
