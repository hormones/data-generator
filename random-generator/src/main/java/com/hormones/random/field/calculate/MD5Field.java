package com.hormones.random.field.calculate;

import com.hormones.random.field.CalculateField;
import com.hormones.random.field.Field;
import com.hormones.random.field.base.ConvertField;
import com.hormones.random.util.FieldUtil;

public class MD5Field extends CalculateField<String> {

    public MD5Field(String name, Field<?>... fields) {
        super(name, String::valueOf, new ConvertField[fields.length]);
    }

    @Override
    public String generate() {
        StringBuilder str = new StringBuilder();
        for (ConvertField<?, String> field : this.fields) {
            if (field.getIndex() != this.getIndex() + 1) {
                throw new RuntimeException(String.format("calculate error, origin field index match failed: this[%s](%s), origin[%s](%s)",
                        this.getName(), this.getIndex(), field.getName(), field.getIndex()));
            }
            str.append(field.next().getValue());
        }
        return FieldUtil.calculateMD5(str.toString());
    }
}
