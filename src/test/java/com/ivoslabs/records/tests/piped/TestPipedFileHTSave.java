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

import com.ivoslabs.records.dtos.piped.PipedHeader;
import com.ivoslabs.records.dtos.piped.PipedOkDTO;
import com.ivoslabs.records.dtos.piped.PipedTail;
import com.ivoslabs.records.parsers.PipedParser;

/**
 * @author
 *
 */
public class TestPipedFileHTSave {

    @Test
    public void testObjectsToFile() {

	// header

	PipedHeader header1 = new PipedHeader();
	header1.setField1("headerA");
	header1.setField2(1);

	PipedHeader header2 = new PipedHeader();
	header2.setField1("headerB");
	header2.setField2(2);

	List<PipedHeader> headers = new ArrayList<PipedHeader>();
	headers.add(header1);
	headers.add(header2);

	PipedOkDTO dto1 = new PipedOkDTO();
	dto1.setField1("a");
	dto1.setField2(1);
	dto1.setField3(1);
	dto1.setField4(true);
	dto1.setField5(1.1);
	dto1.setField6(new Date());

	PipedOkDTO dto2 = new PipedOkDTO();
	dto2.setField1("b");
	dto2.setField2(2);
	dto2.setField3(22);
	dto2.setField4(false);
	dto2.setField5(2.2);
	dto2.setField6(new Date());

	PipedOkDTO dto3 = new PipedOkDTO();
	dto3.setField1("c");
	dto3.setField2(3);
	dto3.setField3(33);
	dto3.setField4(false);
	dto3.setField5(3.3);
	dto3.setField6(new Date());

	List<PipedOkDTO> list = new ArrayList<PipedOkDTO>();
	list.add(dto1);
	list.add(dto2);
	list.add(dto3);

	// tail

	PipedTail tail = new PipedTail();
	tail.setField1(1);
	tail.setField2("tailA");

	List<PipedTail> tails = new ArrayList<PipedTail>();
	tails.add(tail);

	// save objest in a Stacks

	Stack<PipedHeader> headerStack = new Stack<PipedHeader>();
	headerStack.addAll(headers);

	Stack<PipedOkDTO> stack = new Stack<PipedOkDTO>();
	stack.addAll(list);

	Stack<PipedTail> tailStack = new Stack<PipedTail>();
	tailStack.addAll(tails);

	PipedParser ex = new PipedParser();
	ex.objectsToFile("datahdt.piped", headerStack, stack, tailStack);

	assertTrue(Boolean.TRUE);

    }
}
