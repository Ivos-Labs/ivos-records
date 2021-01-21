/**
 *
 */
package com.ivoslabs.records.tests.piped;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.ivoslabs.records.function.Consumer;
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
        Consumer<PipedHeader> headerConsumer = new Consumer<PipedHeader>() {

            public void process(PipedHeader object) {
                System.out.println(object.toString());
            }

        };

        // PipedDataDTO consumer (action to do for each PipedDataDTO)
        Consumer<PipedDataDTO> dataConsumer = new Consumer<PipedDataDTO>() {

            public void process(PipedDataDTO object) {
                System.out.println(object.toString());
            }

        };

        // PipedTail consumer (action to do for each PipedTail)
        Consumer<PipedTail> tailConsumer = new Consumer<PipedTail>() {
            public void process(PipedTail object) {
                System.out.println(object.toString());
            }
        };

        PipedParser pipedParser = new PipedParser();

        int headerSize = 2;
        int tailSize = 1;

        String file = "datahdt.psv";

        // read file
        pipedParser.processFile(file,
                PipedHeader.class, headerSize, headerConsumer,
                PipedDataDTO.class, dataConsumer,
                PipedTail.class, tailSize, tailConsumer);

        assertTrue(Boolean.TRUE);

    }

}
