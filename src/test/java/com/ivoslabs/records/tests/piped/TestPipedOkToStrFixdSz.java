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
import com.ivoslabs.records.tests.piped.dtos.PipedDataFSsDTO;

/**
 * @since 1.0.0
 * @author www.ivoslabs.com
 *
 */
public class TestPipedOkToStrFixdSz {

    @Test
    public void testToString() {
        
        PipedParser ex = new PipedParser();

        PipedDataFSsDTO dto = new PipedDataFSsDTO();
        dto.setField5("");
        dto.setField6("");
        dto.setField7("");
        dto.setField8("");
        //
        dto.setField9(123456);
        dto.setField10(123456);
        dto.setField11(123);
        dto.setField12(456);
        //

        dto.setField16(2);
        //
        dto.setField17(1);
        dto.setField18(false);
        dto.setField19(2.2);

        try {
            dto.setField20(new DateLatinConverver().toObject("20190306"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        dto.setField21(new SubField("aa", "bb"));

        String expected = "||   |   |||   |   |123456|123|123  |  456|||   |  2|1|false       |2.2            |            20190306|aa,bb";
        String actual = ex.toString(dto);

        assertEquals(expected, actual);
    }

    @Test
    public void testToStrings() {
        PipedParser ex = new PipedParser();

        PipedDataFSsDTO dto1 = new PipedDataFSsDTO();
        dto1.setField5("");
        dto1.setField6("");
        dto1.setField7("");
        dto1.setField8("");
        //
        dto1.setField9(123456);
        dto1.setField10(123456);
        dto1.setField11(123);
        dto1.setField12(456);
        //

        dto1.setField16(2);
        //
        dto1.setField17(1);
        dto1.setField18(false);
        dto1.setField19(2.2);

        PipedDataFSsDTO dto = new PipedDataFSsDTO();
        dto.setField5("");
        dto.setField6("");
        dto.setField7("");
        dto.setField8("");
        //
        dto.setField9(123456);
        dto.setField10(123456);
        dto.setField11(123);
        dto.setField12(456);
        //

        dto.setField16(2);
        //
        dto.setField17(1);
        dto.setField18(false);
        dto.setField19(2.2);

        try {
            dto.setField20(new DateLatinConverver().toObject("20190306"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<String> dtos = ex.toStrings(Arrays.asList(dto1, dto));

        String expected = "||   |   |||   |   |123456|123|123  |  456|||   |  2|1|false       |2.2            |            20190306|";

        String actual = null;
        for (String string : dtos) {
            actual = string;
        }

        assertEquals(expected, actual);
    }
}
