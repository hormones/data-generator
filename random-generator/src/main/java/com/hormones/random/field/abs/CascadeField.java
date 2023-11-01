package com.hormones.random.field.abs;

import com.hormones.random.field.base.ConstantField;
import com.hormones.random.field.pattern.DataSetField;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public abstract class CascadeField<T> extends MultiField<T> {

    protected final List<String> fields = new ArrayList<>();

    protected final DataSetField<RelationField<T>> root;

    public CascadeField(String remark, List<String> defaultNames, List<String> customNames) {
        super(remark);
        if (CollectionUtils.isNotEmpty(customNames)) {
            for (int i = 0; i < defaultNames.size(); i++) {
                if (customNames.size() > i) {
                    if (StringUtils.isNotBlank(customNames.get(i))) {
                        defaultNames.set(i, customNames.get(i));
                    }
                }
            }
        }
        this.fields.addAll(defaultNames);
        this.root = this.initConfig();
    }

    public final List<String> getFields() {
        return this.fields;
    }

    protected abstract DataSetField<RelationField<T>> initConfig();

    @Override
    protected List<T> generate() {
        List<T> data = new ArrayList<>();
        DataSetField<RelationField<T>> current = this.initConfig();
        while (Objects.nonNull(current)) {
            RelationField<T> child = current.generate();
            T value = child.next();
            current = child.getChildren();
            data.add(value);
        }
        return data;
    }

    protected static class RelationField<T> extends ConstantField<T> {
        private DataSetField<RelationField<T>> children;

        @SafeVarargs
        public RelationField(T value, RelationField<T>... children) {
            super("useless", value);
            this.children = null;
            if (ArrayUtils.isEmpty(children)) {
                return;
            }
            this.children = new DataSetField<>(children[0].getName(), Arrays.asList(children));
        }

        public final DataSetField<RelationField<T>> getChildren() {
            return children;
        }
    }
}
