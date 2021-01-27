/**
 *
 */
package com.ivoslabs.records.tests.copy;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.ivoslabs.records.parsers.CopyParser;
import com.ivoslabs.records.tests.commons.dtos.SubField;
import com.ivoslabs.records.tests.copy.dtos.CopyDataDTO;
import com.ivoslabs.records.tests.copy.dtos.CopyHeader;

/**
 * @since
 * @author www.ivoslabs.com
 *
 */
public class TestCopyFind {

    @Test
    public void test1() {

        String file = "target/data_2.copy";

        this.saveDataToTest(file);

        CopyParser copyParser = new CopyParser();

        CopyDataDTO dto = copyParser.findFirst(file, 2, CopyDataDTO.class, data -> data.getField1().equals("c"));

        Assertions.assertEquals(dto.getField1(), "c");

    }

    @Test
    public void test2() {

        String file = "target/data_2.copy";

        this.saveDataToTest(file);

        CopyParser copyParser = new CopyParser();

        List<CopyDataDTO> dtos = copyParser.find(file, 2, CopyDataDTO.class, data -> data.getField2().equals(2));

        String rs = dtos.stream().map(CopyDataDTO::getField1).collect(Collectors.joining(","));

        Assertions.assertEquals(rs, "b,c");

    }

    private void saveDataToTest(String file) {

        if (new File(file).exists()) {
            new File(file).delete();
        }

        // creating header objects
        CopyHeader header1 = new CopyHeader("headerA", 1, 12345);
        CopyHeader header2 = new CopyHeader("headerA", 1, 12345);

        // saving header objects into a list
        List<CopyHeader> headers = new ArrayList<CopyHeader>();
        headers.add(header1);
        headers.add(header2);

        // creating data objects
        CopyDataDTO dto1 = new CopyDataDTO("a", 1, 1, true, 1.1, new Date(), new SubField("sf1c1", "sf1c2"));
        CopyDataDTO dto2 = new CopyDataDTO("b", 2, 1, true, 1.1, new Date(), new SubField("sf2c1", "sf2c2"));
        CopyDataDTO dto3 = new CopyDataDTO("c", 2, 1, true, 1.1, new Date(), new SubField("sf3c1", "sf3c2"));
        CopyDataDTO dto4 = new CopyDataDTO("d", 4, 1, true, 1.1, new Date(), new SubField("sf4c1", "sf4c2"));

        // saving data objects into a list
        List<CopyDataDTO> list = new ArrayList<CopyDataDTO>();
        list.add(dto1);
        list.add(dto2);
        list.add(dto3);
        list.add(dto4);

        // save list objects into Stacks
        Stack<CopyHeader> headerStack = new Stack<CopyHeader>();
        headerStack.addAll(headers);

        Stack<CopyDataDTO> dataStack = new Stack<CopyDataDTO>();
        dataStack.addAll(list);

        // append objects into a file
        CopyParser copyParser = new CopyParser();
        copyParser.objectsToFileHD(file, headerStack, dataStack);

    }

}
