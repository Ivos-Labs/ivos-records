# ivos-records

Copy DTO class example


``` copy
a1204.120190306120232
b2214.120190306120232
c3204.120190306120232
d4204.120190306120232

```

``` java
public class CopyOkDTO {

    @Pic(beginIndex = 0, size = 1)
    private String field;

    @Pic(beginIndex = 1, size = 1)
    private Integer field2;

    @Pic(beginIndex = 2, size = 1)
    private int field3;

    @Converter(Boolean10Converter.class)
    @Pic(beginIndex = 3, size = 1)
    private Boolean field4;

    @Pic(beginIndex = 4, size = 3)
    private Double field5;

    @Converter(DateLatinConverver.class)
    @Pic(beginIndex = 7, size = 14)
    private Date field6;
    
    // getters and setters
    
}
```
