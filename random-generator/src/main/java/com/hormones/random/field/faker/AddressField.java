package com.hormones.random.field.faker;

import com.github.javafaker.Address;
import com.hormones.random.field.FakerField;

import java.util.Locale;
import java.util.function.Function;

public class AddressField extends FakerField<Address, String> {

    public AddressField(String name) {
        super(name, Address::streetAddress);
    }

    public AddressField(String name, Function<Address, String> function) {
        super(name, function);
    }

    public AddressField(String name, Locale locale) {
        super(name, Address::streetAddress, locale);
    }

    public AddressField(String name, Function<Address, String> function, Locale locale) {
        super(name, function, locale);
    }

    @Override
    public Address getEntity() {
        return this.faker.address();
    }
}
