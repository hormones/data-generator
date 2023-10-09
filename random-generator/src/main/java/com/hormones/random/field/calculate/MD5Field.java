package com.hormones.random.field.calculate;

import com.hormones.random.field.CalculateField;
import com.hormones.random.field.DataBuilder.FieldValues;
import com.hormones.random.util.FieldUtil;

import java.util.List;

public class MD5Field extends CalculateField<String> {

    public MD5Field(String name) {
        super(name);
    }

    @Override
    public String calculate(List<FieldValues<?>> fields) {
        StringBuilder str = new StringBuilder();
        for (FieldValues<?> fieldValues : fields) {
            str.append(fieldValues.getFuncValue(this.getIndex()));
        }
        return FieldUtil.calculateMD5(str.toString());
    }
}
