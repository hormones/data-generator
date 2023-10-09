package com.hormones.random.field;

import com.hormones.random.field.DataBuilder.FieldValues;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class CalculateField<T> extends Field<T> {

    private List<FieldValues<?>> fields;

    private int index = 0;

    public CalculateField(String name, List<FieldValues<?>> fields) {
        super(name);
        this.fields = fields;
    }

    public CalculateField(String name) {
        this(name, null);
    }

    private List<FieldValues<?>> getFilteredFields() {
        return fields.stream().filter(f -> !(f.getField() instanceof CalculateField)).collect(Collectors.toList());
    }

    public void setFields(List<FieldValues<?>> fields) {
        if (Objects.nonNull(this.fields)) {
            throw new RuntimeException("fields only can be set once");
        }
        this.fields = fields;
    }

    public final T get() {
        List<FieldValues<?>> filteredFields = this.getFilteredFields();
        if (CollectionUtils.isEmpty(filteredFields)) {
            throw new RuntimeException("fields is empty");
        }
        if (this.index >= filteredFields.get(0).getAmount()) {
            this.index = 0;
        }
        T calculate = this.calculate(filteredFields);
        this.index++;
        return calculate;
    }

    public int getIndex() {
        return this.index;
    }

    protected abstract T calculate(List<FieldValues<?>> filteredFields);
}
