package com.hormones.random.field.base;

import com.hormones.random.field.Field;

import java.util.UUID;

public class UUIDLongField extends Field<String> {

    public UUIDLongField(String name) {
        super(name);
    }

    @Override
    public String get() {
        return String.valueOf(Math.abs(UUID.randomUUID().getLeastSignificantBits()));
    }
}
