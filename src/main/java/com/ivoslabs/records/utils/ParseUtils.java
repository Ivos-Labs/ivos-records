package com.ivoslabs.records.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Stack;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ivoslabs.records.core.func.RowConsumer;
import com.ivoslabs.records.core.func.RowSuplier;
import com.ivoslabs.records.exceptions.RecordParserException;

/**
 * 
 * @author www.ivoslabs.com
 *
 */
public class ParseUtils {

    /** The constant logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(ParseUtils.class);

    /** */
    public static final String PIPE = "|";

    /** */
    public static final String EMPTY = "";

    /** */
    public static final int ZERO = 0;

    /** */
    public static final String SPACE = " ";

    /** The constant pipe separator */
    public static final String PIPE_SEPARATOR = "\\|";

    /** The constant true (1) used to parse a boolean without converter */
    private static final String TRUE_1 = "1";

    /** The constant true (Y) used to parse a boolean without converter */
    private static final String TRUE_Y = "Y";

    /** The constant true (T) used to parse a boolean without converter */
    private static final String TRUE_T = "T";

    /** The constant \n */
    private static final char BREAK_LINE = '\n';

    /**
     * Parse a string value to a Object
     * 
     * @param value a string representation of a Object
     * @param type  required type
     * @return the object represented by the received String
     */
    public static Object parse(String value, Class<?> type) {

	Object v = null;

	value = StringUtils.trimToEmpty(value);

	if (type == long.class) {
	    v = Long.parseLong(value);
	} else if (type == int.class) {
	    v = Integer.parseInt(value);
	} else if (type == short.class) {
	    v = Short.parseShort(value);
	} else if (type == double.class) {
	    v = Double.parseDouble(value);
	} else if (type == float.class) {
	    v = Float.parseFloat(value);
	} else if (type == boolean.class) {
	    v = value.equals(TRUE_1) || value.equals(TRUE_T) || value.equals(TRUE_Y);
	    if (v.equals(Boolean.FALSE)) {
		v = Boolean.parseBoolean(value);
	    }
	} else if (type.equals(Long.class)) {
	    if (!value.isEmpty()) {
		v = Long.parseLong(value);
	    }
	} else if (type.equals(Integer.class)) {
	    if (!value.isEmpty()) {
		v = Integer.parseInt(value);
	    }
	} else if (type.equals(Short.class)) {
	    if (!value.isEmpty()) {
		v = Short.parseShort(value);
	    }
	} else if (type.equals(Double.class)) {
	    if (!value.isEmpty()) {
		v = Double.parseDouble(value);
	    }
	} else if (type.equals(Float.class)) {
	    if (!value.isEmpty()) {
		v = Float.parseFloat(value);
	    }
	} else if (type.equals(BigDecimal.class)) {
	    if (!value.isEmpty()) {
		v = new BigDecimal(value);
	    }
	} else if (type.equals(Boolean.class)) {
	    if (!value.isEmpty()) {
		v = value.equals(TRUE_1) || value.equals(TRUE_T) || value.equals(TRUE_Y);
		if (v.equals(Boolean.FALSE)) {
		    v = Boolean.parseBoolean(value);
		}
	    }
	} else {
	    v = value;
	}

	return v;
    }

    /**
     * Read a file and for each row execute the received RowConsumer
     * 
     * @param url    path file to be read
     * @param action action to do for each row
     * @throws RecordParserException when occur an Exception processing a row
     */
    public static void readTextFile(String url, RowConsumer action) throws RecordParserException {
	BufferedReader br = null;
	int rowNumber = 0;
	try {

	    br = new BufferedReader(new FileReader(url));
	    String sCurrentLine;

	    while ((sCurrentLine = br.readLine()) != null) {
		rowNumber++;
		action.process(sCurrentLine, rowNumber);
	    }

	} catch (RecordParserException e) {
	    throw e;
	} catch (Exception e) {
	    if (rowNumber > 0) {
		throw new RecordParserException("An error has occurred while processing file: " + url + "; row: " + rowNumber + "; Detail: " + e.getMessage(), e);
	    } else {
		throw new RuntimeException(e);
	    }
	} finally {
	    if (br != null) {
		try {
		    br.close();
		} catch (Exception e2) {
		    LOGGER.error(e2.getMessage(), e2);
		}
	    }
	}

    }

    /**
     * 
     * @param path
     * @param headers
     * @param rowHeaderSuplier
     * @param data
     * @param rowDataSuplier
     * @param tails
     * @param rowTailSuplier
     */
    public static <H, D, T> void writeFile(String path, Stack<H> headers, RowSuplier<H> rowHeaderSuplier, Stack<D> data, RowSuplier<D> rowDataSuplier, Stack<T> tails, RowSuplier<T> rowTailSuplier) {

	BufferedWriter out = null;

	try {

	    LOGGER.debug("writing file: {}", path);

	    out = new BufferedWriter(new FileWriter(path, Boolean.TRUE));

	    while (headers != null && !headers.empty()) {
		out.write(rowHeaderSuplier.get(headers.firstElement()) + "\n");
		headers.remove(0);
	    }

	    while (!data.empty()) {
		out.write(rowDataSuplier.get(data.firstElement()) + "\n");
		data.remove(0);
	    }

	    while (tails != null && !tails.empty()) {
		out.write(rowTailSuplier.get(tails.firstElement()) + "\n");
		tails.remove(0);
	    }

	    LOGGER.debug("writed: {}", path);

	} catch (IOException e) {
	    throw new RuntimeException(e);
	} finally {
	    if (out != null) {
		try {
		    out.close();
		} catch (Exception e2) {
		    LOGGER.error(e2.getMessage(), e2);
		}
	    }
	}
    }

    /**
     * Counts the number of lines in a file
     * 
     * @param filename path file
     * @return the number of lines
     */
    public static int countLinesNew(String filename) {
	InputStream is = null;
	try {
	    is = new BufferedInputStream(new FileInputStream(filename));
	    byte[] c = new byte[1024];

	    int readChars = is.read(c);
	    if (readChars == -1) {
		// bail out if nothing to read
		return 0;
	    }

	    // make it easy for the optimizer to tune this loop
	    int count = 0;
	    while (readChars == 1024) {
		for (int i = 0; i < 1024;) {
		    if (c[i++] == BREAK_LINE) {
			++count;
		    }
		}
		readChars = is.read(c);
	    }

	    // count remaining characters
	    while (readChars != -1) {
//		System.out.println(readChars);
		for (int i = 0; i < readChars; ++i) {
		    if (c[i] == BREAK_LINE) {
			++count;
		    }
		}
		readChars = is.read(c);
	    }

	    return count == 0 ? 1 : count;
	} catch (Exception e) {
	    throw new RuntimeException(e);
	} finally {
	    if (is != null) {
		try {
		    is.close();
		} catch (Exception e2) {
		    LOGGER.error(e2.getMessage(), e2);
		}
	    }
	}
    }

}
