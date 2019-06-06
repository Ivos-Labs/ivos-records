/**
 * 
 */
package com.ivoslabs.records.tests.piped;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.ivoslabs.records.dtos.piped.PipedHeader;
import com.ivoslabs.records.dtos.piped.PipedDataDTO;
import com.ivoslabs.records.dtos.piped.PipedTail;
import com.ivoslabs.records.function.ObjectConsumer;
import com.ivoslabs.records.parsers.PipedParser;

/**
 * @author
 *
 */
public class TestPipedFileHTRead {

    @Test
    public void testFileToObjects() {

	// PipedHeader consumer (action to do for each PipedHeader)
	ObjectConsumer<PipedHeader> headerConsumer = new ObjectConsumer<PipedHeader>() {
	    
	    public void process(PipedHeader object) {
		System.out.println(object.toString());
	    }
	    
	};

	// PipedDataDTO consumer (action to do for each PipedDataDTO)
	ObjectConsumer<PipedDataDTO> dataConsumer = new ObjectConsumer<PipedDataDTO>() {
	    
	    public void process(PipedDataDTO object) {
		System.out.println(object.toString());
	    }
	    
	};

	// PipedTail consumer (action to do for each PipedTail)
	ObjectConsumer<PipedTail> tailConsumer = new ObjectConsumer<PipedTail>() {
	    public void process(PipedTail object) {
		System.out.println(object.toString());
	    }
	};

	PipedParser pipedParser = new PipedParser();

	int headerSize = 2;
	int tailSize = 1;

	String file = "datahdt.piped";
	
	// read file
	pipedParser.fileToObjects(file,
		PipedHeader.class, headerSize, headerConsumer,
		PipedDataDTO.class, dataConsumer,
		PipedTail.class, tailSize, tailConsumer);

	assertTrue(Boolean.TRUE);

    }

}
