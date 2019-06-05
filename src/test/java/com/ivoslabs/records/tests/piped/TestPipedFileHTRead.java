/**
 * 
 */
package com.ivoslabs.records.tests.piped;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.ivoslabs.records.dtos.piped.PipedHeader;
import com.ivoslabs.records.dtos.piped.PipedOkDTO;
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

	ObjectConsumer<PipedHeader> headerCons = new ObjectConsumer<PipedHeader>() {

	    public void process(PipedHeader object) {
		System.out.println(object.toString());
	    }
	};

	ObjectConsumer<PipedOkDTO> dataCons = new ObjectConsumer<PipedOkDTO>() {

	    public void process(PipedOkDTO object) {
		System.out.println(object.toString());
	    }
	};

	ObjectConsumer<PipedTail> tailCons = new ObjectConsumer<PipedTail>() {

	    public void process(PipedTail object) {
		System.out.println(object.toString());
	    }
	};

	PipedParser ex = new PipedParser();

	int headerSize = 2;
	int tailSize = 1;

	ex.fileToObjects("pipedht.piped", PipedHeader.class, headerSize, headerCons, PipedOkDTO.class, dataCons, PipedTail.class, tailSize, tailCons);

	assertTrue(Boolean.TRUE);

    }

}
