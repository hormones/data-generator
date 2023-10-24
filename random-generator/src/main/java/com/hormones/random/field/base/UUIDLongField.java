package com.hormones.random.field.base;

import com.hormones.random.field.Field;

import java.util.UUID;

public class UUIDLongField extends Field<String> {

    public UUIDLongField(String name) {
        super(name);
    }

    @Override
    protected String generate() {
        return String.valueOf(Math.abs(UUID.randomUUID().getLeastSignificantBits()));
    }
}
