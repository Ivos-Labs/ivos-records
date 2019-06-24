/**
 * 
 */
package com.ivoslabs.records.tests.piped;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;

import org.junit.Test;

import com.ivoslabs.records.parsers.PipedParser;
import com.ivoslabs.records.tests.commons.dtos.SubField;
import com.ivoslabs.records.tests.piped.dtos.PipedDataDTO;
import com.ivoslabs.records.tests.piped.dtos.PipedHeader;
import com.ivoslabs.records.tests.piped.dtos.PipedTail;

/**
 * @author
 *
 */
public class TestPipedFileHTSave {

    @Test
    public void testObjectsToFile() {

	// creating header objects
	PipedHeader header1 = new PipedHeader();
	header1.setField1("headerA");
	header1.setField2(1);

	PipedHeader header2 = new PipedHeader();
	header2.setField1("headerB");
	header2.setField2(2);

	// saving header objects into a list
	List<PipedHeader> headers = new ArrayList<PipedHeader>();
	headers.add(header1);
	headers.add(header2);

	// creating data objects
	PipedDataDTO dto1 = new PipedDataDTO();
	dto1.setField1("a");
	dto1.setField2(null);
	dto1.setField3(1);
	dto1.setField4(true);
	dto1.setField5(1.1);
	dto1.setField6(new Date());

	PipedDataDTO dto2 = new PipedDataDTO();
	dto2.setField1("b");
	dto2.setField2(2);
	dto2.setField3(22);
	dto2.setField4(false);
	dto2.setField5(2.2);
	dto2.setField6(new Date());
	dto2.setField7(new SubField("QW", "WE"));

	PipedDataDTO dto3 = new PipedDataDTO();
	dto3.setField1("c");
	dto3.setField2(3);
	dto3.setField3(33);
	dto3.setField4(false);
	dto3.setField5(3.3);
	dto3.setField6(new Date());

	// saving data objects into a list
	List<PipedDataDTO> list = new ArrayList<PipedDataDTO>();
	list.add(dto1);
	list.add(dto2);
	list.add(dto3);

	// creating tail objects
	PipedTail tail = new PipedTail();
	tail.setField1(1);
	tail.setField2("tailA");

	// saving tail objects into a list
	List<PipedTail> tails = new ArrayList<PipedTail>();
	tails.add(tail);

	// save list objects into Stacks
	Stack<PipedHeader> headerStack = new Stack<PipedHeader>();
	headerStack.addAll(headers);

	Stack<PipedDataDTO> dataStack = new Stack<PipedDataDTO>();
	dataStack.addAll(list);

	Stack<PipedTail> tailStack = new Stack<PipedTail>();
	tailStack.addAll(tails);

	String file = "datahdt.psv";

	// append objects into a file
	PipedParser pipedParser = new PipedParser();
	pipedParser.objectsToFile(file, headerStack, dataStack, tailStack);

	assertTrue(Boolean.TRUE);

    }
}
