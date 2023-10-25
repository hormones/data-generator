package com.hormones.random.generator;

import com.google.common.collect.Lists;
import com.hormones.random.DataBuilder;
import com.hormones.random.field.abs.CalculateField.ConvertField;
import com.hormones.random.field.base.UUIDStringField;
import com.hormones.random.field.calculate.MD5Field;
import com.hormones.random.field.multi.DynamicMultiField;
import com.hormones.random.field.pattern.DataSetField;
import com.hormones.random.field.range.IntegerField;
import org.junit.Test;

public class GeneratorTest {

    /**
     * 测试生成EXCEL
     */
    @Test
    public void generate_excel() {
        DataSetField<String> nameField = new DataSetField<>("姓名", Lists.newArrayList("张三", "李四", "王五", "陈东", "赵辉"));
        IntegerField ageField = new IntegerField("年龄", 18, 60);

        DataBuilder.create()
                .add(new MD5Field("唯一标识", nameField, new ConvertField<>(ageField, String::valueOf)))
                .add(nameField)
                .add(new DataSetField<>("sex", Lists.newArrayList("男", "女", "不详")))
                .add(ageField)
                // 利用配置文件制作带有级联关系的随机数据，文件位于：random-generator\src\main\resources\multi-field\目录下
                // 使用时还可以自定义列名，不自定义则使用默认的列名
                .add(new DynamicMultiField("organization.yml"))
                .add(new DynamicMultiField("address.yml", "自定义省列名", "自定义市列名"))
                .distinct(nameField)
                .toExcel("excel.xlsx", "sheet1", 200);

        // 生成的文件路径：random-generator\target\test-classes\excel.xlsx
    }

    /**
     * 测试生成SQL语句
     */
    @Test
    public void generate_sql() {
        DataSetField<String> nameField = new DataSetField<>("name", Lists.newArrayList("张三", "李四", "王五", "陈东", "赵辉"));
        DataBuilder.create()
                .add(new UUIDStringField("id"))
                .add(nameField)
                .add(new DataSetField<>("sex", Lists.newArrayList("男", "女", "不详")))
                .add(new IntegerField("age", 18, 60))
                // 利用配置文件制作带有级联关系的随机数据，文件位于：random-generator\src\main\resources\multi-field\目录下
                // 使用时还可以自定义列名，不自定义则使用默认的列名
                .add(new DynamicMultiField("organization.yml", "unit", "department", "post"))
                .add(new DynamicMultiField("address.yml", "province", "city", "district"))
                .distinct(nameField)
                .toSqlInsert("insert.sql", "user", 10);

        // 生成的文件路径：random-generator\target\test-classes\insert.sql
    }
}
