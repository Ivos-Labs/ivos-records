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

import com.ivoslabs.records.core.ObjectConsumer;
import com.ivoslabs.records.dtos.piped.PipedOkDTO;
import com.ivoslabs.records.parsers.CopyParser;

/**
 * @author www.ivoslabs.com
 *
 */
public class TestCopyFile {

    @Test
    public void testFileToObjects() {

	CopyParser ex = new CopyParser();
	ex.fileToObjects("data.copy", PipedOkDTO.class, new ObjectConsumer<PipedOkDTO>() {

	    public void process(PipedOkDTO object) {
		System.out.println(object.toString());
	    }

	});

	assertTrue(Boolean.TRUE);

    }

    @Test
    public void testObjectsToFile() {
	CopyParser ex = new CopyParser();

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

	/**
	 * a b c
	 * 
	 */

	Stack<PipedOkDTO> stack = new Stack<PipedOkDTO>();
	stack.addAll(list);

	ex.objectsToFile("data.copy", stack);

	assertTrue(Boolean.TRUE);

    }
}
