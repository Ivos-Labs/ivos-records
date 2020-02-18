/**
 * 
 */
package com.ivoslabs.records.tests.copy;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.Arrays;

import org.junit.Test;

import com.ivoslabs.records.converters.DateLatinConverver;
import com.ivoslabs.records.parsers.CopyParser;
import com.ivoslabs.records.tests.commons.dtos.SubField;
import com.ivoslabs.records.tests.copy.dtos.CopyDataDTO;

/**
 * @author www.ivoslabs.com
 *
 */
public class TestCopyOkToStr {

    @Test
    public void testString1() {
	CopyParser cex = new CopyParser();

	CopyDataDTO dto = new CopyDataDTO();
	dto.setField1("");
	dto.setField2(2);
	dto.setField3(3);
	dto.setField4(false);
	dto.setField5(1.2);

	try {
	    dto.setField6(new DateLatinConverver().toObject("20190306"));
	} catch (ParseException e) {
	    e.printStackTrace();
	}

	String actual = cex.toString(dto);

	String expected = " 2301.220190306-    ";

	assertEquals(expected, actual);
    }

    @Test
    public void testString2() {
	CopyParser cex = new CopyParser();

	CopyDataDTO dto = new CopyDataDTO();
	dto.setField1("b");
	dto.setField2(2);
	dto.setField3(3);
	dto.setField4(false);
	dto.setField5(1.2);

	try {
	    dto.setField6(new DateLatinConverver().toObject("20190306"));
	} catch (ParseException e) {
	    e.printStackTrace();
	}

	dto.setField7(new SubField("aa", "bb"));

	String actual = cex.toString(dto);

	String expected = "b2301.220190306aa,bb";

	assertEquals(expected, actual);
    }

    @Test
    public void testStrings() {
	CopyParser cex = new CopyParser();

	CopyDataDTO dto1 = new CopyDataDTO();
	dto1.setField1("a");
	dto1.setField2(1);
	dto1.setField3(2);
	dto1.setField4(false);
	dto1.setField5(1.1);
	try {
	    dto1.setField6(new DateLatinConverver().toObject("20190306"));
	} catch (ParseException e) {
	    e.printStackTrace();
	}

	CopyDataDTO dto2 = new CopyDataDTO();
	dto2.setField1("b");
	dto2.setField2(2);
	dto2.setField3(3);
	dto2.setField4(false);
	dto2.setField5(1.2);

	try {
	    dto2.setField6(new DateLatinConverver().toObject("20190306"));
	} catch (ParseException e) {
	    e.printStackTrace();
	}

	String actual = cex.toStrings(Arrays.asList(dto2, dto2)).get(1);

	String expected = "b2301.220190306-    ";

	assertEquals(expected, actual);
    }

}
