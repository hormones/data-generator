package com.hormones.random.field;

import com.github.javafaker.Faker;
import com.hormones.random.util.FieldUtil;

import java.util.Locale;
import java.util.function.Function;

public abstract class FakerField<T, K> extends Field<K> {

    protected final Faker faker;

    protected final Function<T, K> function;

    public FakerField(String name, Function<T, K> function) {
        super(name);
        this.faker = FieldUtil.FAKER_CN;
        this.function = function;
    }

    public FakerField(String name, Function<T, K> function, Locale locale) {
        super(name);
        this.faker = new Faker(locale);
        this.function = function;
    }

    public final Faker getFaker() {
        return this.faker;
    }

    public abstract T getEntity();

    @Override
    public final K get() {
        return this.function.apply(this.getEntity());
    }
}
