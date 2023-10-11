package com.hormones.random.field;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class DataBuilder extends Field<List<Object>> {

    protected final List<Field<?>> fields = new ArrayList<>();

    protected final List<Field<?>> distinctFields = new ArrayList<>();

    protected boolean distinct = false;

    protected Locale locale = Locale.SIMPLIFIED_CHINESE;

    private DataBuilder() {
        super("unused");
    }

    public static DataBuilder create() {
        return new DataBuilder();
    }

    public final DataBuilder add(Field<?>... fields) {
        if (ArrayUtils.isNotEmpty(fields)) {
            this.fields.addAll(Arrays.asList(fields));
        }
        return this;
    }

    public DataBuilder distinct(Field<?>... fields) {
        this.distinct = true;
        if (ArrayUtils.isNotEmpty(fields)) {
            this.distinctFields.addAll(Arrays.asList(fields));
        }
        return this;
    }

    /**
     * TODO... 目前该方法不生效
     *
     * @param locale Locale
     * @return this
     */
    public DataBuilder locale(Locale locale) {
        this.locale = locale;
        return this;
    }

    @Override
    protected List<Object> generate() {
        List<Object> next = new ArrayList<>(this.fields.size());

        Map<Integer, CalculateField<?>> calcFields = new LinkedHashMap<>();
        for (int i = 0; i < this.fields.size(); i++) {
            Field<?> field = this.fields.get(i);
            if (field instanceof CalculateField) {
                calcFields.put(i, (CalculateField<?>) field);
                continue;
            }
            next.add(field.next());
        }
        calcFields.forEach((index, field) -> {
            next.add(index, field.next());
        });
        return next;
    }

    public FieldData next(int amount) {
        List<List<Object>> dataset = new ArrayList<>(amount);

        amount = Math.max(1, amount);

        int times = 0;
        while (dataset.size() < amount) {
            List<Object> data = this.next();
            if (!this.distinct || this.isDistinctData(dataset, data)) {
                dataset.add(data);
            }
            times++;
            if (times > amount * 10) {
                System.out.println("generate data failed, looping multiple times still cannot obtain sufficient data.");
                break;
            }
        }

        return new FieldData(this.fields, dataset);
    }

    protected boolean isDistinctData(List<List<Object>> dataset, List<Object> data) {
        boolean repeat = false;
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < this.fields.size(); i++) {
            if (CollectionUtils.isEmpty(this.distinctFields) || this.distinctFields.contains(this.fields.get(i))) {
                indexes.add(i);
            }
        }
        for (List<Object> existedData : dataset) {
            boolean same = true;
            for (Integer index : indexes) {
                same = same && Objects.equals(data.get(index), existedData.get(index));
            }
            if (same) {
                repeat = true;
                break;
            }
        }
        return !repeat;
    }

    public static class FieldData {
        protected final List<Field<?>> fields;

        protected final List<List<Object>> data;

        public FieldData(List<Field<?>> fields, List<List<Object>> data) {
            this.fields = fields;
            this.data = data;
        }

        public List<Field<?>> getFields() {
            return this.fields;
        }

        public List<List<Object>> getData() {
            return this.data;
        }
    }
}
