package com.hormones.random.field.pattern;

import com.hormones.random.field.Field;
import com.hormones.random.field.PatternField;

public class StringStemField extends PatternField<Object, String> {
    public static final String EMPTY = "";

    protected final String prefix;

    protected final String suffix;

    public StringStemField(String prefix, Field<?> holder, String suffix) {
        super((Field<Object>) holder);
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public StringStemField(String prefix, Field<?> holder) {
        this(prefix, holder, EMPTY);
    }

    public StringStemField(Field<?> holder, String suffix) {
        this(EMPTY, holder, suffix);
    }

    @Override
    protected String pattern(Object value) {
        return this.prefix + value + this.suffix;
    }
}
