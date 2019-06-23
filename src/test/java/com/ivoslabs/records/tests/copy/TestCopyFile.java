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

import com.ivoslabs.records.function.Consumer;
import com.ivoslabs.records.parsers.CopyParser;
import com.ivoslabs.records.tests.copy.dtos.CopyDataDTO;

/**
 * @author www.ivoslabs.com
 *
 */
public class TestCopyFile {

    @Test
    public void testFileToObjects() {

	CopyParser ex = new CopyParser();
	ex.processFile("data.copy", CopyDataDTO.class, new Consumer<CopyDataDTO>() {

	    public void process(CopyDataDTO object) {
		System.out.println(object.toString());
	    }

	});

	assertTrue(Boolean.TRUE);

    }

    @Test
    public void testObjectsToFile() {
	CopyParser ex = new CopyParser();

	CopyDataDTO dto1 = new CopyDataDTO();
	dto1.setField("a");
	dto1.setField2(1);
	dto1.setField3(1);
	dto1.setField4(false);
	dto1.setField5(1.1);
	dto1.setField6(new Date());

	CopyDataDTO dto2 = new CopyDataDTO();
	dto2.setField("b");
	dto2.setField2(2);
	dto2.setField3(2);
	dto2.setField4(false);
	dto2.setField5(2.2);
	dto2.setField6(new Date());

	CopyDataDTO dto3 = new CopyDataDTO();
	dto3.setField("c");
	dto3.setField2(3);
	dto3.setField3(3);
	dto3.setField4(false);
	dto3.setField5(3.3);
	dto3.setField6(new Date());

	List<CopyDataDTO> list = new ArrayList<CopyDataDTO>();
	list.add(dto1);
	list.add(dto2);
	list.add(dto3);

	Stack<CopyDataDTO> stack = new Stack<CopyDataDTO>();
	stack.addAll(list);

	ex.objectsToFile("data.copy", stack);

	assertTrue(Boolean.TRUE);

    }
}
