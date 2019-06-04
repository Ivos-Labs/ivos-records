/**
 * 
 */
package com.ivoslabs.records.tests.piped;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.ivoslabs.records.converters.DateLatinConverver;
import com.ivoslabs.records.dtos.piped.PipedFailDTO;
import com.ivoslabs.records.dtos.piped.PipedOkDTO;
import com.ivoslabs.records.parsers.PipedParser;

/**
 * @author www.ivoslabs.com
 *
 */
public class TestPipedFail {

    @Test
    public void testToObjects() {
	PipedParser ex = new PipedParser();

	List<String> rows = new ArrayList<String>();
	rows.add("a|1||true |1.1|20190306120232");

	String actual = null;
	try {
	    ex.toObjects(rows, PipedOkDTO.class);
	} catch (Exception e) {
	    // e.printStackTrace();
	    actual = e.getMessage();
	}

	String expected = "An error occurred setting value, original value: ''; converted value: null; class: com.ivoslabs.records.dtos.PipedOkDTO; field name: field3; field type: int; at row 1";

	assertEquals(expected, actual);
    }

    @Test
    public void testToObjects2() {
	PipedParser ex = new PipedParser();

	List<String> rows = new ArrayList<String>();
	rows.add("a|1|2|true |1.1|20190306120232");

	String actual = null;
	try {
	    ex.toObjects(rows, PipedFailDTO.class);
	} catch (Exception e) {
//	    e.printStackTrace();
	    actual = e.getMessage();
	}

	String expected = "An error occurred setting value, original value: '1.1'; converted value: null; converter: com.ivoslabs.records.converters.DateLatinConverver; class: com.ivoslabs.records.dtos.PipedFailDTO; field name: field5; field type: java.lang.Double; at row 1";

	assertEquals(expected, actual);
    }

    @Test
    public void testToStrings() {
	PipedParser ex = new PipedParser();

	PipedFailDTO dto1 = new PipedFailDTO();
	dto1.setField1("1");
	dto1.setField2(2);
	dto1.setField3(1);
	dto1.setField4(true);
	dto1.setField5(2.2);

	String expected = "An error occurred converting value: '2.2'; converter: com.ivoslabs.records.converters.DateLatinConverver; class: com.ivoslabs.records.dtos.PipedFailDTO; field: field5;";
	String actual = null;

	try {
	    dto1.setField6(new DateLatinConverver().toObject("20190306120232"));
	    ex.toStrings(Arrays.asList(dto1));
	} catch (Exception e) {
//	    e.printStackTrace();
	    actual = e.getMessage();
	}

	assertEquals(expected, actual);
    }

}
