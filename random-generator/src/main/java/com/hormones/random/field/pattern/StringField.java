package com.hormones.random.field.pattern;

import com.hormones.random.field.abs.PatternField;
import com.hormones.random.field.range.IntegerField;

public class StringField extends PatternField<Integer, String> {
    private static final String KEYS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public StringField(String name) {
        this(name, 3, 3);
    }

    public StringField(String name, int length) {
        this(name, length, length);
    }

    public StringField(String name, int min, int max) {
        super(new IntegerField(name, min, max));
    }

    @Override
    protected String pattern(Integer value) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < value; i++) {
            sb.append(KEYS.charAt((int) (Math.random() * KEYS.length())));
        }
        return sb.toString();
    }
}
