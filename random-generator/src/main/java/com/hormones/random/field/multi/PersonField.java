package com.hormones.random.field.multi;

import com.hormones.random.field.abs.MultiField;
import com.hormones.random.field.faker.NameField;
import com.hormones.random.field.range.DateField;
import com.hormones.random.util.FieldUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PersonField extends MultiField<Object> {
    private final NameField nameField;

    private final DateField birthDayField;

    private final LocalDate dateNow;

    private final String ageFieldName;

    public PersonField() {
        super("useless");
        this.dateNow = LocalDate.now();
        this.nameField = new NameField("name");
        this.birthDayField = new DateField("birthDay", dateNow.minusYears(60), dateNow.minusYears(18));
        this.ageFieldName = "age";
    }

    @Override
    protected List<Object> generate() {
        LocalDate birthDay = this.birthDayField.next();
        int age = this.dateNow.getYear() - birthDay.getYear();

        List<Object> data = new ArrayList<>();
        data.add(nameField.next());
        data.add(birthDay.format(FieldUtil.DEFAULT_DATE_FORMATTER));
        data.add(age);
        return data;
    }

    @Override
    public List<String> getFields() {
        List<String> fields = new ArrayList<>();
        fields.add(nameField.getName());
        fields.add(birthDayField.getName());
        fields.add(ageFieldName);
        return fields;
    }
}
