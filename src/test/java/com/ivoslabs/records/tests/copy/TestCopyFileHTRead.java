/**
 * 
 */
package com.ivoslabs.records.tests.copy;

import org.junit.Test;

import com.ivoslabs.records.dtos.copy.CopyDataDTO;
import com.ivoslabs.records.dtos.copy.CopyHeader;
import com.ivoslabs.records.function.Consumer;
import com.ivoslabs.records.parsers.CopyParser;

/**
 * @author
 *
 */
public class TestCopyFileHTRead {

    @Test
    public void testFileToObjects() {
	// CopyHeader consumer (action to do for each CopyHeader)
	Consumer<CopyHeader> headerConsumer = new Consumer<CopyHeader>() {

	    public void process(CopyHeader object) {
		System.out.println(object.toString());
	    }

	};

	// CopyDataDTO consumer (action to do for each CopyDataDTO)
	Consumer<CopyDataDTO> dataConsumer = new Consumer<CopyDataDTO>() {

	    public void process(CopyDataDTO object) {
		System.out.println(object.toString());
	    }
	};

	CopyParser copyParser = new CopyParser();

	int headerSize = 3;

	String file = "data.copy";

	copyParser.processFile(file,
		CopyHeader.class, headerSize, headerConsumer,
		CopyDataDTO.class, dataConsumer);
    }
}
