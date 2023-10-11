package com.hormones.random;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.github.javafaker.Address;
import com.google.common.collect.Lists;
import com.hormones.random.field.DataBuilder;
import com.hormones.random.field.DataBuilder.FieldData;
import com.hormones.random.field.Field;
import com.hormones.random.field.base.ConstantField;
import com.hormones.random.field.base.ConvertField;
import com.hormones.random.field.base.UUIDLongField;
import com.hormones.random.field.calculate.MD5Field;
import com.hormones.random.field.faker.AddressField;
import com.hormones.random.field.faker.NameField;
import com.hormones.random.field.faker.PhoneField;
import com.hormones.random.field.pattern.DataSetField;
import com.hormones.random.field.pattern.DatePatternField;
import com.hormones.random.field.pattern.DateTimePatternField;
import com.hormones.random.field.pattern.StringStemField;
import com.hormones.random.field.range.IntegerField;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class ExcelGenerator {

    /**
     * 生成Excel文件及随机数据，包含1个sheet
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        LocalDate from1 = LocalDate.of(1980, 1, 1);
        LocalDate to1 = LocalDate.of(2005, 6, 1);
        LocalDateTime from2 = LocalDateTime.of(2020, 1, 1, 10, 10, 10);
        LocalDateTime to2 = LocalDateTime.of(2023, 6, 1, 23, 59, 59);

        Field<String> nameField = new NameField("姓名");

        FieldData fieldData = DataBuilder.create()
                .add(new UUIDLongField("UUID"))
                .add(new MD5Field("MD5", new ConvertField<>(nameField, String::valueOf)))
                .add(nameField)
                .add(new DataSetField<>("性别", Lists.newArrayList("男", "女", "不详")))
                .add(new IntegerField("年龄", 18, 60))
                .add(new ConstantField<>("毕业院校", "南西大学"))
                .add(new DatePatternField("出生日期", from1, to1))
                .add(new PhoneField("手机号码"))
                .add(new DateTimePatternField("打卡时间", from2, to2))
                .add(new DataSetField<>("部门", Lists.newArrayList("开发部门", "测试部门", "营销部门", "运营部门")))
                .add(new DataSetField<>("岗位", Lists.newArrayList("开发工程师", "测试工程师", "开发经理", "测试经理")))
                .add(new StringStemField(new IntegerField("工作进度", 10, 100, 1), "%"))
                .add(new AddressField("城市", Address::cityName))
                .add(new AddressField("详细地址"))
                .distinct(nameField)
                .next(200);

        ExcelGenerator.generate("test.xlsx", "测试sheet", fieldData.getFields(), fieldData.getData());
    }

    /**
     * easyexcel生成Excel文件和随机数据，包含1个sheet
     *
     * @param fileName  生成的文件名称
     * @param sheetName sheet名称
     * @param amount    生成的数据量
     * @param fields    字段定义
     */
    public static void generate(String fileName, String sheetName, int amount, List<Field<?>> fields) {
        ExcelGenerator.generate(fileName, sheetName, fields, ExcelGenerator.dataList(amount, fields));
    }

    public static void generate(String fileName, String sheetName, List<Field<?>> fields, List<List<Object>> dataList) {
        System.out.println("data size:" + dataList.size());
        System.out.println(dataList);
        EasyExcel.write(ExcelGenerator.getResourcePath(fileName))
                .sheet(sheetName)
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .head(head(fields))
                .doWrite(dataList);
    }

    public static List<List<String>> head(List<Field<?>> fields) {
        List<List<String>> list = ListUtils.newArrayList();
        for (Field<?> field : fields) {
            List<String> head = ListUtils.newArrayList();
            head.add(field.getName());
            list.add(head);
        }
        return list;
    }

    public static List<List<Object>> dataList(int amount, List<Field<?>> fields) {
        List<List<Object>> list = ListUtils.newArrayList();
        amount = Math.max(1, amount);
        for (int i = 0; i < amount; i++) {
            List<Object> data = ListUtils.newArrayList();
            for (Field<?> field : fields) {
                data.add(field.get());
            }
            list.add(data);
        }
        return list;
    }

    public static String getResourcePath(String name) {
        String root = Objects.requireNonNull(ExcelGenerator.class.getClassLoader().getResource("")).getPath();
        return root + name;
    }
}
