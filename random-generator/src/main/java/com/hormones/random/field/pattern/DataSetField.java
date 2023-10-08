package com.hormones.random.field.pattern;


import com.hormones.random.field.PatternField;
import com.hormones.random.field.range.IntegerField;

import java.util.List;

public class DataSetField<T> extends PatternField<Integer, T> {

    protected final List<T> keys;

    public DataSetField(String name, List<T> keys) {
        this(name, 0, keys);
    }

    public DataSetField(String name, int auto, List<T> keys) {
        super(name, new IntegerField(name, 0, keys.size() - 1, auto));
        this.keys = keys;
    }

    @Override
    protected T pattern(Integer value) {
        return this.keys.get(value);
    }
}
