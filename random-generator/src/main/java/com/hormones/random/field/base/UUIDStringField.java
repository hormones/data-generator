package com.hormones.random.field.base;

import com.hormones.random.field.Field;

import java.util.UUID;

public class UUIDStringField extends Field<String> {

    public UUIDStringField(String name) {
        super(name);
    }

    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
