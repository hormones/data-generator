package com.hormones.random.field.faker;

import com.github.javafaker.Name;
import com.hormones.random.field.FakerField;

import java.util.Locale;
import java.util.function.Function;

public class NameField extends FakerField<Name, String> {

    public NameField(String name) {
        super(name, Name::name);
    }

    public NameField(String name, Function<Name, String> function) {
        super(name, function);
    }

    public NameField(String name, Locale locale) {
        super(name, Name::name, locale);
    }

    public NameField(String name, Function<Name, String> function, Locale locale) {
        super(name, function, locale);
    }

    @Override
    public Name getEntity() {
        return this.faker.name();
    }
}
