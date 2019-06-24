/**
 * 
 */
package com.ivoslabs.records.tests.piped;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.ivoslabs.records.converters.DateLatinConverver;
import com.ivoslabs.records.parsers.PipedParser;
import com.ivoslabs.records.tests.commons.dtos.SubField;
import com.ivoslabs.records.tests.piped.dtos.PipedDataDTO;

/**
 * @author www.ivoslabs.com
 *
 */
public class TestPipedOkToStr {

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
	dto.setField7(new SubField("aa", "bb"));

	String expected = "-|2|1|false|2.2|20190306|aa,bb";
	String actual = ex.toString(dto);

	assertEquals(expected, actual);
    }

    @Test
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
	    dto.setField6(new DateLatinConverver().toObject("20190306"));
	} catch (ParseException e) {
	    e.printStackTrace();
	}

	List<String> dtos = ex.toStrings(Arrays.asList(dto1, dto));

	String expected = "b|2|1|false|2.2|20190306|-";

	String actual = null;
	for (String string : dtos) {
	    actual = string;
	}

	assertEquals(expected, actual);
    }

}
