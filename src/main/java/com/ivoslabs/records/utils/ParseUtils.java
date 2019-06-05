package com.ivoslabs.records.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ivoslabs.records.annontation.Converter;
import com.ivoslabs.records.annontation.Pic;
import com.ivoslabs.records.annontation.PipedField;
import com.ivoslabs.records.core.FieldParseDTO;
import com.ivoslabs.records.core.RowConsumer;
import com.ivoslabs.records.core.RowSuplier;
import com.ivoslabs.records.core.ClassParseDTO;
import com.ivoslabs.records.core.ClassParseDTO.Type;
import com.ivoslabs.records.exceptions.RecordParserException;

/**
 * 
 * @author www.ivoslabs.com
 *
 */
public class ParseUtils {

    /** The constant logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(ParseUtils.class);

    /** The constant true (1) used to parse a boolean without converter */
    private static final String TRUE_1 = "1";

    /** The constant true (Y) used to parse a boolean without converter */
    private static final String TRUE_Y = "Y";

    /** The constant true (T) used to parse a boolean without converter */
    private static final String TRUE_T = "T";

    /**
     * Gets the template
     * 
     * @param type    Class to use
     * @param annon   indicates the type of fields to read, Pic or PipedField
     * @param isToObj indicates if the tampleate will be used to parse String to Object
     * @return the Template generated
     */
    public static ClassParseDTO getTemplate(Class<?> type, Class<? extends Annotation> annon, boolean isToObj) {

	Type t = annon.equals(PipedField.class) ? Type.PIPE : Type.PIC;

	ClassParseDTO template = new ClassParseDTO(t);

	Field fields[] = type.getDeclaredFields();

	for (Field field : fields) {
	    FieldParseDTO ex = null;

	    if (annon.equals(PipedField.class)) {
		PipedField pf = field.getAnnotation(PipedField.class);
		if (pf != null) {
		    ex = new FieldParseDTO(field, pf, field.getAnnotation(Converter.class));
		}
	    } else {
		Pic pic = field.getAnnotation(Pic.class);
		if (pic != null) {
		    ex = new FieldParseDTO(field, pic, field.getAnnotation(Converter.class));
		}
	    }

	    if (ex != null) {
		template.add(ex);
	    }
	}

	if (template.getFieldParseDTOs().isEmpty()) {
	    throw new IllegalArgumentException("Class: " + type.getCanonicalName() + " doesn't have PipedField or Pic fields");
	}

	if (annon.equals(PipedField.class) && !isToObj) {
	    List<FieldParseDTO> extracts = template.getFieldParseDTOs();
	    Collections.sort(extracts, new Comparator<FieldParseDTO>() {
		public int compare(FieldParseDTO o1, FieldParseDTO o2) {
		    return Integer.compare(o1.getPipeField().value(), o2.getPipeField().value());
		}
	    });

	    for (FieldParseDTO extract : extracts) {
		template.addExtractMap(extract.getPipeField().value(), extract);
	    }

	    template.setLastIndex(extracts.get(extracts.size() - 1).getPipeField().value());

	} else if (!isToObj) {

	    List<FieldParseDTO> extracts = template.getFieldParseDTOs();

	    Collections.sort(extracts, new Comparator<FieldParseDTO>() {
		public int compare(FieldParseDTO o1, FieldParseDTO o2) {
		    return Integer.compare(o1.getPic().beginIndex(), o2.getPic().beginIndex());
		}
	    });

	    FieldParseDTO last = extracts.get(extracts.size() - 1);
	    template.setLastIndex(last.getPic().beginIndex() + last.getPic().size() - 1);

	}

	return template;

    }

    /**
     * Parse a string value to a type
     * 
     * @param value
     * @param type
     * @return
     */
    public static Object parse(String value, Class<?> type) {
	Object v = null;

	if (type != String.class) {
	    value = value.trim();
	}

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
     * 
     * @param url
     * @param action
     */
    public static void readTextFile(String url, RowConsumer action) throws RecordParserException {
	BufferedReader br = null;

	try {

	    br = new BufferedReader(new FileReader(url));
	    String sCurrentLine;

	    while ((sCurrentLine = br.readLine()) != null) {
		action.process(sCurrentLine);
	    }

	} catch (RecordParserException e) {
	    throw e;
	} catch (Exception e) {
	    throw new RuntimeException(e);
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

    /** */
    private static final char BREAK_LINE = '\n';

    /**
     * 
     * @param filename
     * @return
     * @throws IOException
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

    /**
     * Checks that the specified object reference is not {@code null} and throws a customized {@link NullPointerException} if it is. This method is designed primarily for doing parameter validation in methods and constructors with multiple parameters, as demonstrated below: <blockquote>
     * 
     * <pre>
     * public Foo(Bar bar, Baz baz) {
     *     ParseUtils.notNull(bar, "bar must not be null");
     * }
     * </pre>
     * 
     * </blockquote>
     *
     * @param obj     the object reference to check for nullity
     * @param message detail message to be used in the event that a {@code
     *                NullPointerException} is thrown
     * @param         <T> the type of the reference
     * @throws NullPointerException if {@code obj} is {@code null}
     */
    public static void notNull(Object obj, String message) {
	if (obj == null) {
	    throw new NullPointerException(message);
	}
    }

    /**
     * <p>
     * Validate that the argument condition is {@code false}; otherwise throwing an exception.<br>
     * This method is useful when validating according to an arbitrary boolean expression, such as validating a primitive number or using your own custom validation expression.
     * </p>
     *
     * <pre>
     * ParseUtils.notTrue(myObject.isOk(), "Message");
     * </pre>
     *
     *
     * @param expression the boolean expression to check
     * @param message    the message
     * @throws IllegalArgumentException if expression is {@code false}
     */
    public static void notTrue(boolean cond, String message) {
	if (!cond) {
	    throw new IllegalArgumentException(message);
	}
    }

    /**
     * <p>
     * Validate that the argument condition is {@code true}; otherwise throwing an exception.<br>
     * This method is useful when validating according to an arbitrary boolean expression, such as validating a primitive number or using your own custom validation expression.
     * </p>
     *
     * <pre>
     * ParseUtils.isTrue(myObject.isOk(), "Message");
     * </pre>
     *
     * 
     * @param expression the boolean expression to check
     * @param message    the message
     * @throws IllegalArgumentException if expression is {@code false}
     */
    public static void isTrue(boolean expression, String message) {
	if (expression) {
	    throw new IllegalArgumentException(message);
	}
    }
}
