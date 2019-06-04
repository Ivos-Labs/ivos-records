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
import com.ivoslabs.records.dtos.copy.CopyOkDTO;
import com.ivoslabs.records.parsers.CopyParser;

/**
 * @author www.ivoslabs.com
 *
 */
public class TestCopyOk {

    @Test
    public void testToObject() {
	CopyParser cex = new CopyParser();

	CopyOkDTO cdto = cex.toObject("d1204.420190306120232", CopyOkDTO.class);

	String expected = "CopyOkDTO [field=d, field2=1, field3=2, field4=false, field5=4.4, field6=Wed Mar 06 12:02:32 CST 2019]";
	String actual = cdto.toString();

	assertEquals(expected, actual);
    }

    @Test
    public void testToObjects() {
	CopyParser cex = new CopyParser();

	List<String> crows = new ArrayList<String>();
	crows.add("a2304.520190306120232");
	crows.add("d1204.420190306120232");

	List<CopyOkDTO> cdtos = cex.toObjects(crows, CopyOkDTO.class);

	String expected = "CopyOkDTO [field=d, field2=1, field3=2, field4=false, field5=4.4, field6=Wed Mar 06 12:02:32 CST 2019]";
	String actual = null;
	for (CopyOkDTO dataDTO : cdtos) {
	    actual = dataDTO.toString();
//	    System.out.println(actual);
	}

	assertEquals(expected, actual);
    }

    @Test
    public void testString() {
	CopyParser cex = new CopyParser();

	CopyOkDTO dto = new CopyOkDTO();
	dto.setField("d");
	dto.setField2(1);
	dto.setField3(2);
	dto.setField4(false);
	dto.setField5(4.1);
	try {
	    dto.setField6(new DateLatinConverver().toObject("20190306120232"));
	} catch (ParseException e) {
	    e.printStackTrace();
	}

	String actual = cex.toString(dto);

	String expected = "d1204.120190306120232";

	assertEquals(expected, actual);
    }

    @Test
    public void testStrings() {
	CopyParser cex = new CopyParser();

	CopyOkDTO dto = new CopyOkDTO();
	dto.setField("d");
	dto.setField2(1);
	dto.setField3(2);
	dto.setField4(false);
	dto.setField5(4.1);
	try {
	    dto.setField6(new DateLatinConverver().toObject("20190306120232"));
	} catch (ParseException e) {
	    e.printStackTrace();
	}

	String actual = cex.toStrings(Arrays.asList(dto, dto)).get(1);

	String expected = "d1204.120190306120232";

	assertEquals(expected, actual);
    }

    @Test
    public void testFile() {

	CopyParser ex = new CopyParser();

	ObjectConsumer<CopyOkDTO> action = new ObjectConsumer<CopyOkDTO>() {

	    public void process(CopyOkDTO object) {
		System.out.println(object.toString());
	    }
	};

	ex.fileToObjects("src/test/resources/copy.cbl", CopyOkDTO.class, action);

	assertTrue(true);
    }

}
