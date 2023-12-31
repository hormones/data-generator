package com.hormones.random;

import com.hormones.random.field.Field;
import com.hormones.random.field.abs.CalculateField;
import com.hormones.random.field.abs.MultiField;
import com.hormones.random.generator.ExcelGenerator;
import com.hormones.random.generator.Generator;
import com.hormones.random.generator.SqlInsertGenerator;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class DataBuilder extends Field<List<Object>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataBuilder.class);

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
    @SuppressWarnings("rawtypes")
    protected List<Object> generate() {
        List<Object> next = new ArrayList<>();

        Map<Integer, CalculateField<?>> calcFields = new LinkedHashMap<>();
        int index = 0;
        for (Field<?> field : this.fields) {
            if (field instanceof CalculateField) {
                calcFields.put(index, (CalculateField<?>) field);
                continue;
            }
            if (field instanceof MultiField) {
                MultiField multiField = (MultiField) field;
                List data = (List) multiField.next();
                for (int i = 0; i < multiField.getSize(); i++) {
                    next.add(data.size() > i ? data.get(i) : "");
                }
                index += multiField.getSize();
            } else {
                index++;
                next.add(field.next());
            }
        }
        calcFields.forEach((i, field) -> next.add(i, field.next()));
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
                LOGGER.warn("generate data failed, looping multiple times still cannot obtain sufficient data.");
                break;
            }
        }
        List<String> fieldNames = new ArrayList<>();
        for (Field<?> field : this.fields) {
            if (field instanceof MultiField) {
                fieldNames.addAll(((MultiField<?>) field).getFields());
            } else {
                fieldNames.add(field.getName());
            }
        }

        LOGGER.info("data generated success, size: " + dataset.size());
        return new FieldData(fieldNames, dataset);
    }

    public FieldData toExcel(String fileName, String sheetName, int amount) {
        FieldData fieldData = this.next(amount);
        Generator generator = new ExcelGenerator(fileName, sheetName, fieldData.getFields(), fieldData.getData());
        generator.generate();
        return fieldData;
    }

    public FieldData toSqlInsert(String fileName, String tableName, int amount) {
        FieldData fieldData = this.next(amount);
        Generator generator = new SqlInsertGenerator(fileName, tableName, fieldData.getFields(), fieldData.getData());
        generator.generate();
        return fieldData;
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
        protected final List<String> fields;

        protected final List<List<Object>> data;

        public FieldData(List<String> fields, List<List<Object>> data) {
            this.fields = fields;
            this.data = data;
        }

        public List<String> getFields() {
            return this.fields;
        }

        public List<List<Object>> getData() {
            return this.data;
        }
    }
}
