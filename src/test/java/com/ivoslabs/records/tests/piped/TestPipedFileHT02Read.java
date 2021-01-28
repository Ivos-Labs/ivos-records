/**
 *
 */
package com.ivoslabs.records.tests.piped;

import java.util.function.Consumer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.ivoslabs.records.core.ParserTask;
import com.ivoslabs.records.parsers.PipedParser;
import com.ivoslabs.records.tests.piped.dtos.PipedDataDTO;
import com.ivoslabs.records.tests.piped.dtos.PipedHeader;
import com.ivoslabs.records.tests.piped.dtos.PipedTail;

/**
 * @author
 *
 */
public class TestPipedFileHT02Read {

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

        ParserTask<PipedHeader, PipedDataDTO, PipedTail> parserTask = new ParserTask<>(file, PipedDataDTO.class, dataConsumer);
        parserTask.setHeaderInfo(PipedHeader.class, headerSize, headerConsumer);
        parserTask.setTailInfo(PipedTail.class, tailSize, tailConsumer);

        // read file
        pipedParser.processFile(parserTask);

        Assertions.assertTrue(Boolean.TRUE);

    }

}
