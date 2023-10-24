package com.hormones.random.field.pattern;


import com.hormones.random.field.abs.PatternField;
import com.hormones.random.field.range.DateField;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DatePatternField extends PatternField<LocalDate, String> {
    public static final String DEFAULT_PATTERN = "yyyy-MM-dd";

    protected final DateTimeFormatter formatter;

    public DatePatternField(DateField field, String pattern) {
        super(field);
        this.formatter = DateTimeFormatter.ofPattern(pattern);
    }

    public DatePatternField(DateField field) {
        this(field, DEFAULT_PATTERN);
    }

    public DatePatternField(String name, LocalDate from, LocalDate to, int auto, String pattern) {
        this(new DateField(name, from, to, auto), pattern);
    }

    public DatePatternField(String name) {
        this(new DateField(name, LocalDate.MIN, LocalDate.MAX, 0), DEFAULT_PATTERN);
    }

    public DatePatternField(String name, String pattern) {
        this(new DateField(name, LocalDate.MIN, LocalDate.MAX, 0), pattern);
    }

    public DatePatternField(String name, LocalDate from, LocalDate to) {
        this(new DateField(name, from, to, 0), DEFAULT_PATTERN);
    }

    public DatePatternField(String name, LocalDate from, LocalDate to, String pattern) {
        this(new DateField(name, from, to, 0), pattern);
    }

    public DatePatternField(String name, LocalDate from, LocalDate to, int auto) {
        this(new DateField(name, from, to, auto), DEFAULT_PATTERN);
    }

    @Override
    protected String pattern(LocalDate value) {
        return value.format(formatter);
    }
}
