# ivos-records

Utility to parse piped files and COPYs to pojos


**Reading and saving piped file**


Example piped header pojo


``` java

public class PipedHeader {

    @PipedField(0)
    private String field1;

    @PipedField(1)
    private Integer field2;
    
    ...
    ...
```

Example piped data pojo 


``` java

public class PipedDataDTO {

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

Example piped tail pojo


``` java

public class PipedTail {

    @PipedField(0)
    private Integer field1;

    @PipedField(1)
    private String field2;
    
    ...
    ...

```

Saving a piped file

``` java

	// headers

	PipedHeader header1 = new PipedHeader();
	header1.setField1("headerA");
	header1.setField2(1);

	PipedHeader header2 = new PipedHeader();
	header2.setField1("headerB");
	header2.setField2(2);

   // 
	List<PipedHeader> headers = new ArrayList<PipedHeader>();
	headers.add(header1);
	headers.add(header2);
	
	// data

	PipedDataDTO dto1 = new PipedDataDTO();
	dto1.setField1("a");
	dto1.setField2(1);
	dto1.setField3(1);
	dto1.setField4(true);
	dto1.setField5(1.1);
	dto1.setField6(new Date());

	PipedDataDTO dto2 = new PipedDataDTO();
	dto2.setField1("b");
	dto2.setField2(2);
	dto2.setField3(22);
	dto2.setField4(false);
	dto2.setField5(2.2);
	dto2.setField6(new Date());

	PipedDataDTO dto3 = new PipedDataDTO();
	dto3.setField1("c");
	dto3.setField2(3);
	dto3.setField3(33);
	dto3.setField4(false);
	dto3.setField5(3.3);
	dto3.setField6(new Date());

	List<PipedDataDTO> list = new ArrayList<PipedDataDTO>();
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

	Stack<PipedDataDTO> dataStack = new Stack<PipedDataDTO>();
	dataStack.addAll(list);

	Stack<PipedTail> tailStack = new Stack<PipedTail>();
	tailStack.addAll(tails);

	PipedParser pipedParser = new PipedParser();
	pipedParser.objectsToFile("datahdt.piped", headerStack, dataStack, tailStack);


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

**Reading piped file**

``` java


	// PipedHeader consumer (action to do for each PipedHeader)
	ObjectConsumer<PipedHeader> headerConsumer = new ObjectConsumer<PipedHeader>() {
	    
	    public void process(PipedHeader object) {
		    System.out.println(object.toString());
	    }
	    
	};

	// PipedDataDTO consumer (action to do for each PipedDataDTO)
	ObjectConsumer<PipedDataDTO> dataConsumer = new ObjectConsumer<PipedDataDTO>() {
	    
	    public void process(PipedDataDTO object) {
		    System.out.println(object.toString());
	    }
	    
	};

	// PipedTail consumer (action to do for each PipedTail)
	ObjectConsumer<PipedTail> tailConsumer = new ObjectConsumer<PipedTail>() {
	    public void process(PipedTail object) {
		    System.out.println(object.toString());
	    }
	};

	PipedParser pipedParser = new PipedParser();

	int headerSize = 2;
	int tailSize = 1;

	String file = "pipedht.piped";
	
	// read file
	pipedParser.fileToObjects(file,
		PipedHeader.class, headerSize, headerConsumer,
		PipedDataDTO.class, dataConsumer,
		PipedTail.class, tailSize, tailConsumer);



```

**Reading and saving COPY file**


Example COPY header pojo


``` java

public class CopyHeader {

    @Pic(beginIndex = 0, size = 7)
    private String field1;

    @Pic(beginIndex = 7, size = 5)
    private int field2;

    @Pic(beginIndex = 12, size = 5)
    private int field3;
    
```

Example COPY data pojo

``` java

public class CopyDataDTO {

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


Saving a COPY file

``` java

	// headers

	CopyHeader header1 = new CopyHeader();
	header1.setField1("headerA");
	header1.setField2(1);
	header1.setField3(2);

	CopyHeader header2 = new CopyHeader();
	header2.setField1("headerB");
	header2.setField2(2);

	List<CopyHeader> headers = new ArrayList<CopyHeader>();
	headers.add(header1);
	headers.add(header2);

	// data

	CopyDataDTO dto1 = new CopyDataDTO();
	dto1.setField("");
	dto1.setField2(1);
	dto1.setField3(1);
	dto1.setField4(true);
	dto1.setField5(1.1);
	dto1.setField6(new Date());

	CopyDataDTO dto2 = new CopyDataDTO();
	dto2.setField("b");
	dto2.setField2(2);
	dto2.setField3(22);
	dto2.setField4(false);
	dto2.setField5(2.2);
	dto2.setField6(new Date());

	CopyDataDTO dto3 = new CopyDataDTO();
	dto3.setField("c");
	dto3.setField2(3);
	dto3.setField3(33);
	dto3.setField4(false);
	dto3.setField5(3.3);
	dto3.setField6(new Date());

	List<CopyDataDTO> list = new ArrayList<CopyDataDTO>();
	list.add(dto1);
	list.add(dto2);
	list.add(dto3);

	// save objest in a Stacks

	Stack<CopyHeader> headerStack = new Stack<CopyHeader>();
	headerStack.addAll(headers);

	Stack<CopyDataDTO> dataStack = new Stack<CopyDataDTO>();
	dataStack.addAll(list);

	CopyParser copyParser = new CopyParser();
	
	String file = "data.copy";
	
	copyParser.objectsToFileHD(file, headerStack, dataStack);
	

```

data.copy expected content


``` copy
headerA1    2
headerB2    0
 1111.120190606173408
b2202.220190606173408
c3303.320190606173408

```

Reading copy file

``` java

   // CopyHeader consumer (action to do for each CopyHeader)
	ObjectConsumer<CopyHeader> headerConsumer = new ObjectConsumer<CopyHeader>() {

	    public void process(CopyHeader object) {
		     System.out.println(object.toString());
	    }
	    
	};

   // CopyDataDTO consumer (action to do for each CopyDataDTO)
	ObjectConsumer<CopyDataDTO> dataConsumer = new ObjectConsumer<CopyDataDTO>() {

	    public void process(CopyDataDTO object) {
		     System.out.println(object.toString());
	    }
	};


	CopyParser copyParser = new CopyParser();

	int headerSize = 2;
	
	String file = "pipe.copy";

	copyParser.fileToObjects(file, 
	     CopyHeader.class, headerSize, headerConsumer, 
	     CopyDataDTO.class, dataConsumer);



```




