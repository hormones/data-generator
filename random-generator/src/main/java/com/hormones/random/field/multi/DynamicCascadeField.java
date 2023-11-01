package com.hormones.random.field.multi;

import com.hormones.random.field.abs.CascadeField;
import com.hormones.random.field.pattern.DataSetField;
import com.hormones.random.util.FieldUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 通过YML配置文件生成级联随机数据
 */
public class DynamicCascadeField extends CascadeField<String> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicCascadeField.class);

    private static final Map<String, Config> FILE_CONFIG = new HashMap<>();

    public DynamicCascadeField(String fileName, String... customNames) {
        super(fileName, getConfig(fileName).getFields(), Arrays.asList(customNames));
    }

    @Override
    protected DataSetField<RelationField<String>> initConfig() {
        Config config = getConfig(this.getName());
        List<Datum> configData = config.getData();
        List<RelationField<String>> data = new ArrayList<>();
        for (Datum datum : configData) {
            data.add(buildDatum(datum));
        }
        return new DataSetField<>("useless", data);
    }

    private static RelationField<String> buildDatum(Datum datum) {
        String value = datum.getValue();
        List<Datum> children = datum.getChildren();
        if (CollectionUtils.isNotEmpty(children)) {
            List<RelationField<String>> childrenList = new ArrayList<>();
            for (Datum child : children) {
                childrenList.add(buildDatum(child));
            }
            return new RelationField<>(value, childrenList.toArray(new RelationField[0]));
        } else {
            return new RelationField<>(value);
        }
    }

    @SuppressWarnings({"IOStreamConstructor"})
    private static Config getConfig(String fileName) {
        if (Objects.isNull(fileName)) {
            throw new RuntimeException("file name is null");
        }
        Config config = FILE_CONFIG.get(fileName);
        if (Objects.isNull(config)) {
            String filePath = FieldUtil.getPath("multi-field/" + fileName);
            LOGGER.info("read config file: " + filePath);
            try (InputStream in = new FileInputStream(filePath)) {
                Yaml yaml = new Yaml();
                config = yaml.loadAs(in, Config.class);
                FILE_CONFIG.put(fileName, config);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return config;
    }

    private static class Config {
        private List<String> fields;

        public List<Datum> data;

        public List<String> getFields() {
            return fields;
        }

        public void setFields(List<String> fields) {
            this.fields = fields;
        }

        public List<Datum> getData() {
            return data;
        }

        public void setData(List<Datum> data) {
            this.data = data;
        }
    }

    private static class Datum {
        private String value;

        private List<Datum> children;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public List<Datum> getChildren() {
            return children;
        }

        public void setChildren(List<Datum> children) {
            this.children = children;
        }
    }
}
