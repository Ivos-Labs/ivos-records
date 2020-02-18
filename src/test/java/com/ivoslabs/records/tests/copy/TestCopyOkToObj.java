/**
 * 
 */
package com.ivoslabs.records.tests.copy;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.ivoslabs.records.parsers.CopyParser;
import com.ivoslabs.records.tests.copy.dtos.CopyDataDTO;

/**
 * @author www.ivoslabs.com
 *
 */
public class TestCopyOkToObj {

    @Test
    public void testToObject1() {
	CopyParser cex = new CopyParser();

	CopyDataDTO cdto = cex.toObject("b2301.220190306    ", CopyDataDTO.class);

	String expected = "CopyDataDTO [field1=b, field2=2, field3=3, field4=false, field5=1.2, field6=Wed Mar 06 00:00:00 CST 2019, field7=null]";
	String actual = cdto.toString();

	System.out.println(expected);
	System.out.println(actual);

	assertEquals(expected, actual);
    }

    @Test
    public void testToObject2() {
	CopyParser cex = new CopyParser();

	CopyDataDTO cdto = cex.toObject("b2301.220190306aa,bb", CopyDataDTO.class);

	String expected = "CopyDataDTO [field1=b, field2=2, field3=3, field4=false, field5=1.2, field6=Wed Mar 06 00:00:00 CST 2019, field7=SubField [a=aa, b=bb]]";
	String actual = cdto.toString();

	System.out.println(expected);
	System.out.println(actual);

	assertEquals(expected, actual);
    }

    @Test
    public void testToObject3() {
	CopyParser cex = new CopyParser();

	CopyDataDTO cdto = cex.toObject("b 301.220190306aa,bb", CopyDataDTO.class);

	String expected = "CopyDataDTO [field1=b, field2=null, field3=3, field4=false, field5=1.2, field6=Wed Mar 06 00:00:00 CST 2019, field7=SubField [a=aa, b=bb]]";
	String actual = cdto.toString();

	System.out.println(expected);
	System.out.println(actual);

	assertEquals(expected, actual);
    }

    @Test
    public void testToObjects() {
	CopyParser cex = new CopyParser();

	List<String> crows = new ArrayList<String>();
	crows.add("a2304.520190306     ");
	crows.add("b 301.220190306aa,bb");

	List<CopyDataDTO> cdtos = cex.toObjects(crows, CopyDataDTO.class);

	String expected = "CopyDataDTO [field1=b, field2=null, field3=3, field4=false, field5=1.2, field6=Wed Mar 06 00:00:00 CST 2019, field7=SubField [a=aa, b=bb]]";
	String actual = null;
	for (CopyDataDTO dataDTO : cdtos) {
	    actual = dataDTO.toString();
	}

	System.out.println(expected);
	System.out.println(actual);

	assertEquals(expected, actual);
    }
}
