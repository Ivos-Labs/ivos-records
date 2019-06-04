# ivos-records

Copy DTO class example

Reading and saving piped file


Header pojo example


``` java

public class PipedHeader {

    @PipedField(0)
    private String field1;

    @PipedField(1)
    private Integer field2;
    
    ...
    ...
```

Data pojo example


``` java

public class PipedOkDTO {

    @PipedField(0)
    private String field1;

    @PipedField(1)
    private Integer field2;

    @PipedField(2)
    private int field3;

    @PipedField(3)
    private Boolean field4;

    @PipedField(4)
    private Double field5;

    @Converter(DateLatinConverver.class)
    @PipedField(5)
    private Date field6;
    
    ...
    ...
    
```

Tail pojo example


``` java
public class PipedTail {

    @PipedField(0)
    private Integer field1;

    @PipedField(1)
    private String field2;
    
    ...
    ...

```

Saving piped file

``` java
	// headers

	PipedHeader header1 = new PipedHeader();
	header1.setField1("headerA");
	header1.setField2(1);

	PipedHeader header2 = new PipedHeader();
	header2.setField1("headerB");
	header2.setField2(2);

	List<PipedHeader> headers = new ArrayList<PipedHeader>();
	headers.add(header1);
	headers.add(header2);
	
	// data

	PipedOkDTO dto1 = new PipedOkDTO();
	dto1.setField1("a");
	dto1.setField2(1);
	dto1.setField3(1);
	dto1.setField4(true);
	dto1.setField5(1.1);
	dto1.setField6(new Date());

	PipedOkDTO dto2 = new PipedOkDTO();
	dto2.setField1("b");
	dto2.setField2(2);
	dto2.setField3(22);
	dto2.setField4(false);
	dto2.setField5(2.2);
	dto2.setField6(new Date());

	PipedOkDTO dto3 = new PipedOkDTO();
	dto3.setField1("c");
	dto3.setField2(3);
	dto3.setField3(33);
	dto3.setField4(false);
	dto3.setField5(3.3);
	dto3.setField6(new Date());

	List<PipedOkDTO> list = new ArrayList<PipedOkDTO>();
	list.add(dto1);
	list.add(dto2);
	list.add(dto3);

	// tail

	PipedTail tail = new PipedTail();
	tail.setField1(1);
	tail.setField2("tailA");

	List<PipedTail> tails = new ArrayList<PipedTail>();
	tails.add(tail);

	// save objest in a Stacks

	Stack<PipedHeader> headerStack = new Stack<PipedHeader>();
	headerStack.addAll(headers);

	Stack<PipedOkDTO> stack = new Stack<PipedOkDTO>();
	stack.addAll(list);

	Stack<PipedTail> tailStack = new Stack<PipedTail>();
	tailStack.addAll(tails);

	PipedParser ex = new PipedParser();
	ex.objectsToFile("datahdt.piped", headerStack, stack, tailStack);

```

datahdt.piped expected content


``` 
	headerA|1
	headerB|2
	a|1|1|true|1.1|20190604182420
	b|2|22|false|2.2|20190604182420
	c|3|33|false|3.3|20190604182420
	1|tailA
```

Reading piped file

``` java
ObjectConsumer<PipedHeader> headerCons = new ObjectConsumer<PipedHeader>() {

	    public void process(PipedHeader object) {
		System.out.println(object.toString());
	    }
	};

	ObjectConsumer<PipedOkDTO> dataCons = new ObjectConsumer<PipedOkDTO>() {

	    public void process(PipedOkDTO object) {
		System.out.println(object.toString());
	    }
	};

	ObjectConsumer<PipedTail> tailCons = new ObjectConsumer<PipedTail>() {

	    public void process(PipedTail object) {
		System.out.println(object.toString());
	    }
	};

	PipedParser ex = new PipedParser();

	int headerSize = 2;
	int tailSize = 1;

	ex.fileToObjects("pipedht.piped", PipedHeader.class, headerSize, headerCons, PipedOkDTO.class, dataCons, PipedTail.class, tailSize, tailCons);



```


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
