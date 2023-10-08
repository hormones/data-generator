package com.hormones.random;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.github.javafaker.Address;
import com.google.common.collect.Lists;
import com.hormones.random.field.Field;
import com.hormones.random.field.base.ConstantField;
import com.hormones.random.field.faker.AddressField;
import com.hormones.random.field.faker.NameField;
import com.hormones.random.field.faker.PhoneField;
import com.hormones.random.field.pattern.DataSetField;
import com.hormones.random.field.pattern.DatePatternField;
import com.hormones.random.field.pattern.TimePatternField;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

public class ExcelGenerator {

    /**
     * 生成Excel文件及随机数据，包含1个sheet
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        LocalDate from1 = LocalDate.of(2020, 1, 1);
        LocalDate to1 = LocalDate.of(2023, 6, 1);
        LocalTime from2 = LocalTime.of(10, 10, 10);
        LocalTime to2 = LocalTime.of(23, 59, 59);

        List<Field<?>> fields = Lists.newArrayList(
                new NameField("姓名"),
                new ConstantField<>("毕业院校", "南西大学"),
                new DatePatternField("报道日期", from1, to1),
                new TimePatternField("报道时间", from2, to2),
                new PhoneField("手机号码"),
                new DataSetField<>("性别", Lists.newArrayList("男", "女", "不详")),
                new AddressField("城市", Address::cityName),
                new AddressField("详细地址")
        );
        ExcelGenerator.generate("test.xlsx", "测试sheet", 10, fields);
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
