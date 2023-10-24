package com.hormones.random.field;

import com.hormones.random.field.pattern.DataSetField;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;

public class CascadeField<T> extends Field<T> {
    private DataSetField<CascadeField<T>> children;

    @SafeVarargs
    public CascadeField(T value, CascadeField<T>... children) {
        super("useless");
        this.value = value;
        this.children = null;
        if (ArrayUtils.isEmpty(children)) {
            return;
        }
        this.children = new DataSetField<>(children[0].getName(), Arrays.asList(children));
    }

    public final DataSetField<CascadeField<T>> getChildren() {
        return children;
    }

    @Override
    protected T generate() {
        return this.value;
    }
}
