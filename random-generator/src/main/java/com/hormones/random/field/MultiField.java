package com.hormones.random.field;

import com.hormones.random.field.pattern.DataSetField;
import com.hormones.random.field.pattern.StringField;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class MultiField<T> extends Field<List<T>> {

    protected final List<StringField> fields = new ArrayList<>();

    protected final DataSetField<CascadeField<T>> root;

    public MultiField(String remark, List<String> defaultNames, List<String> customNames) {
        super(remark);
        if (CollectionUtils.isNotEmpty(customNames)) {
            for (int i = 0; i < defaultNames.size(); i++) {
                if (customNames.size() > i) {
                    defaultNames.set(i, customNames.get(i));
                }
            }
        }
        defaultNames.stream().map(StringField::new).forEach(this.fields::add);
        this.root = this.initConfig();
    }

    public final List<StringField> getFields() {
        return this.fields;
    }

    protected abstract DataSetField<CascadeField<T>> initConfig();

    public int getSize() {
        return this.fields.size();
    }

    @Override
    protected List<T> generate() {
        List<T> data = new ArrayList<>();
        DataSetField<CascadeField<T>> current = this.initConfig();
        while (Objects.nonNull(current)) {
            CascadeField<T> child = current.generate();
            T value = child.next();
            current = child.getChildren();
            data.add(value);
        }
        return data;
    }
}
