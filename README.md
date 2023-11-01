# data-generator

生成随机假数据，主要用于生成测试数据

## random-generator

- 生成excel文件及填充随机数据：`com.hormones.random.generator.GeneratorTest#generate_excel`

  生成的文件位于：`random-generator\target\test-classes\excel.xlsx`

- 生成sql的insert语句文件及填充随机数据：`com.hormones.random.generator.GeneratorTest#generate_sql`

  生成的文件位于：`random-generator\target\test-classes\insert.sql`

### 已实现的随机功能

| 功能        | 实现类                                                    | 描述                   |
|-----------|--------------------------------------------------------|----------------------|
| UUID      | UUIDLongField/UUIDStringField                          |                      |
| MD5       | MD5Field                                               | 使用其它Field生成的数据来生成MD5 |
| 姓名        | NameField                                              | 生成随机姓名               |
| 电话号码      | PhoneField                                             | 生成随机电话号码             |
| 地址        | AddressField                                           | 生成随机地址               |
| 数字        | IntegerField/LongField                                 | 生成随机数字               |
| 小数        | DoubleField                                            | 生成随机小数               |
| 日期时间      | DateField/TimeField/DateTimeField                      | 生成随机日期时间             |
| 日期时间(格式化) | DatePatternField/TimePatternField/DateTimePatternField | 生成随机的格式化后的日期时间       |
| 字符        | StringField/StringStemField                            | 生成随机的字符串             |
| 动态级联数据    | DynamicCascadeField                                    | 通过YML配置文件生成随机级联数据    |

`DynamicCascadeField`使用说明

以生成省市区为例：

1. 新建配置文件`work.yml`并定义数据，放在`random-generator\src\main\resources\multi-field\`目录下

   该配置文件已存在，用于参考

2. 编写代码获取随机数据

   ```java
   // 获取级联数据对象
   DynamicCascadeField field = new DynamicCascadeField("work.yml")
   // 通过不断的调用next()方法获取随机数据
   List<String> data = field.next(); // data: [开发部门, 开发经理]
   
   ```

### 扩展

当已有的生成数据类无法满足需求时，可自行编写实现类继承`com.hormones.random.field.Field`抽象类，然后实现其`generate()`方法即可