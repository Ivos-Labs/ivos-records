/**
 *
 */
package com.ivoslabs.records.tests.piped;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.ivoslabs.records.parsers.PipedParser;
import com.ivoslabs.records.tests.piped.dtos.PipedDataDTO;

/**
 * @author www.ivoslabs.com
 *
 */
public class TestPipedOkToObj {

    @Test
    public void testToObject1() {
        PipedParser ex = new PipedParser();

        PipedDataDTO dto = ex.toObject("b   |2 |1 |false  |  2.2  |20190306  |cc,dd", PipedDataDTO.class);

        String expected = "PipedDataDTO [field1=b, field2=2, field3=1, field4=false, field5=2.2, field6=Wed Mar 06 00:00:00 CST 2019, field7=SubField [a=cc, b=dd]]";
        String actual = dto.toString();

        Assertions.assertEquals(expected, actual);
    }

    // @ifNull value
    @Test
    public void testToObject2() {
        PipedParser ex = new PipedParser();

        PipedDataDTO dto = ex.toObject("b|-|1|false|2.2|20190306|cc,dd", PipedDataDTO.class);

        String expected = "PipedDataDTO [field1=b, field2=null, field3=1, field4=false, field5=2.2, field6=Wed Mar 06 00:00:00 CST 2019, field7=SubField [a=cc, b=dd]]";
        String actual = dto.toString();

        Assertions.assertEquals(expected, actual);
    }

    // empty as null
    @Test
    public void testToObject3() {
        PipedParser ex = new PipedParser();

        PipedDataDTO dto = ex.toObject("b||1|false|2.2|20190306|cc,dd", PipedDataDTO.class);

        String expected = "PipedDataDTO [field1=b, field2=null, field3=1, field4=false, field5=2.2, field6=Wed Mar 06 00:00:00 CST 2019, field7=SubField [a=cc, b=dd]]";
        String actual = dto.toString();

        Assertions.assertEquals(expected, actual);
    }

    // space as null
    @Test
    public void testToObject4() {
        PipedParser ex = new PipedParser();

        PipedDataDTO dto = ex.toObject("b| |1|false|2.2|20190306|cc,dd", PipedDataDTO.class);

        String expected = "PipedDataDTO [field1=b, field2=null, field3=1, field4=false, field5=2.2, field6=Wed Mar 06 00:00:00 CST 2019, field7=SubField [a=cc, b=dd]]";
        String actual = dto.toString();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testToObjects() {
        PipedParser ex = new PipedParser();

        List<String> rows = new ArrayList<String>();
        rows.add("a|1|1|true |1.1|20190306|-");
        rows.add("b|2|1|false|2.2|20190306|cc,dd");

        List<PipedDataDTO> dtos = ex.toObjects(rows, PipedDataDTO.class);

        String expected = "PipedDataDTO [field1=b, field2=2, field3=1, field4=false, field5=2.2, field6=Wed Mar 06 00:00:00 CST 2019, field7=SubField [a=cc, b=dd]]";

        String actual = null;
        for (PipedDataDTO dataDTO : dtos) {
            actual = dataDTO.toString();
        }

        Assertions.assertEquals(expected, actual);
    }
}
