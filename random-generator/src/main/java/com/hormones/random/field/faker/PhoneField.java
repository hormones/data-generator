package com.hormones.random.field.faker;

import com.github.javafaker.PhoneNumber;
import com.hormones.random.field.abs.FakerField;

import java.util.Locale;
import java.util.function.Function;

public class PhoneField extends FakerField<PhoneNumber, String> {

    public PhoneField(String name) {
        super(name, PhoneNumber::cellPhone);
    }

    public PhoneField(String name, Function<PhoneNumber, String> function) {
        super(name, function);
    }

    public PhoneField(String name, Locale locale) {
        super(name, PhoneNumber::cellPhone, locale);
    }

    public PhoneField(String name, Function<PhoneNumber, String> function, Locale locale) {
        super(name, function, locale);
    }

    @Override
    public PhoneNumber getEntity() {
        return this.faker.phoneNumber();
    }
}
