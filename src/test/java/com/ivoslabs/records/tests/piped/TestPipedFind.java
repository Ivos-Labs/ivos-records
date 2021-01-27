/**
 *
 */
package com.ivoslabs.records.tests.piped;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.ivoslabs.records.parsers.PipedParser;
import com.ivoslabs.records.tests.commons.dtos.SubField;
import com.ivoslabs.records.tests.piped.dtos.PipedDataDTO;
import com.ivoslabs.records.tests.piped.dtos.PipedHeader;

/**
 * @since
 * @author www.ivoslabs.com
 *
 */
public class TestPipedFind {

    @Test
    public void testFind1() {

        String file = "target/datahdt_2.psv";

        // save data to test
        this.saveData(file);

        PipedParser pipedParser = new PipedParser();
        PipedDataDTO dto = pipedParser.findFirst(file, 2, PipedDataDTO.class, d -> d.getField1().equals("c"));

        Assertions.assertEquals(dto.getField1(), "c");
    }

    @Test
    public void testFind2() {

        String file = "target/datahdt_2.psv";

        // save data to test
        this.saveData(file);

        PipedParser pipedParser = new PipedParser();
        List<PipedDataDTO> dtos = pipedParser.find(file, 2, PipedDataDTO.class, d -> d.getField3() == 2);

        String rs = dtos.stream().map(PipedDataDTO::getField1).collect(Collectors.joining(","));

        Assertions.assertEquals(rs, "b,c");
    }

    private void saveData(String file) {

        if (new File(file).exists()) {
            new File(file).delete();
        }

        // creating header objects
        PipedHeader header1 = new PipedHeader("headerA", 1);
        PipedHeader header2 = new PipedHeader("headerB", 2);

        // saving header objects into a list
        List<PipedHeader> headers = new ArrayList<>();
        headers.add(header1);
        headers.add(header2);

        // creating data objects
        PipedDataDTO dto1 = new PipedDataDTO("a", null, 1, true, 1.1, new Date(), new SubField("sf1c1", "sf1c2"));
        PipedDataDTO dto2 = new PipedDataDTO("b", null, 2, true, 1.1, new Date(), new SubField("sf2c1", "sf2c2"));
        PipedDataDTO dto3 = new PipedDataDTO("c", null, 2, true, 1.1, new Date(), new SubField("sf3c1", "sf3c2"));
        PipedDataDTO dto4 = new PipedDataDTO("d", null, 1, true, 1.1, new Date(), new SubField("sf4c1", "sf4c2"));

        // saving data objects into a list
        List<PipedDataDTO> list = new ArrayList<PipedDataDTO>();
        list.add(dto1);
        list.add(dto2);
        list.add(dto3);
        list.add(dto4);

        // save list objects into Stacks
        Stack<PipedHeader> headerStack = new Stack<PipedHeader>();
        headerStack.addAll(headers);

        Stack<PipedDataDTO> dataStack = new Stack<PipedDataDTO>();
        dataStack.addAll(list);

        // append objects into a file
        PipedParser pipedParser = new PipedParser();
        pipedParser.objectsToFileHD(file, headerStack, dataStack);
    }
}
