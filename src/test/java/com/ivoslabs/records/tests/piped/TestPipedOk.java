/**
 * 
 */
package com.ivoslabs.records.tests.piped;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.ivoslabs.records.converters.DateLatinConverver;
import com.ivoslabs.records.dtos.piped.PipedDataDTO;
import com.ivoslabs.records.function.ObjectConsumer;
import com.ivoslabs.records.parsers.PipedParser;

/**
 * @author www.ivoslabs.com
 *
 */
public class TestPipedOk {

    //@Test
    public void testToObject() {
	PipedParser ex = new PipedParser();

	PipedDataDTO dto = ex.toObject("b|2|1|false|2.2|20190306120232", PipedDataDTO.class);

	String expected = "PipedOkDTO [field1=b, field2=2, field3=1, field4=false, field5=2.2, field6=Wed Mar 06 12:02:32 CST 2019]";
	String actual = dto.toString();

	assertEquals(expected, actual);
    }

    //@Test
    public void testToObjects() {
	PipedParser ex = new PipedParser();

	List<String> rows = new ArrayList<String>();
	rows.add("a|1|1|true |1.1|20190306120232");
	rows.add("b|2|1|false|2.2|20190306120232");

	List<PipedDataDTO> dtos = ex.toObjects(rows, PipedDataDTO.class);

	String expected = "PipedOkDTO [field1=b, field2=2, field3=1, field4=false, field5=2.2, field6=Wed Mar 06 12:02:32 CST 2019]";
	String actual = null;
	for (PipedDataDTO dataDTO : dtos) {
	    actual = dataDTO.toString();
//	    System.out.println(actual);
	}

	assertEquals(expected, actual);
    }

    @Test
    public void testToString() {
	PipedParser ex = new PipedParser();

	PipedDataDTO dto = new PipedDataDTO();
	dto.setField1(null);
	dto.setField2(2);
	dto.setField3(1);
	dto.setField4(false);
	dto.setField5(2.2);
	
	try {
	    dto.setField6(new DateLatinConverver().toObject("20190306"));
	} catch (ParseException e) {
	    e.printStackTrace();
	}

	String expected = "-|2|1|false|2.2|20190306";
	String actual = ex.toString(dto);

	assertEquals(expected, actual);
    }

    //@Test
    public void testToStrings() {
	PipedParser ex = new PipedParser();

	PipedDataDTO dto1 = new PipedDataDTO();
	dto1.setField1("1");
	dto1.setField2(2);
	dto1.setField3(1);
	dto1.setField4(true);
	dto1.setField5(2.2);

	PipedDataDTO dto = new PipedDataDTO();
	dto.setField1("b");
	dto.setField2(2);
	dto.setField3(1);
	dto.setField4(false);
	dto.setField5(2.2);

	try {
	    dto.setField6(new DateLatinConverver().toObject("20190306120232"));
	} catch (ParseException e) {
	    e.printStackTrace();
	}

	String expected = "b|2|1|false|2.2|20190306120232";
	String actual = ex.toStrings(Arrays.asList(dto1, dto)).get(1);

	assertEquals(expected, actual);
    }

  //  @Test
    public void testFile() {

	PipedParser ex = new PipedParser();

	ObjectConsumer<PipedDataDTO> action = new ObjectConsumer<PipedDataDTO>() {

	    public void process(PipedDataDTO object) {
		System.out.println(object.toString());
	    }
	};

	ex.fileToObjects("src/test/resources/piped.piped", PipedDataDTO.class, action);

	assertTrue(true);
    }
}
