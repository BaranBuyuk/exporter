# exporter
This project gives you a very fast way to export your list of objects as an excel file.
You can excel the list you want in two lines. Here's an example for that.

1.First, we add an @ExcelColumnName to the object you want to retrieve the list. For example;

```java
    @ExcelColumnName(value = "")
    private final Integer customerId;
    @ExcelColumnName(value = "name", showType = ExcelColumnName.ShowType.LOWERCASE)
    private final String name;
    @ExcelColumnName(value = "Surname", showType = ExcelColumnName.ShowType.UPPERCASE)
    private final String surname;
    @ExcelColumnName(value = "Email")
    private final String email;
```
* Value = Column name in Excel file
* ShowType = Uppercase, lowercase, none(default none)

* If the value field is empty, it will come from the area within the other object.

# Example for create excel file

```java

new ExportToExcel.Builder("C:/Baran/export/", "Movies", ExcelType.XLS)
                .withModel(Model.of(TestModel.class, list)).build().export();

```
