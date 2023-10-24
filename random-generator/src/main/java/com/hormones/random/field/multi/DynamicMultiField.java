package com.hormones.random.field.multi;

import com.hormones.random.field.CascadeField;
import com.hormones.random.field.MultiField;
import com.hormones.random.field.pattern.DataSetField;
import org.apache.commons.collections4.CollectionUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DynamicMultiField extends MultiField<String> {
    private static final Map<String, Config> FILE_CONFIG = new HashMap<>();

    public DynamicMultiField(String fileName, String... customNames) {
        super(fileName, getConfig(fileName).getFields(), Arrays.asList(customNames));
    }

    @Override
    protected DataSetField<CascadeField<String>> initConfig() {
        Config config = getConfig(this.getName());
        List<Datum> configData = config.getData();
        List<CascadeField<String>> data = new ArrayList<>();
        for (Datum datum : configData) {
            data.add(buildDatum(datum));
        }
        return new DataSetField<>("useless", data);
    }

    private static CascadeField<String> buildDatum(Datum datum) {
        String value = datum.getValue();
        List<Datum> children = datum.getChildren();
        if (CollectionUtils.isNotEmpty(children)) {
            List<CascadeField<String>> childrenList = new ArrayList<>();
            for (Datum child : children) {
                childrenList.add(buildDatum(child));
            }
            return new CascadeField<>(value, childrenList.toArray(new CascadeField[0]));
        } else {
            return new CascadeField<>(value);
        }
    }

    @SuppressWarnings({"DataFlowIssue", "IOStreamConstructor"})
    private static Config getConfig(String fileName) {
        if (Objects.isNull(fileName)) {
            throw new RuntimeException("file name is null");
        }
        Config config = FILE_CONFIG.get(fileName);
        if (Objects.isNull(config)) {
            URL basePath = DynamicMultiField.class.getResource("/");
            String filePath = basePath.getPath() + "multi-field/" + fileName;
            System.out.println("read config file: " + filePath);
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
