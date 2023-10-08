package com.hormones.random.field.pattern;


import com.hormones.random.field.PatternField;
import com.hormones.random.field.range.DateTimeField;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimePatternField extends PatternField<LocalDateTime, String> {
    public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    protected final DateTimeFormatter formatter;

    public DateTimePatternField(DateTimeField field, String pattern) {
        super(field.getName(), field);
        this.formatter = DateTimeFormatter.ofPattern(pattern);
    }

    public DateTimePatternField(DateTimeField field) {
        this(field, DEFAULT_PATTERN);
    }

    public DateTimePatternField(String name, LocalDateTime from, LocalDateTime to, int auto, String pattern) {
        this(new DateTimeField(name, from, to, auto), pattern);
    }

    public DateTimePatternField(String name) {
        this(new DateTimeField(name, LocalDateTime.MIN, LocalDateTime.MAX, 0), DEFAULT_PATTERN);
    }

    public DateTimePatternField(String name, String pattern) {
        this(new DateTimeField(name, LocalDateTime.MIN, LocalDateTime.MAX, 0), pattern);
    }

    public DateTimePatternField(String name, LocalDateTime from, LocalDateTime to) {
        this(new DateTimeField(name, from, to, 0), DEFAULT_PATTERN);
    }

    public DateTimePatternField(String name, LocalDateTime from, LocalDateTime to, String pattern) {
        this(new DateTimeField(name, from, to, 0), pattern);
    }

    public DateTimePatternField(String name, LocalDateTime from, LocalDateTime to, int auto) {
        this(new DateTimeField(name, from, to, auto), DEFAULT_PATTERN);
    }

    @Override
    protected String pattern(LocalDateTime value) {
        return value.format(formatter);
    }
}
