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

import com.ivoslabs.records.function.Consumer;
import com.ivoslabs.records.parsers.PipedParser;
import com.ivoslabs.records.tests.piped.dtos.PipedDataDTO;

/**
 * @author www.ivoslabs.com
 *
 */
public class TestPipedFile {

    @Test
    public void testFileToObjects() {
	
	PipedParser ex = new PipedParser();
	ex.processFile("pipeds.piped", PipedDataDTO.class, new Consumer<PipedDataDTO>() {

	    public void process(PipedDataDTO object) {
		System.out.println(object.toString());
	    }
	    
	});

	assertTrue(Boolean.TRUE);

    }

    @Test
    public void testObjectsToFile() {
	PipedParser ex = new PipedParser();

	PipedDataDTO dto1 = new PipedDataDTO();
	dto1.setField1("a");
	dto1.setField2(1);
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

	PipedDataDTO dto3 = new PipedDataDTO();
	dto3.setField1("c");
	dto3.setField2(3);
	dto3.setField3(33);
	dto3.setField4(false);
	dto3.setField5(3.3);
	dto3.setField6(new Date());

	List<PipedDataDTO> list = new ArrayList<PipedDataDTO>();
	list.add(dto1);
	list.add(dto2);
	list.add(dto3);

	/**
	 * a
	 * b
	 * c
	 * 
	 */
	
	Stack<PipedDataDTO> stack = new Stack<PipedDataDTO>();
	stack.addAll(list);

	ex.objectsToFile("pipeds.piped", stack);

	assertTrue(Boolean.TRUE);

    }
}
