package com.hormones.random.field.base;

import com.hormones.random.field.Field;

import java.util.UUID;

public class UUIDLongField extends Field<Long> {

    public UUIDLongField(String name) {
        super(name);
    }

    @Override
    public Long get() {
        return Math.abs(UUID.randomUUID().getLeastSignificantBits());
    }
}
