# ivos-records

Copy DTO class example

``` java
public class CopyDTO {

    /** */
    @Pic(beginIndex = 0, size = 1)
    private String field;

    @Pic(beginIndex = 1, size = 1)
    private Integer field2;

    @Pic(beginIndex = 2, size = 1)
    private int field3;

    @Pic(beginIndex = 3, size = 1)
    private Boolean field4;

    @Pic(beginIndex = 4, size = 3)
    private Double field5;

    @Pic(beginIndex = 7, size = 14, converter = DateLatinConverver.class)
    private Date field6;
    
    // getters and setters
    
}
```
