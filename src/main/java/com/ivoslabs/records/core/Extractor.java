/**
 * 
 */
package com.ivoslabs.records.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.ivoslabs.records.annontation.Converter;
import com.ivoslabs.records.annontation.Pic;
import com.ivoslabs.records.annontation.PipedField;
import com.ivoslabs.records.converters.FieldConverter;
import com.ivoslabs.records.exceptions.ParseException;
import com.ivoslabs.records.exceptions.RecordParserException;
import com.ivoslabs.records.function.ObjectConsumer;
import com.ivoslabs.records.utils.MutableCounter;
import com.ivoslabs.records.utils.ParseUtils;

/**
 * @author www.ivoslabs.com
 *
 */
public class Extractor {

    /** */
    private static final String EMPTY = "";

    /** */
    private static final String SPACE = " ";

    /** The constant pipe separator */
    private static final String PIPE_SEPARATOR = "\\|";

    /**
     * 
     * @param data
     * @param annon
     * @return
     */
    public static String convertObjectToString(Object data, Class<? extends Annotation> annon) {
	ParseUtils.notNull(data, "data must not be null");
	String obj;
	ClassParseDTO template = ParseUtils.getTemplate(data.getClass(), annon, Boolean.FALSE);
	if (annon.equals(PipedField.class)) {
	    obj = Extractor.convertPipedObjectToString(data, template);
	} else {
	    obj = Extractor.convertCopyObjectToString(data, template);
	}

	return obj;
    }

    /**
     * 
     * @param data
     * @param annon
     * @return
     */
    public static List<String> convertObjectsToStrings(List<?> data, Class<? extends Annotation> annon) {
	ParseUtils.notNull(data, "data must not be null");
	ParseUtils.isTrue(data.isEmpty(), "data must not be empty");

	List<String> list = new ArrayList<String>();

	if (!data.isEmpty()) {
	    ClassParseDTO template = ParseUtils.getTemplate(data.get(0).getClass(), annon, Boolean.FALSE);

	    if (annon.equals(PipedField.class)) {
		for (Object row : data) {
		    String object;
		    object = Extractor.convertPipedObjectToString(row, template);
		    list.add(object);
		}
	    } else {
		for (Object row : data) {
		    String object;
		    object = Extractor.convertCopyObjectToString(row, template);
		    list.add(object);
		}
	    }

	}

	return list;

    }

    public static <H, D, T> void convertObjectsToFile(String file, Stack<H> headers, Stack<D> data, Stack<T> tails, Class<? extends Annotation> annon) {

	ParseUtils.notNull(file, "file must not be null");
	ParseUtils.notNull(data, "data must not be null");
	ParseUtils.isTrue(data.empty(), "data must not be empty");

	RowSuplier<H> rowHeaderSuplier = null;
	RowSuplier<D> rowDataSuplier;
	RowSuplier<T> rowTailSuplier = null;

	final ClassParseDTO headerTemplate;
	final ClassParseDTO template = ParseUtils.getTemplate(data.get(0).getClass(), annon, Boolean.FALSE);
	final ClassParseDTO tailTemplate;

	if (headers != null && !headers.empty()) {
	    headerTemplate = ParseUtils.getTemplate(headers.get(0).getClass(), annon, Boolean.FALSE);
	} else {
	    headerTemplate = null;
	}

	if (tails != null && !tails.empty()) {
	    tailTemplate = ParseUtils.getTemplate(tails.get(0).getClass(), annon, Boolean.FALSE);
	} else {
	    tailTemplate = null;
	}

	if (annon.equals(PipedField.class)) {

	    if (headerTemplate != null) {
		rowHeaderSuplier = new RowSuplier<H>() {
		    public String get(H object) {
			return Extractor.convertPipedObjectToString(object, headerTemplate);
		    }
		};
	    }

	    rowDataSuplier = new RowSuplier<D>() {
		public String get(D object) {
		    return Extractor.convertPipedObjectToString(object, template);
		}
	    };

	    if (tailTemplate != null) {
		rowTailSuplier = new RowSuplier<T>() {
		    public String get(T object) {
			return Extractor.convertPipedObjectToString(object, tailTemplate);
		    }
		};
	    }

	} else {

	    if (headerTemplate != null) {
		rowHeaderSuplier = new RowSuplier<H>() {
		    public String get(H object) {
			return Extractor.convertCopyObjectToString(object, headerTemplate);
		    }
		};
	    }

	    rowDataSuplier = new RowSuplier<D>() {

		public String get(D object) {
		    return Extractor.convertCopyObjectToString(object, template);
		}

	    };

	    if (tailTemplate != null) {
		rowTailSuplier = new RowSuplier<T>() {
		    public String get(T object) {
			return Extractor.convertCopyObjectToString(object, tailTemplate);
		    }
		};
	    }
	}

	ParseUtils.writeFile(file, headers, rowHeaderSuplier, data, rowDataSuplier, tails, rowTailSuplier);
    }

    /**********************************
     * Start String to object parsers *
     **********************************/

    /**
     * 
     * @param data
     * @param type
     * @param annon
     * @return
     */
    public static <T extends Object> T convertStringToObject(String data, Class<T> type, Class<? extends Annotation> annon) {
	ParseUtils.notNull(data, "data must not be null");
	ParseUtils.notNull(type, "type must not be null");

	T obj;
	ClassParseDTO extracts = ParseUtils.getTemplate(type, annon, Boolean.TRUE);
	obj = Extractor.convertStringRowToObject(data, type, extracts);

	return obj;
    }

    /**
     * 
     * @param data
     * @param type
     * @param annon
     * @return
     */
    public static <T extends Object> List<T> convertStringsToObjects(List<String> data, Class<T> type, Class<? extends Annotation> annon) {
	ParseUtils.notNull(data, "data must not be null");
	ParseUtils.notNull(type, "type must not be null");

	List<T> list = new ArrayList<T>();

	if (!data.isEmpty()) {
	    int rowNum = 0;
	    ClassParseDTO extracts = ParseUtils.getTemplate(type, annon, Boolean.TRUE);

	    try {
		for (String row : data) {
		    rowNum++;
		    T object;
		    object = Extractor.convertStringRowToObject(row, type, extracts);
		    list.add(object);
		}
	    } catch (RecordParserException e) {
		throw new RecordParserException("An error has occurred while processing row: " + rowNum + "; Detail: " + e.getMessage(), e);
	    } catch (Exception e) {
		throw new RecordParserException("An error has occurred while processing row: " + rowNum, e);
	    }

	}

	return list;

    }

    /**
     * 
     * @param file
     * @param headerType
     * @param headerSize
     * @param headerConsumer
     * @param dataType
     * @param dataConsumer
     * @param tailType
     * @param tailSize
     * @param tailConsumer
     * @param annon
     */
    public static <T, U, V> void convertFileToObjects(String file,
	    final Class<T> headerType,
	    final Integer headerSize,
	    final ObjectConsumer<T> headerConsumer,
	    final Class<U> dataType,
	    final ObjectConsumer<U> dataConsumer,
	    final Class<V> tailType,
	    Integer tailSize,
	    final ObjectConsumer<V> tailConsumer,
	    Class<? extends Annotation> annon) {

	ParseUtils.notNull(file, "file must not be null");
	ParseUtils.notNull(dataType, "type must not be null");
	ParseUtils.notNull(dataConsumer, "action must not be null");

	ParseUtils.isTrue(headerType != null && (headerSize == null || headerSize < 1), "headerSize have to be greater than 0");
	ParseUtils.isTrue(tailType != null && (tailSize == null || tailSize < 1), "tailSize have to be greater than 0");

	final MutableCounter rowNum = new MutableCounter();

	final ClassParseDTO extracts = ParseUtils.getTemplate(dataType, annon, Boolean.TRUE);
	final ClassParseDTO headerExtracts;
	final ClassParseDTO tailExtracts;

	final Integer tailLine;

	if (headerType != null) {
	    headerExtracts = ParseUtils.getTemplate(headerType, annon, Boolean.TRUE);
	} else {
	    headerExtracts = null;
	}

	if (tailType != null) {
	    tailLine = ParseUtils.countLinesNew(file) - tailSize;
	    tailExtracts = ParseUtils.getTemplate(tailType, annon, Boolean.TRUE);
	} else {
	    tailLine = null;
	    tailExtracts = null;
	}

	RowConsumer actionForRow = new RowConsumer() {

	    public void process(String row) throws Exception {

		rowNum.increment();

		if (headerExtracts != null && rowNum.getValue() <= headerSize) {
		    T object = Extractor.convertStringRowToObject(row, headerType, headerExtracts);
		    headerConsumer.process(object);
		} else if (tailLine != null && rowNum.getValue() > tailLine) {
		    V object = Extractor.convertStringRowToObject(row, tailType, tailExtracts);
		    tailConsumer.process(object);
		} else {
		    U object = Extractor.convertStringRowToObject(row, dataType, extracts);
		    dataConsumer.process(object);
		}

	    }

	};

	try {
	    ParseUtils.readTextFile(file, actionForRow);
	} catch (RecordParserException e) {
	    throw new RecordParserException("An error has occurred while processing file: " + file + "; row: " + rowNum.getValue() + "; Detail: " + e.getMessage(), e);
	} catch (Exception e) {
	    throw new RecordParserException("An error has occurred while processing file: " + file + "; row: " + rowNum.getValue(), e);
	}

    }

    /**
     * 
     * @param file
     * @param dataType
     * @param dataConsumer
     * @param annon
     */
    public static <T> void convertFileToObjects(String file, final Class<T> dataType, final ObjectConsumer<T> dataConsumer, Class<? extends Annotation> annon) {
	Extractor.convertFileToObjects(file, null, null, null, dataType, dataConsumer, null, null, null, annon);
    }

    /**
     * 
     * @param file
     * @param headerType
     * @param headerSize
     * @param headerConsumer
     * @param dataType
     * @param dataConsumer
     * @param annon
     */
    public static <T, U, V> void convertFileToObjects(String file,
	    Class<T> headerType,
	    Integer headerSize,
	    ObjectConsumer<T> headerConsumer,
	    Class<U> dataType,
	    ObjectConsumer<U> dataConsumer,
	    Class<? extends Annotation> annon) {

	Extractor.convertFileToObjects(file, headerType, headerSize, headerConsumer, dataType, dataConsumer, null, null, null, annon);

    }

    /**
     * 
     * @param file
     * @param dataType
     * @param dataConsumer
     * @param tailType
     * @param tailSize
     * @param tailConsumer
     * @param annon
     */
    public static <T, U, V> void convertFileToObjects(String file,
	    Class<U> dataType,
	    ObjectConsumer<U> dataConsumer,
	    Class<V> tailType,
	    Integer tailSize,
	    ObjectConsumer<V> tailConsumer,
	    Class<? extends Annotation> annon) {

	Extractor.convertFileToObjects(file, null, null, null, dataType, dataConsumer, tailType, tailSize, tailConsumer, annon);

    }

    /***
     * *
     ***/

    /***
     * *
     ***/

    /***
     * *
     ***/

    /***
     * *
     ***/

    /***
     * *
     ***/

    /***
     * *
     ***/

    /*************************
     * Start private methods *
     *************************/

    /**
     * Conver a string to a dto object
     * 
     * @param data
     * @param type
     * @param template
     * @param rowNum
     * @return
     */
    @SuppressWarnings("unchecked")
    private static <T extends Object> T convertStringRowToObject(String data, Class<T> type, ClassParseDTO template) {

	T object;

	String values[] = null;

	if (template.getType().equals(ClassParseDTO.Type.PIPE)) {
	    values = data.split(PIPE_SEPARATOR, -1);
	}

	try {
	    object = type.newInstance();
	} catch (IllegalAccessException e) {
	    throw new RuntimeException(e);
	} catch (InstantiationException e) {
	    throw new RuntimeException(e);
	}

	for (FieldParseDTO ex : template.getFieldParseDTOs()) {

	    Field field = ex.getField();
	    PipedField pf = ex.getPipeField();
	    Pic pic = ex.getPic();

	    Converter conv = ex.getConverter();

	    String fieldName = field.getName();
	    String strValue = null;
	    Object objValue = null;
	    Class<? extends FieldConverter<Object>> clazzConv = null;

	    try {
		if (template.getType().equals(ClassParseDTO.Type.PIPE)) {
		    int indx = pf.value();
		    strValue = values[indx];
		} else {
		    // extracts - Type is equals to Template.Type.PIC
		    int begin = pic.beginIndex();
		    int size = pic.size();
		    
		    if ((begin + size) > data.length()) {
			strValue = data.substring(begin);
		    } else {
			strValue = data.substring(begin, begin + size);
		    }

		}

		if (strValue != null) {

		    field.setAccessible(Boolean.TRUE);
		    try {
			if (conv == null) {
			    objValue = ParseUtils.parse(strValue, field.getType());
			} else {
			    clazzConv = (Class<? extends FieldConverter<Object>>) conv.value();
			    FieldConverter<?> c = template.getConverter(clazzConv);
			    if (c == null) {
				c = clazzConv.newInstance();
				template.addConverter(c);
			    }
			    objValue = c.toObject(strValue, conv.args());
			}
		    } catch (Exception e) {
			throw new ParseException(e.getMessage(), e);
		    }

		    field.set(object, objValue);
		}

	    } catch (IndexOutOfBoundsException e) {
		// if is IndexOutOfBoundsException strValue is null
		throw new RecordParserException("An error has occurred while getting value, field_name: " + fieldName + "; of:  '" + data + "'; index_not_found: " + e.getMessage() + "; class: " + type.getCanonicalName() + "; ", e);
	    } catch (ParseException e) {
		// if is ParseException objValue is null
		String convDesc = clazzConv != null ? "converter: " + clazzConv.getCanonicalName() + "; " : "";
		throw new RecordParserException("An error has occurred while setting value, original value: " + (strValue != null ? "'" + strValue + "'" : null) + "; " + convDesc + "class: " + type.getCanonicalName() + "; " + "field name: " + fieldName + "; field type: " + field.getType().getCanonicalName() + "; ", e);
	    } catch (Exception e) {
		String convDesc = clazzConv != null ? "converter: " + clazzConv.getCanonicalName() + "; " : "";
		throw new RecordParserException("An error has occurred while setting value, original value: " + (strValue != null ? "'" + strValue + "'" : null) + "; converted value: " + (objValue != null ? "'" + objValue + "'" : null) + "; " + convDesc + "class: " + type.getCanonicalName() + "; " + "field name: " + fieldName + "; field type: " + field.getType().getCanonicalName() + "; ", e);
	    }

	}

	return object;
    }

    /**
     * 
     * @param data
     * @param template
     * @return
     */
    @SuppressWarnings("unchecked")
    private static String convertPipedObjectToString(Object data, ClassParseDTO template) {

	StringBuilder sb = new StringBuilder();

	int last = template.getLastIndex();

	for (int idx = 0; idx <= last; idx++) {

	    FieldParseDTO extract = template.getExtractMap().get(idx);
	    if (extract != null) {

		Field field = extract.getField();
		field.setAccessible(Boolean.TRUE);

		String name = field.getName();

		Object value;

		try {
		    value = field.get(data);
		} catch (IllegalArgumentException e) {
		    throw new RecordParserException("An error has occurred while getting value in class: " + data.getClass().getCanonicalName() + "; field: " + field.getName() + ";", e);
		} catch (IllegalAccessException e) {
		    throw new RecordParserException("An error has occurred while getting value in class: " + data.getClass().getCanonicalName() + "; field: " + field.getName() + ";", e);
		}

		Class<? extends FieldConverter<?>> conv = null;
		try {

		    if (extract.getConverter() == null) {
			sb.append(value != null ? value.toString() : EMPTY);
		    } else {
			conv = extract.getConverter().value();
			FieldConverter<Object> c = (FieldConverter<Object>) template.getConverter(conv);

			if (c == null) {
			    c = (FieldConverter<Object>) conv.newInstance();
			    template.addConverter(c);
			}

			sb.append(c.toString(value, extract.getConverter().args()));
		    }

		} catch (InstantiationException e) {
		    String convName = conv != null ? "converter: " + conv.getCanonicalName() + "; " : EMPTY;
		    throw new RecordParserException("An error has occurred while converting value: '" + value + "'; " + convName + "class: " + data.getClass().getCanonicalName() + "; field: " + name + ";", e);
		} catch (IllegalAccessException e) {
		    String convName = conv != null ? "converter: " + conv.getCanonicalName() + "; " : EMPTY;
		    throw new RecordParserException("An error has occurred while converting value: '" + value + "'; " + convName + "class: " + data.getClass().getCanonicalName() + "; field: " + name + ";", e);
		} catch (ClassCastException e) {
		    String convName = conv != null ? "converter: " + conv.getCanonicalName() + "; " : EMPTY;
		    throw new RecordParserException("An error has occurred while converting value: '" + value + "'; " + convName + "class: " + data.getClass().getCanonicalName() + "; field: " + name + ";", e);
		} catch (Exception e) {
		    String convName = conv != null ? "converter: " + conv.getCanonicalName() + "; " : EMPTY;
		    throw new RecordParserException("An error has occurred while converting value: '" + value + "'; " + convName + "class: " + data.getClass().getCanonicalName() + "; field: " + name + ";", e);
		}

	    }

	    if (idx < last) {
		sb.append("|");
	    }
	}

	return sb.toString();
    }

    /**
     * 
     * @param data
     * @param template
     * @return
     */
    @SuppressWarnings("unchecked")
    private static String convertCopyObjectToString(Object data, ClassParseDTO template) {

	int size = template.getLastIndex();

	StringBuilder sb = new StringBuilder(size);
	for (int i = 0; i < size; i++) {
	    sb.append(SPACE);
	}

	List<FieldParseDTO> extracts = template.getFieldParseDTOs();

	for (FieldParseDTO extract : extracts) {

	    Field field = extract.getField();
	    field.setAccessible(Boolean.TRUE);

	    String name = field.getName();

	    Object value;

	    try {
		value = field.get(data);
	    } catch (IllegalArgumentException e) {
		throw new RecordParserException("An error has occurred while getting value in class: " + data.getClass().getCanonicalName() + "; field: " + field.getName() + ";", e);
	    } catch (IllegalAccessException e) {
		throw new RecordParserException("An error has occurred while getting value in class: " + data.getClass().getCanonicalName() + "; field: " + field.getName() + ";", e);
	    }

	    Class<? extends FieldConverter<?>> conv = null;

	    try {

		String val;
		if (extract.getConverter() == null) {
		    val = value != null ? value.toString() : EMPTY;
		} else {
		    conv = extract.getConverter().value();
		    FieldConverter<Object> c = (FieldConverter<Object>) template.getConverter(conv);
		    if (c == null) {
			c = (FieldConverter<Object>) conv.newInstance();
			template.addConverter(c);
		    }

		    val = c.toString(value, extract.getConverter().args());
		}

		int fieldSize = extract.getPic().size();
 		if (val.length() > fieldSize) {
		    val = val.substring(0, fieldSize);
		} else if (val.length() < fieldSize) {
		    StringBuilder ssb = new StringBuilder(fieldSize);
		    ssb.append(val);
		    int lim = fieldSize - val.length();
		    for (int i = 0; i < lim; i++) {
			ssb.append(SPACE);
		    }
		    val = ssb.toString();
		}

		int start = extract.getPic().beginIndex();
		sb.replace(start, start + fieldSize, val);

	    } catch (InstantiationException e) {
		String convName = conv != null ? "converter: " + conv.getCanonicalName() + "; " : EMPTY;
		throw new RecordParserException("An error has occurred while converting value: '" + value + "'; " + convName + "class: " + data.getClass().getCanonicalName() + "; field: " + name + ";", e);
	    } catch (IllegalAccessException e) {
		String convName = conv != null ? "converter: " + conv.getCanonicalName() + "; " : EMPTY;
		throw new RecordParserException("An error has occurred while converting value: '" + value + "'; " + convName + "class: " + data.getClass().getCanonicalName() + "; field: " + name + ";", e);
	    } catch (ClassCastException e) {
		String convName = conv != null ? "converter: " + conv.getCanonicalName() + "; " : EMPTY;
		throw new RecordParserException("An error has occurred while converting value: '" + value + "'; " + convName + "class: " + data.getClass().getCanonicalName() + "; field: " + name + ";", e);
	    } catch (Exception e) {
		String convName = conv != null ? "converter: " + conv.getCanonicalName() + "; " : EMPTY;
		throw new RecordParserException("An error has occurred while converting value: '" + value + "'; " + convName + "class: " + data.getClass().getCanonicalName() + "; field: " + name + ";", e);
	    }

	}

	return sb.toString();
    }

}
