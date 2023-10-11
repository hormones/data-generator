package com.hormones.random.field;

public abstract class Field<T> {

    protected final String name;

    protected T value = null;

    private int index = -1;

    public Field(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    protected abstract T generate();

    public final T next() {
        this.value = this.generate();
        index++;
        return this.value;
    }

    public T get() {
        return this.value;
    }

    public int getIndex() {
        return index;
    }
}
