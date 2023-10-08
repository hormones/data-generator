# data-generator
生成数据

## random-generator
- 生成excel文件及填充随机数据：`com.hormones.random.ExcelGenerator#main`
   
    生成的excel文件位于：`random-generator\target\classes\test.xlsx`

## 扩展
当已有的生成数据类无法满足需求时，可自行编写实现类继承`com.hormones.random.field.Field`抽象类，然后实现其`get()`方法即可