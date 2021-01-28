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
public class TestPipedFail {

    @Test
    public void testToObjects() {
        PipedParser ex = new PipedParser();

        List<String> rows = new ArrayList<String>();
        rows.add("a|1||true |1.1|20190306");

        String actual = null;
        try {
            ex.toObjects(rows, PipedDataDTO.class);
        } catch (Exception e) {
            actual = e.getMessage();
        }

        String expected = "An error has occurred while processing row: 1; Detail: An error has occurred while setting value, original value: ''; converter: ; class: com.ivoslabs.records.tests.piped.dtos.PipedDataDTO; field name: field3; field type: int;";

        System.out.println(expected);
        System.out.println(actual);

        Assertions.assertEquals(expected, actual);

    }

}
