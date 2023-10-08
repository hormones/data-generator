package com.hormones.random.util;

import com.github.javafaker.Faker;

import java.util.Locale;

public class FieldUtil {

    public static final Faker FAKER_CN = new Faker(Locale.SIMPLIFIED_CHINESE);
    public static final Faker FAKER_EN = new Faker(Locale.ENGLISH);

}
