package com.hormones.random.field.pattern;


import com.hormones.random.field.abs.PatternField;
import com.hormones.random.field.range.IntegerField;

import java.util.List;

public class DataSetField<T> extends PatternField<Integer, T> {

    protected final List<T> dataset;

    public DataSetField(String name, List<T> dataset) {
        this(name, 0, dataset);
    }

    public DataSetField(String name, int auto, List<T> dataset) {
        super(new IntegerField(name, 0, dataset.size(), auto));
        this.dataset = dataset;
    }

    public List<T> getDataset() {
        return dataset;
    }

    @Override
    protected T pattern(Integer value) {
        return this.dataset.get(value);
    }
}
