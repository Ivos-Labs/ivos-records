/**
 * 
 */
package com.ivoslabs.records.tests.copy;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;

import org.junit.Test;

import com.ivoslabs.records.parsers.CopyParser;
import com.ivoslabs.records.tests.commons.dtos.SubField;
import com.ivoslabs.records.tests.copy.dtos.CopyDataDTO;
import com.ivoslabs.records.tests.copy.dtos.CopyHeader;

/**
 * @author
 *
 */
public class TestCopyFileHTSave {

    @Test
    public void testObjectsToFile() {

	// creating header objects
	CopyHeader header1 = new CopyHeader();
	header1.setField1("headerA");
	header1.setField2(1);
	header1.setField3(12345);

	CopyHeader header2 = new CopyHeader();
	header2.setField1("headerB");
	header2.setField2(2);

	CopyHeader header3 = new CopyHeader();
	header3.setField1("headerC");
	header3.setField2(3);
	header3.setField3(123456);

	// saving header objects into a list
	List<CopyHeader> headers = new ArrayList<CopyHeader>();
	headers.add(header1);
	headers.add(header2);
	headers.add(header3);

	// creating data objects
	CopyDataDTO dto1 = new CopyDataDTO();
	dto1.setField1("");
	dto1.setField2(1);
	dto1.setField3(1);
	dto1.setField4(true);
	dto1.setField5(1.1);
	dto1.setField6(new Date());

	CopyDataDTO dto2 = new CopyDataDTO();
	dto2.setField1("b");
	dto2.setField2(2);
	dto2.setField3(22);
	dto2.setField4(false);
	dto2.setField5(2.2);
	dto2.setField6(new Date());
	dto2.setField7(new SubField("qw", "er"));

	CopyDataDTO dto3 = new CopyDataDTO();
	dto3.setField1("c");
	dto3.setField2(3);
	dto3.setField3(33);
	dto3.setField4(false);
	dto3.setField5(3.3);
	dto3.setField6(new Date());

	// saving data objects into a list
	List<CopyDataDTO> list = new ArrayList<CopyDataDTO>();
	list.add(dto1);
	list.add(dto2);
	list.add(dto3);


	// save list objects into Stacks
	Stack<CopyHeader> headerStack = new Stack<CopyHeader>();
	headerStack.addAll(headers);

	Stack<CopyDataDTO> dataStack = new Stack<CopyDataDTO>();
	dataStack.addAll(list);

	String file = "data.copy";
	
	// append objects into a file
	CopyParser copyParser = new CopyParser();
	copyParser.objectsToFileHD(file, headerStack, dataStack);

	assertTrue(Boolean.TRUE);
    }
}
