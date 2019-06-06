/**
 * 
 */
package com.ivoslabs.records.tests.copy;

import org.junit.Test;

import com.ivoslabs.records.dtos.copy.CopyDataDTO;
import com.ivoslabs.records.dtos.copy.CopyHeader;
import com.ivoslabs.records.function.ObjectConsumer;
import com.ivoslabs.records.parsers.CopyParser;

/**
 * @author
 *
 */
public class TestCopyFileHTRead {

    @Test
    public void testFileToObjects() {
	// CopyHeader consumer (action to do for each CopyHeader)
	ObjectConsumer<CopyHeader> headerConsumer = new ObjectConsumer<CopyHeader>() {

	    public void process(CopyHeader object) {
		System.out.println(object.toString());
	    }

	};

	// CopyDataDTO consumer (action to do for each CopyDataDTO)
	ObjectConsumer<CopyDataDTO> dataConsumer = new ObjectConsumer<CopyDataDTO>() {

	    public void process(CopyDataDTO object) {
		System.out.println(object.toString());
	    }
	};

	CopyParser copyParser = new CopyParser();

	int headerSize = 3;

	String file = "data.copy";

	copyParser.fileToObjects(file,
		CopyHeader.class, headerSize, headerConsumer,
		CopyDataDTO.class, dataConsumer);
    }
}
