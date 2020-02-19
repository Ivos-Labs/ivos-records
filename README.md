# ivos-records

Utility to parse piped files and COPYs to pojos


**Reading and saving piped file**

 

Example piped data pojo 


``` java

@IfNull("-")
public class PipedDataDTO {

    @Piped(value = 0, maxSize = 3)
    private String field1;

    @Piped(1)
    private Integer field2;

    @Piped(2)
    private int field3;

    @Piped(3)
    private Boolean field4;

    @Piped(4)
    private Double field5;

    @Converter(DateLatinConverver.class)
    @Piped(5)
    private Date field6;

    @Converter(SubConverter.class)
    @Piped(6)
    private SubField field7;
    
    ...
    ...
    
```

Example piped header pojo


``` java

public class PipedHeader {

    @Piped(0)
    private String field1;

    @Piped(1)
    private Integer field2;
    
    ...
    ...
```

Example piped tail pojo


``` java

public class PipedTail {

    @Piped(0)
    private Integer field1;

    @Piped(1)
    private String field2;
    
    ...
    ...

```

Example subfield 


``` java

public class SubField {

    private String a;

    private String b;
    
    ...
    ...
```
Example subfield converter


``` java

public class SubConverter implements FieldConverter<SubField> {

 
    public String toString(SubField object, String... args) throws Exception {
	    return object != null ? object.getA() + "," + object.getB() : "";
    }

 
    public SubField toObject(String string, String... args) throws Exception {
	    return string != null && string.split(",").length > 1 ? new SubField(string.split(",")[0], string.split(",")[1]) : null;
    }
    
    ...
    ...
```

Saving a piped file

``` java

	// creating header objects
	PipedHeader header1 = new PipedHeader();
	header1.setField1("headerA");
	header1.setField2(1);

	PipedHeader header2 = new PipedHeader();
	header2.setField1("headerB");
	header2.setField2(2);

	// saving header objects into a list
	List<PipedHeader> headers = new ArrayList<PipedHeader>();
	headers.add(header1);
	headers.add(header2);

	// creating data objects
	PipedDataDTO dto1 = new PipedDataDTO();
	dto1.setField1("a");
	dto1.setField2(null);
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
	dto2.setField7(new SubField("QW", "WE"));

	PipedDataDTO dto3 = new PipedDataDTO();
	dto3.setField1("c");
	dto3.setField2(3);
	dto3.setField3(33);
	dto3.setField4(false);
	dto3.setField5(3.3);
	dto3.setField6(new Date());

	// saving data objects into a list
	List<PipedDataDTO> list = new ArrayList<PipedDataDTO>();
	list.add(dto1);
	list.add(dto2);
	list.add(dto3);

	// creating tail objects
	PipedTail tail = new PipedTail();
	tail.setField1(1);
	tail.setField2("tailA");

	// saving tail objects into a list
	List<PipedTail> tails = new ArrayList<PipedTail>();
	tails.add(tail);
	
	//
	
	// save list objects into Stacks
	Stack<PipedHeader> headerStack = new Stack<PipedHeader>();
	headerStack.addAll(headers);
	
	Stack<PipedDataDTO> dataStack = new Stack<PipedDataDTO>();
	dataStack.addAll(list);

	Stack<PipedTail> tailStack = new Stack<PipedTail>();
	tailStack.addAll(tails);

	String file = "datahdt.psv";

	// append objects into a file
	PipedParser pipedParser = new PipedParser();
	pipedParser.objectsToFile(file, headerStack, dataStack, tailStack);


```

datahdt.piped expected content


``` 
headerA|1
headerB|2
a|-|1|true|1.1|20190623|-
b|2|22|false|2.2|20190623|QW,WE
c|3|33|false|3.3|20190623|-
1|tailA

```

**Reading piped file**

``` java

	// PipedHeader consumer (action to do for each PipedHeader)
	Consumer<PipedHeader> headerConsumer = new Consumer<PipedHeader>() {

	    public void process(PipedHeader object) {
		System.out.println(object.toString());
	    }

	};

	// PipedDataDTO consumer (action to do for each PipedDataDTO)
	Consumer<PipedDataDTO> dataConsumer = new Consumer<PipedDataDTO>() {

	    public void process(PipedDataDTO object) {
		System.out.println(object.toString());
	    }

	};

	// PipedTail consumer (action to do for each PipedTail)
	Consumer<PipedTail> tailConsumer = new Consumer<PipedTail>() {
	    public void process(PipedTail object) {
		System.out.println(object.toString());
	    }
	};

	PipedParser pipedParser = new PipedParser();

	int headerSize = 2;
	int tailSize = 1;

	String file = "datahdt.psv";

	// read file
	pipedParser.processFile(file,
		PipedHeader.class, headerSize, headerConsumer,
		PipedDataDTO.class, dataConsumer,
		PipedTail.class, tailSize, tailConsumer);



```

**Reading and saving COPY file**


Example COPY data pojo

``` java

public class CopyDataDTO {

    @Pic(beginIndex = 0, size = 1)
    private String field1;

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
    @Pic(beginIndex = 7, size = 8)
    private Date field6;

    @Converter(SubConverter.class)
    @Pic(beginIndex = 15, size = 5)
    private SubField field7;
    
    // getters and setters
    
}
```

Example COPY header pojo


``` java

public class CopyHeader {

    @Pic(beginIndex = 0, size = 7)
    private String field1;

    @Pic(beginIndex = 7, size = 5)
    private int field2;

    @Pic(beginIndex = 12, size = 5)
    private Integer field3;
    
```


Example subfield 


``` java

public class SubField {

    private String a;

    private String b;
    
    ...
    ...
```
Example subfield converter


``` java

public class SubConverter implements FieldConverter<SubField> {

 
    public String toString(SubField object, String... args) throws Exception {
	    return object != null ? object.getA() + "," + object.getB() : "";
    }

 
    public SubField toObject(String string, String... args) throws Exception {
	    return string != null && string.split(",").length > 1 ? new SubField(string.split(",")[0], string.split(",")[1]) : null;
    }
    
    ...
    ...
```

Saving a COPY file

``` java

	// creating header objects
	CopyHeader header1 = new CopyHeader();
	header1.setField1("headerA");
	header1.setField2(1);
	header1.setField3(12345);

	CopyHeader header2 = new CopyHeader();
	header2.setField1("headerB");
	header2.setField2(2);

	CopyHeader header3 = new CopyHeader();
	header3.setField1("headerC");
	header3.setField2(3);
	header3.setField3(123456);

	// saving header objects into a list
	List<CopyHeader> headers = new ArrayList<CopyHeader>();
	headers.add(header1);
	headers.add(header2);
	headers.add(header3);

	// creating data objects
	CopyDataDTO dto1 = new CopyDataDTO();
	dto1.setField1("");
	dto1.setField2(1);
	dto1.setField3(1);
	dto1.setField4(true);
	dto1.setField5(1.1);
	dto1.setField6(new Date());

	CopyDataDTO dto2 = new CopyDataDTO();
	dto2.setField1("b");
	dto2.setField2(2);
	dto2.setField3(22);
	dto2.setField4(false);
	dto2.setField5(2.2);
	dto2.setField6(new Date());
	dto2.setField7(new SubField("qw", "er"));

	CopyDataDTO dto3 = new CopyDataDTO();
	dto3.setField1("c");
	dto3.setField2(3);
	dto3.setField3(33);
	dto3.setField4(false);
	dto3.setField5(3.3);
	dto3.setField6(new Date());

	// saving data objects into a list
	List<CopyDataDTO> list = new ArrayList<CopyDataDTO>();
	list.add(dto1);
	list.add(dto2);
	list.add(dto3);


	// save list objects into Stacks
	Stack<CopyHeader> headerStack = new Stack<CopyHeader>();
	headerStack.addAll(headers);

	Stack<CopyDataDTO> dataStack = new Stack<CopyDataDTO>();
	dataStack.addAll(list);

	String file = "data.copy";
	
	// append objects into a file
	CopyParser copyParser = new CopyParser();
	copyParser.objectsToFileHD(file, headerStack, dataStack);
	

```

data.copy expected content


``` copy

headerA1    12345
headerB2         
headerC3    12345
 1111.120190623     
b2202.220190623qw,er
c3303.320190623     

```

Reading copy file

``` java


	// CopyHeader consumer (action to do for each CopyHeader)
	Consumer<CopyHeader> headerConsumer = new Consumer<CopyHeader>() {

	    public void process(CopyHeader object) {
		System.out.println(object.toString());
	    }

	};

	// CopyDataDTO consumer (action to do for each CopyDataDTO)
	Consumer<CopyDataDTO> dataConsumer = new Consumer<CopyDataDTO>() {

	    public void process(CopyDataDTO object) {
		System.out.println(object.toString());
	    }
	};

	CopyParser copyParser = new CopyParser();

	int headerSize = 3;

	String file = "data.copy";

	copyParser.processFile(file,
		CopyHeader.class, headerSize, headerConsumer,
		CopyDataDTO.class, dataConsumer);



```




