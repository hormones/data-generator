package com.hormones.random.generator;

import com.google.common.io.Files;
import com.hormones.random.enums.Type;
import com.hormones.random.util.FieldUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * 生成insert语句
 */
public class SqlInsertGenerator implements Generator {
    private static final Logger LOGGER = LoggerFactory.getLogger(SqlInsertGenerator.class);

    /**
     * 生成的文件名称
     */
    private final String fileName;

    /**
     * 表名
     */
    private final String tableName;

    /**
     * 字段
     */
    private final List<String> fields;

    /**
     * 数据
     */
    private final List<List<Object>> dataList;

    public SqlInsertGenerator(String fileName, String tableName, List<String> fields, List<List<Object>> dataList) {
        this.fileName = fileName;
        this.tableName = tableName;
        this.fields = fields;
        this.dataList = dataList;
    }

    @Override
    public Type type() {
        return Type.EXCEL;
    }

    @Override
    public void generate() {
        StringBuilder sql = new StringBuilder();
        String fieldStr = this.fields.stream().reduce((a, b) -> a + ", " + b).orElse("");
        for (List<Object> data : this.dataList) {
            sql.append("INSERT INTO ")
                    .append(this.tableName)
                    .append(" (")
                    .append(fieldStr)
                    .append(") VALUES (")
                    .append(data.stream().map(datum -> {
                        if (datum instanceof String) {
                            return "'" + datum + "'";
                        }
                        return Objects.toString(datum);
                    }).reduce((a, b) -> a + ", " + b).orElse(""))
                    .append(");\n");
        }

        String filePath = FieldUtil.getPath(this.fileName);
        LOGGER.info("write to file: " + filePath);
        try {
            Files.write(sql.toString().getBytes(), new File(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
