package com.hormones.random.field;

public abstract class Field<T> {

    protected final String name;

    public Field(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract T get();
}
