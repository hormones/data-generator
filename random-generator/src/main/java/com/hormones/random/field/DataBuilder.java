package com.hormones.random.field;

import com.hormones.random.field.range.IntegerField;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DataBuilder {

    protected final int amount;

    protected final List<FieldValues<?>> fields = new ArrayList<>();

    protected boolean distinct = false;

    protected Locale locale = Locale.SIMPLIFIED_CHINESE;

    private DataBuilder(int amount) {
        this.amount = Math.max(1, amount);
    }

    public static DataBuilder create(int amount) {
        return new DataBuilder(amount);
    }

    /**
     * 添加需要生成随机数的字段
     *
     * @param field    字段定义
     * @param calcFunc 标识该字段需要参与CalculateField字段的计算，calcFunc得到的值会作为计算值
     * @param <T>      T
     * @return this
     */
    public <T> DataBuilder add(Field<T> field, Function<T, String> calcFunc) {
        this.fields.add(new FieldValues<T>(field, amount, calcFunc));
        if (field instanceof CalculateField) {
            ((CalculateField<?>) field).setFields(this.fields);
        }
        return this;
    }

    /**
     * @see #add(Field, Function)
     */
    public <T> DataBuilder add(Field<T> field) {
        return this.add(field, null);
    }

    public DataBuilder distinct() {
        this.distinct = true;
        return this;
    }

    /**
     * 目前该方法不生效
     *
     * @param locale Locale
     * @return this
     */
    public DataBuilder locale(Locale locale) {
        this.locale = locale;
        return this;
    }

    public FieldData get() {
        List<List<Object>> result = new ArrayList<>();
        for (int i = 0; i < this.amount; i++) {
            List<Object> data = new ArrayList<>();
            for (FieldValues<?> field : this.fields) {
                data.add(field.getValue(i));
            }
            boolean isRepeat = false;
            if (this.distinct) {
                for (List<Object> existedData : result) {
                    boolean same = true;
                    for (int j = 0; j < existedData.size(); j++) {
                        same = same && Objects.equals(existedData.get(j), data.get(j));
                    }
                    if (same) {
                        isRepeat = true;
                        break;
                    }
                }
            }
            if (!isRepeat) {
                result.add(data);
            }
        }
        return new FieldData(this.fields, result);
    }

    public static class FieldValues<T> {
        private final Field<T> field;

        private final int amount;
        private final Function<T, String> func;

        private final List<T> values = new ArrayList<>();

        private final List<String> funcValues = new ArrayList<>();

        public FieldValues(Field<T> field, int amount, Function<T, String> func) {
            this.field = field;
            this.amount = Math.max(1, amount);
            this.func = func;
        }

        public Field<T> getField() {
            return field;
        }

        public int getAmount() {
            return amount;
        }

        public Function<T, String> getFunc() {
            return func;
        }

        public T getValue(int index) {
            this.generateData();
            if (index >= this.values.size()) {
                return null;
            }
            return this.values.get(index);
        }

        public String getFuncValue(int index) {
            this.generateData();
            if (index >= this.funcValues.size()) {
                return "";
            }
            return this.funcValues.get(index);
        }

        protected void generateData() {
            if (CollectionUtils.isEmpty(values)) {
                for (int i = 0; i < this.amount; i++) {
                    T value = this.field.get();
                    this.values.add(value);
                    if (this.func != null) {
                        this.funcValues.add(this.func.apply(value));
                    }
                }
            }
        }
    }

    public static class FieldData {
        protected final List<Field<?>> fields;

        protected final List<List<Object>> data;

        public FieldData(List<FieldValues<?>> fields, List<List<Object>> data) {
            this.fields = fields.stream().map(f -> f.field).collect(Collectors.toList());
            this.data = data;
        }

        public List<Field<?>> getFields() {
            return this.fields;
        }

        public List<List<Object>> getData() {
            return this.data;
        }
    }

    public static void main(String[] args) {
        FieldData fieldData = DataBuilder.create(10)
                // .add(new IntegerField("as", 3, 10), String::valueOf)
                // .add(new HashField("hash"))
                .add(new IntegerField("ds", 3, 10), String::valueOf)
                .get();
        System.out.println(fieldData.getFields());
        System.out.println(fieldData.getData());
    }
}
