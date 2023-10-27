package com.hormones.random.field.range;


import com.hormones.random.field.abs.RangeField;

import java.util.Random;

public class DoubleField extends RangeField<Double> {

    public DoubleField(String name) {
        this(name, Double.MIN_VALUE, Double.MAX_VALUE, 0.0);
    }

    public DoubleField(String name, Double from, Double to) {
        this(name, from, to, 0.0);
    }

    public DoubleField(String name, Double from, Double to, double auto) {
        super(name, from, to, auto);
    }

    @Override
    protected Double getIncrement() {
        // 边界值处理,防止越界
        double autoValue = this.auto.doubleValue();
        if (autoValue > 0 && Double.MAX_VALUE - autoValue < this.value) {
            return this.from;
        }
        if (autoValue < 0 && Double.MIN_VALUE - autoValue > this.value) {
            return this.from;
        }
        return this.value + autoValue;
    }

    @Override
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    protected Double getRandom() {
        if (this.from.equals(this.to)) {
            return this.from;
        }
        Random random = new Random();
        return random.doubles(Math.min(this.from, this.to), Math.max(this.from, this.to)).findFirst().getAsDouble();
    }

    public static void main(String[] args) {
        DoubleField doubleField = new DoubleField("test", 1.0, 50.2, 1.1);
        System.out.println(doubleField.next());
        System.out.println(doubleField.next());
        System.out.println(doubleField.next());
        System.out.println(doubleField.next());
        System.out.println(doubleField.next());
        System.out.println(doubleField.next());
        System.out.println(doubleField.next());
    }
}
