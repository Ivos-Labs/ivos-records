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
	Template template = ParseUtils.getTemplate(data.getClass(), annon, Boolean.FALSE);
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
	    Template template = ParseUtils.getTemplate(data.get(0).getClass(), annon, Boolean.FALSE);

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

    /**
     * 
     * @param file
     * @param objects
     */
    public static <T> void convertObjectsToFile(String file, Stack<T> objects, Class<? extends Annotation> annon) {
	ParseUtils.notNull(file, "file must not be null");
	ParseUtils.notNull(objects, "objects must not be null");
	ParseUtils.isTrue(objects.empty(), "objects must not be empty");

	final Template template = ParseUtils.getTemplate(objects.get(0).getClass(), annon, Boolean.FALSE);

	RowSuplier<T> rowSuplier;

	if (annon.equals(PipedField.class)) {
	    rowSuplier = new RowSuplier<T>() {

		public String get(T object) {
		    String row;
		    row = Extractor.convertPipedObjectToString(object, template);
		    return row;
		}

	    };
	} else {
	    rowSuplier = new RowSuplier<T>() {

		public String get(T object) {
		    String row;
		    row = Extractor.convertCopyObjectToString(object, template);
		    return row;
		}

	    };
	}

	ParseUtils.writeFile(file, objects, rowSuplier);

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
	Template extracts = ParseUtils.getTemplate(type, annon, Boolean.TRUE);
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
	    Template extracts = ParseUtils.getTemplate(type, annon, Boolean.TRUE);

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
     * @param type
     * @param action
     * @param annon
     */
    public static <T> void convertFileToObjects(String file, final Class<T> type, final ObjectConsumer<T> action, Class<? extends Annotation> annon) {
	ParseUtils.notNull(file, "file must not be null");
	ParseUtils.notNull(type, "type must not be null");
	ParseUtils.notNull(action, "action must not be null");

	final MutableCounter rowNum = new MutableCounter();
	final Template extracts = ParseUtils.getTemplate(type, annon, Boolean.TRUE);

	RowConsumer actionForRow = new RowConsumer() {
 
	    public void process(String row) {
		T object;
		rowNum.increment();
		object = Extractor.convertStringRowToObject(row, type, extracts);
		action.process(object);
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
    private static <T extends Object> T convertStringRowToObject(String data, Class<T> type, Template template) {

	T object;

	String values[] = null;

	if (template.getType().equals(Template.Type.PIPE)) {
	    values = data.split(PIPE_SEPARATOR, -1);
	}

	try {
	    object = type.newInstance();
	} catch (IllegalAccessException e) {
	    throw new RuntimeException(e);
	} catch (InstantiationException e) {
	    throw new RuntimeException(e);
	}

	for (Extract ex : template.getExtracts()) {

	    Field field = ex.getField();
	    PipedField pf = ex.getPipeField();
	    Pic pic = ex.getCopyField();

	    Converter conv = ex.getConverter();

	    String fieldName = field.getName();
	    String strValue = null;
	    Object objValue = null;
	    Class<? extends FieldConverter<Object>> clazzConv = null;

	    try {
		if (template.getType().equals(Template.Type.PIPE)) {
		    int indx = pf.value();
		    strValue = values[indx];
		} else {
		    // extracts - Type is equals to Template.Type.PIC
		    int begin = pic.beginIndex();
		    int size = pic.size();
		    strValue = data.substring(begin, begin + size);
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
		throw new RecordParserException("An error occurred getting value, field_name: " + fieldName + "; of:  '" + data + "'; index_not_found: " + e.getMessage() + "; class: " + type.getCanonicalName() + "; ", e);
	    } catch (ParseException e) {
		// if is ParseException objValue is null
		String convDesc = clazzConv != null ? "converter: " + clazzConv.getCanonicalName() + "; " : "";
		throw new RecordParserException("An error occurred setting value, original value: " + (strValue != null ? "'" + strValue + "'" : null) + "; " + convDesc + "class: " + type.getCanonicalName() + "; " + "field name: " + fieldName + "; field type: " + field.getType().getCanonicalName() + "; ", e);
	    } catch (Exception e) {
		String convDesc = clazzConv != null ? "converter: " + clazzConv.getCanonicalName() + "; " : "";
		throw new RecordParserException("An error occurred setting value, original value: " + (strValue != null ? "'" + strValue + "'" : null) + "; converted value: " + (objValue != null ? "'" + objValue + "'" : null) + "; " + convDesc + "class: " + type.getCanonicalName() + "; " + "field name: " + fieldName + "; field type: " + field.getType().getCanonicalName() + "; ", e);
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
    private static String convertPipedObjectToString(Object data, Template template) {

	StringBuilder sb = new StringBuilder();

	int last = template.getLastIndex();

	for (int idx = 0; idx <= last; idx++) {

	    Extract extract = template.getExtractMap().get(idx);
	    if (extract != null) {

		Field field = extract.getField();
		field.setAccessible(Boolean.TRUE);

		String name = field.getName();

		Object value;

		try {
		    value = field.get(data);
		} catch (IllegalArgumentException e) {
		    throw new RecordParserException("An error occurred getting value in class: " + data.getClass().getCanonicalName() + "; field: " + field.getName() + ";", e);
		} catch (IllegalAccessException e) {
		    throw new RecordParserException("An error occurred getting value in class: " + data.getClass().getCanonicalName() + "; field: " + field.getName() + ";", e);
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
		    throw new RecordParserException("An error occurred converting value: '" + value + "'; " + convName + "class: " + data.getClass().getCanonicalName() + "; field: " + name + ";", e);
		} catch (IllegalAccessException e) {
		    String convName = conv != null ? "converter: " + conv.getCanonicalName() + "; " : EMPTY;
		    throw new RecordParserException("An error occurred converting value: '" + value + "'; " + convName + "class: " + data.getClass().getCanonicalName() + "; field: " + name + ";", e);
		} catch (ClassCastException e) {
		    String convName = conv != null ? "converter: " + conv.getCanonicalName() + "; " : EMPTY;
		    throw new RecordParserException("An error occurred converting value: '" + value + "'; " + convName + "class: " + data.getClass().getCanonicalName() + "; field: " + name + ";", e);
		} catch (Exception e) {
		    String convName = conv != null ? "converter: " + conv.getCanonicalName() + "; " : EMPTY;
		    throw new RecordParserException("An error occurred converting value: '" + value + "'; " + convName + "class: " + data.getClass().getCanonicalName() + "; field: " + name + ";", e);
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
    private static String convertCopyObjectToString(Object data, Template template) {

	int size = template.getLastIndex();

	StringBuilder sb = new StringBuilder(size);
	for (int i = 0; i < size; i++) {
	    sb.append(SPACE);
	}

	List<Extract> extracts = template.getExtracts();

	for (Extract extract : extracts) {

	    Field field = extract.getField();
	    field.setAccessible(Boolean.TRUE);

	    String name = field.getName();

	    Object value;

	    try {
		value = field.get(data);
	    } catch (IllegalArgumentException e) {
		throw new RecordParserException("An error occurred getting value in class: " + data.getClass().getCanonicalName() + "; field: " + field.getName() + ";", e);
	    } catch (IllegalAccessException e) {
		throw new RecordParserException("An error occurred getting value in class: " + data.getClass().getCanonicalName() + "; field: " + field.getName() + ";", e);
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

		int fieldSize = extract.getCopyField().size();
		if (val.length() > fieldSize) {
		    val = val.substring(0, fieldSize);
		} else if (val.length() < fieldSize) {
		    StringBuilder ssb = new StringBuilder(fieldSize);
		    ssb.append(val);
		    int lim = fieldSize - val.length();
		    for (int i = 0; i < lim; i++) {
			ssb.append(SPACE);
		    }
		}

		int start = extract.getCopyField().beginIndex();
		sb.replace(start, start + fieldSize, val);

	    } catch (InstantiationException e) {
		String convName = conv != null ? "converter: " + conv.getCanonicalName() + "; " : EMPTY;
		throw new RecordParserException("An error occurred converting value: '" + value + "'; " + convName + "class: " + data.getClass().getCanonicalName() + "; field: " + name + ";", e);
	    } catch (IllegalAccessException e) {
		String convName = conv != null ? "converter: " + conv.getCanonicalName() + "; " : EMPTY;
		throw new RecordParserException("An error occurred converting value: '" + value + "'; " + convName + "class: " + data.getClass().getCanonicalName() + "; field: " + name + ";", e);
	    } catch (ClassCastException e) {
		String convName = conv != null ? "converter: " + conv.getCanonicalName() + "; " : EMPTY;
		throw new RecordParserException("An error occurred converting value: '" + value + "'; " + convName + "class: " + data.getClass().getCanonicalName() + "; field: " + name + ";", e);
	    } catch (Exception e) {
		String convName = conv != null ? "converter: " + conv.getCanonicalName() + "; " : EMPTY;
		throw new RecordParserException("An error occurred converting value: '" + value + "'; " + convName + "class: " + data.getClass().getCanonicalName() + "; field: " + name + ";", e);
	    }

	}

	return sb.toString();
    }

}
