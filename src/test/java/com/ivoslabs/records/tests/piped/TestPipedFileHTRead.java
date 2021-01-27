/**
 *
 */
package com.ivoslabs.records.tests.piped;

import java.util.function.Consumer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.ivoslabs.records.parsers.PipedParser;
import com.ivoslabs.records.tests.piped.dtos.PipedDataDTO;
import com.ivoslabs.records.tests.piped.dtos.PipedHeader;
import com.ivoslabs.records.tests.piped.dtos.PipedTail;

/**
 * @author
 *
 */
public class TestPipedFileHTRead {

    @Test
    public void testFileToObjects() {

        // PipedHeader consumer (action to do for each PipedHeader)
        Consumer<PipedHeader> headerConsumer = object -> System.out.println("header: " + object.toString());

        // PipedDataDTO consumer (action to do for each PipedDataDTO)
        Consumer<PipedDataDTO> dataConsumer = object -> System.out.println("data: " + object.toString());

        // PipedTail consumer (action to do for each PipedTail)
        Consumer<PipedTail> tailConsumer = object -> System.out.println("tail" + object.toString());

        PipedParser pipedParser = new PipedParser();

        int headerSize = 2;
        int tailSize = 1;

        String file = "target/datahdt.psv";

        // read file
        pipedParser.processFile(file,
                PipedHeader.class, headerSize, headerConsumer,
                PipedDataDTO.class, dataConsumer,
                PipedTail.class, tailSize, tailConsumer);

        Assertions.assertTrue(Boolean.TRUE);

    }

}
