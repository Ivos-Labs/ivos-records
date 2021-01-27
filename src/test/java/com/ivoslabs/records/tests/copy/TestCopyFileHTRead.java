/**
 *
 */
package com.ivoslabs.records.tests.copy;

import java.util.function.Consumer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.ivoslabs.records.parsers.CopyParser;
import com.ivoslabs.records.tests.copy.dtos.CopyDataDTO;
import com.ivoslabs.records.tests.copy.dtos.CopyHeader;

/**
 * @author
 *
 */
public class TestCopyFileHTRead {

    @Test
    public void testFileToObjects() {

        // CopyHeader consumer (action to do for each CopyHeader)
        Consumer<CopyHeader> headerConsumer = object -> System.out.println("header: " + object.toString());

        // CopyDataDTO consumer (action to do for each CopyDataDTO)
        Consumer<CopyDataDTO> dataConsumer = object -> System.out.println("data:   " + object.toString());

        CopyParser copyParser = new CopyParser();

        int headerSize = 3;

        String file = "target/data.copy";

        copyParser.processFile(file, CopyHeader.class, headerSize, headerConsumer, CopyDataDTO.class, dataConsumer);

        Assertions.assertTrue(Boolean.TRUE);
    }
}
