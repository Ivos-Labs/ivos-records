package com.ivoslabs.records.piped;
/**
 * 
 */


import java.util.ArrayList;
import java.util.List;

import com.ivoslabs.records.extractors.CopyExtractor;
import com.ivoslabs.records.extractors.PipedExtactor;
import com.ivoslabs.records.parsers.PipedParser;

/**
 * @author www.ivoslabs.com
 *
 */
public class TestPipe {

    public static void main(String[] args) {
	CopyExtractor cex = new CopyExtractor();
	List<String> crows = new ArrayList<String>();
	crows.add("a111.1");
	crows.add("b202.2");
	crows.add("c313.3");
	crows.add("d404.4");

	List<DataDTO2> cdtos = cex.convert(crows, DataDTO2.class);

	for (DataDTO2 dataDTO : cdtos) {
	    System.out.println(dataDTO);
	}

	PipedParser ex = new PipedParser();

	List<String> rows = new ArrayList<String>();
	rows.add("a|1|true|1.1");
	rows.add("b|2|false|2.2");
	rows.add("c|3|1|3.3");
	rows.add("d||0|4.4");

	List<PipedDTO> dtos = ex.toObjects(rows, PipedDTO.class);

	for (PipedDTO dataDTO : dtos) {
	    System.out.println(dataDTO);
	}
	
	List<String> strs = ex.toStrings(dtos);
	
	for (String s : strs) {
	    System.out.println(s);
	}

    }
}
