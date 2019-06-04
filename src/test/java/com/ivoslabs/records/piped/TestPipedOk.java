/**
 * 
 */
package com.ivoslabs.records.piped;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.ivoslabs.records.converters.DateLatinConverver;
import com.ivoslabs.records.core.ObjectConsumer;
import com.ivoslabs.records.dtos.PipedOkDTO;
import com.ivoslabs.records.parsers.PipedParser;

/**
 * @author www.ivoslabs.com
 *
 */
public class TestPipedOk {

    @Test
    public void testToObject() {
	PipedParser ex = new PipedParser();

	PipedOkDTO dto = ex.toObject("b|2|1|false|2.2|20190306120232", PipedOkDTO.class);

	String expected = "PipedOkDTO [field1=b, field2=2, field3=1, field4=false, field5=2.2, field6=Wed Mar 06 12:02:32 CST 2019]";
	String actual = dto.toString();

	assertEquals(expected, actual);
    }

    @Test
    public void testToObjects() {
	PipedParser ex = new PipedParser();

	List<String> rows = new ArrayList<String>();
	rows.add("a|1|1|true |1.1|20190306120232");
	rows.add("b|2|1|false|2.2|20190306120232");

	List<PipedOkDTO> dtos = ex.toObjects(rows, PipedOkDTO.class);

	String expected = "PipedOkDTO [field1=b, field2=2, field3=1, field4=false, field5=2.2, field6=Wed Mar 06 12:02:32 CST 2019]";
	String actual = null;
	for (PipedOkDTO dataDTO : dtos) {
	    actual = dataDTO.toString();
//	    System.out.println(actual);
	}

	assertEquals(expected, actual);
    }

    @Test
    public void testToString() {
	PipedParser ex = new PipedParser();

	PipedOkDTO dto = new PipedOkDTO();
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
	String actual = ex.toString(dto);

	assertEquals(expected, actual);
    }

    @Test
    public void testToStrings() {
	PipedParser ex = new PipedParser();

	PipedOkDTO dto1 = new PipedOkDTO();
	dto1.setField1("1");
	dto1.setField2(2);
	dto1.setField3(1);
	dto1.setField4(true);
	dto1.setField5(2.2);

	PipedOkDTO dto = new PipedOkDTO();
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

    @Test
    public void testFile() {

	PipedParser ex = new PipedParser();

	ObjectConsumer<PipedOkDTO> action = new ObjectConsumer<PipedOkDTO>() {

	    public void process(PipedOkDTO object) {
		System.out.println(object.toString());
	    }
	};

	ex.fileToObjects("src/test/resources/piped.piped", PipedOkDTO.class, action);

	assertTrue(true);
    }
}
