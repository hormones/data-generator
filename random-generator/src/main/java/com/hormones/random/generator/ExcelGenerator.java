package com.hormones.random.generator;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.hormones.random.enums.Type;
import com.hormones.random.field.Field;
import com.hormones.random.util.FieldUtil;

import java.util.List;

/**
 * Excel生成器，只能生成一个sheet
 */
public class ExcelGenerator implements Generator {

    /**
     * 生成的文件名称
     */
    private final String fileName;

    /**
     * sheet名称
     */
    private final String sheetName;

    /**
     * 字段
     */
    private final List<Field<?>> fields;

    /**
     * 数据
     */
    private final List<List<Object>> dataList;

    public ExcelGenerator(String fileName, String sheetName, List<Field<?>> fields, List<List<Object>> dataList) {
        this.fileName = fileName;
        this.sheetName = sheetName;
        this.fields = fields;
        this.dataList = dataList;
    }

    @Override
    public Type type() {
        return Type.EXCEL;
    }

    @Override
    public void generate() {
        String filePath = FieldUtil.getPath(fileName);
        System.out.println("   INFO: write to file: " + filePath);
        EasyExcel.write(filePath)
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


}
