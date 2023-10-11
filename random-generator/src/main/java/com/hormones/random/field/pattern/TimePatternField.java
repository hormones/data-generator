package com.hormones.random.field.pattern;


import com.hormones.random.field.PatternField;
import com.hormones.random.field.range.TimeField;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimePatternField extends PatternField<LocalTime, String> {
    public static final String DEFAULT_PATTERN = "HH:mm:ss";

    protected final DateTimeFormatter formatter;

    public TimePatternField(TimeField field, String pattern) {
        super(field);
        this.formatter = DateTimeFormatter.ofPattern(pattern);
    }

    public TimePatternField(TimeField field) {
        this(field, DEFAULT_PATTERN);
    }

    public TimePatternField(String name, LocalTime from, LocalTime to, int auto, String pattern) {
        this(new TimeField(name, from, to, auto), pattern);
    }

    public TimePatternField(String name) {
        this(new TimeField(name, LocalTime.MIN, LocalTime.MAX, 0), DEFAULT_PATTERN);
    }

    public TimePatternField(String name, String pattern) {
        this(new TimeField(name, LocalTime.MIN, LocalTime.MAX, 0), pattern);
    }

    public TimePatternField(String name, LocalTime from, LocalTime to) {
        this(new TimeField(name, from, to, 0), DEFAULT_PATTERN);
    }

    public TimePatternField(String name, LocalTime from, LocalTime to, String pattern) {
        this(new TimeField(name, from, to, 0), pattern);
    }

    public TimePatternField(String name, LocalTime from, LocalTime to, int auto) {
        this(new TimeField(name, from, to, auto), DEFAULT_PATTERN);
    }

    @Override
    protected String pattern(LocalTime value) {
        return value.format(formatter);
    }
}
