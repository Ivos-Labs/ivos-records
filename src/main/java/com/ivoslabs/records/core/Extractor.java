/**
 * 
 */
package com.ivoslabs.records.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.mutable.MutableInt;

import com.ivoslabs.records.annontation.Converter;
import com.ivoslabs.records.annontation.Pic;
import com.ivoslabs.records.converters.FieldConverter;
import com.ivoslabs.records.core.func.RowConsumer;
import com.ivoslabs.records.core.func.RowSuplier;
import com.ivoslabs.records.dtos.BaseClass;
import com.ivoslabs.records.dtos.CopyClass;
import com.ivoslabs.records.dtos.CopyField;
import com.ivoslabs.records.dtos.DTOFactory;
import com.ivoslabs.records.dtos.BaseField;
import com.ivoslabs.records.dtos.PipedClass;
import com.ivoslabs.records.dtos.PipedField;
import com.ivoslabs.records.exceptions.ParseException;
import com.ivoslabs.records.exceptions.RecordParserException;
import com.ivoslabs.records.function.Consumer;
import com.ivoslabs.records.utils.ParseUtils;

/**
 * @author www.ivoslabs.com
 *
 */
public class Extractor {

    /***
     * *
     ***/

    /***
     * *
     ***/

    /**********************************
     * Start String-To-Object methods *
     **********************************/

    /**
     * 
     * Creates a new instance of T using the received data
     * 
     * @param       <T> Required type
     * @param data  values separated by pipes
     * @param type  required type
     * @param annon annotation to indicate if is Piped or COPY file
     * @return a T instance
     */
    public static <T extends Object> T convertStringToObject(String data, Class<T> type, Class<? extends Annotation> annon) {
	Validate.notNull(data, "data must not be null");
	Validate.notNull(type, "type must not be null");

	T obj;
	BaseClass extracts = DTOFactory.getTemplate(type, annon, Boolean.TRUE);
	obj = Extractor.convertStringRowToObject(data, type, extracts);

	return obj;
    }

    /**
     * 
     * Creates a new instance of T using each item in the received data list
     * 
     * @param       <T> Required type
     * @param data  list of values separated by pipes
     * @param type  required type
     * @param annon annotation to indicate if is Piped or COPY file
     * @return a List of T instance
     */
    public static <T extends Object> List<T> convertStringsToObjects(List<String> data, Class<T> type, Class<? extends Annotation> annon) {
	Validate.notNull(data, "data must not be null");
	Validate.notNull(type, "type must not be null");

	List<T> list = new ArrayList<T>();

	if (!data.isEmpty()) {
	    int rowNum = 0;
	    BaseClass extracts = DTOFactory.getTemplate(type, annon, Boolean.TRUE);

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
     * Creates an instance of H, D or T for each row in a file and execute the respective received ObjectConsumer
     * 
     * @param                <H> Header type
     * @param                <D> Data type
     * @param                <T> Tail type
     * @param file           file path
     * @param headerType     header type
     * @param headerSize     number of first rows to be processed with headerType and headerConsumer
     * @param headerConsumer action to do for each header instance
     * @param dataType       data type
     * @param dataConsumer   action to do for each data instance
     * @param tailType       tail type
     * @param tailSize       number of last rows to be processed with tailType and tailConsumer
     * @param tailConsumer   action to do for each tail instance
     * @param annon          annotation to indicate if is Piped or COPY file
     */
    public static <H, D, T> void processFile(String file,
	    // header
	    final Class<H> headerType,
	    final Integer headerSize,
	    final Consumer<H> headerConsumer,
	    // data
	    final Class<D> dataType,
	    final Consumer<D> dataConsumer,
	    // tail
	    final Class<T> tailType,
	    Integer tailSize,
	    final Consumer<T> tailConsumer,
	    Class<? extends Annotation> annon) {

	Validate.notNull(file, "file must not be null");
	Validate.notNull(dataType, "type must not be null");
	Validate.notNull(dataConsumer, "action must not be null");

	Validate.isTrue(headerType == null || (headerSize != null && headerSize > 0), "headerSize have to be greater than 0");
	Validate.isTrue(tailType == null || (tailSize != null && tailSize > 0), "tailSize have to be greater than 0");

	final BaseClass extracts = DTOFactory.getTemplate(dataType, annon, Boolean.TRUE);
	final BaseClass headerExtracts;
	final BaseClass tailExtracts;

	final Integer tailLine;

	if (headerType != null) {
	    headerExtracts = DTOFactory.getTemplate(headerType, annon, Boolean.TRUE);
	} else {
	    headerExtracts = null;
	}

	if (tailType != null) {
	    tailLine = ParseUtils.countLinesNew(file) - tailSize;
	    tailExtracts = DTOFactory.getTemplate(tailType, annon, Boolean.TRUE);
	} else {
	    tailLine = null;
	    tailExtracts = null;
	}

	final MutableInt rowNum = new MutableInt();

	RowConsumer actionForRow = new RowConsumer() {

	    public void process(String row, int rowNumber) throws Exception {
		rowNum.setValue(rowNumber);
		if (headerExtracts != null && rowNumber <= headerSize) {
		    H object = Extractor.convertStringRowToObject(row, headerType, headerExtracts);
		    headerConsumer.process(object);
		} else if (tailLine != null && rowNumber > tailLine) {
		    T object = Extractor.convertStringRowToObject(row, tailType, tailExtracts);
		    tailConsumer.process(object);
		} else {
		    D object = Extractor.convertStringRowToObject(row, dataType, extracts);
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

    /********************************
     * End String-To-Object methods *
     ********************************/

    /***
     * *
     ***/

    /***
     * *
     ***/

    /**********************************
     * Start Object-To-String methods *
     **********************************/

    /**
     * 
     * Creates a {@code String} object representing the received object
     * 
     * @param data  the {@code Object} to be converted to a {@code Strings}
     * @param annon annotation to indicate if is Piped or COPY file
     * 
     * @return a string representation of the value of the received object
     */
    public static String convertObjectToString(Object data, Class<? extends Annotation> annon) {
	Validate.notNull(data, "data must not be null");
	String obj;
	BaseClass template = DTOFactory.getTemplate(data.getClass(), annon, Boolean.FALSE);

	if (template instanceof PipedClass) {
	    obj = Extractor.convertPipedObjectToString(data, (PipedClass) template);
	} else {
	    obj = Extractor.convertCopyObjectToString(data, (CopyClass) template);
	}

	return obj;
    }

    /**
     * Creates a {@code String} object representing each item in the received object list
     * 
     * @param data  the {@code Object} list to be converted to a {@code Strings}
     * @param annon annotation to indicate if is Piped or COPY file
     * @return a list of string representation of the value of each item in the received object list
     */
    public static List<String> convertObjectsToStrings(List<?> data, Class<? extends Annotation> annon) {
	Validate.notNull(data, "data must not be null");
	Validate.isTrue(!data.isEmpty(), "data must not be empty");

	List<String> list = new ArrayList<String>();

	if (!data.isEmpty()) {
	    BaseClass template = DTOFactory.getTemplate(data.get(0).getClass(), annon, Boolean.FALSE);

	    if (template instanceof PipedClass) {
		for (Object row : data) {
		    String object;
		    object = Extractor.convertPipedObjectToString(row, (PipedClass) template);
		    list.add(object);
		}
	    } else {
		for (Object row : data) {
		    String object;
		    object = Extractor.convertCopyObjectToString(row, (CopyClass) template);
		    list.add(object);
		}
	    }

	}

	return list;

    }

    /********************************
     * End Object-To-String methods *
     ********************************/

    /***
     * *
     ***/

    /***
     * *
     ***/

    /********************************
     * Start Object-To-File methods *
     ********************************/

    /**
     * Creates a {@code String} object representing each item in the received object lists and append it into a file
     * 
     * @param        <H> Header type
     * @param        <D> Data type
     * @param        <T> Tail type
     * @param file   path file to save string representations of the value of each item in the received object lists
     * @param header objects to be appended into the fist rows of the file
     * @param data   objects to be appended into after header rows
     * @param tails  objects to be appended into the last rows of the file
     * @param annon  annotation to indicate if is Piped or COPY file
     * 
     */
    public static <H, D, T> void convertObjectsToFile(String file, Stack<H> header, Stack<D> data, Stack<T> tails, Class<? extends Annotation> annon) {

	Validate.notNull(file, "file must not be null");
	Validate.notNull(data, "data must not be null");
	Validate.isTrue(!data.empty(), "data must not be empty");

	RowSuplier<H> rowHeaderSuplier = null;
	RowSuplier<D> rowDataSuplier;
	RowSuplier<T> rowTailSuplier = null;

	final BaseClass headerTemplate;
	final BaseClass template = DTOFactory.getTemplate(data.get(0).getClass(), annon, Boolean.FALSE);
	final BaseClass tailTemplate;

	if (header != null && !header.empty()) {
	    headerTemplate = DTOFactory.getTemplate(header.get(0).getClass(), annon, Boolean.FALSE);
	} else {
	    headerTemplate = null;
	}

	if (tails != null && !tails.empty()) {
	    tailTemplate = DTOFactory.getTemplate(tails.get(0).getClass(), annon, Boolean.FALSE);
	} else {
	    tailTemplate = null;
	}

	if (headerTemplate instanceof PipedClass) {
	    rowHeaderSuplier = new RowSuplier<H>() {
		public String get(H object) {
		    return Extractor.convertPipedObjectToString(object, (PipedClass) headerTemplate);
		}
	    };
	}

	if (template instanceof PipedClass) {
	    rowDataSuplier = new RowSuplier<D>() {
		public String get(D object) {
		    return Extractor.convertPipedObjectToString(object, (PipedClass) template);
		}
	    };
	} else {
	    rowDataSuplier = new RowSuplier<D>() {

		public String get(D object) {
		    return Extractor.convertCopyObjectToString(object, (CopyClass) template);
		}

	    };
	}

	if (tailTemplate instanceof PipedClass) {
	    rowTailSuplier = new RowSuplier<T>() {
		public String get(T object) {
		    return Extractor.convertPipedObjectToString(object, (PipedClass) tailTemplate);
		}
	    };
	}

	if (headerTemplate instanceof CopyClass) {
	    rowHeaderSuplier = new RowSuplier<H>() {
		public String get(H object) {
		    return Extractor.convertCopyObjectToString(object, (CopyClass) headerTemplate);
		}
	    };
	}

	if (tailTemplate instanceof CopyClass) {
	    rowTailSuplier = new RowSuplier<T>() {
		public String get(T object) {
		    return Extractor.convertCopyObjectToString(object, (CopyClass) tailTemplate);
		}
	    };
	}

	ParseUtils.writeFile(file, header, rowHeaderSuplier, data, rowDataSuplier, tails, rowTailSuplier);
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
     * Conver a string to a DTO object
     * 
     * @param          <T> Required type
     * @param data     a string representation of a DTO object
     * @param type     required type
     * @param template DTO with the the required converters to convert the object
     * @return a new T instance
     */
    @SuppressWarnings("unchecked")
    private static <T extends Object> T convertStringRowToObject(String data, Class<T> type, BaseClass template) {

	T object;

	// array with values when is a piped String
	String values[] = null;

	boolean isPiped = template instanceof PipedClass;

	if (isPiped) {
	    values = data.split(ParseUtils.PIPE_SEPARATOR, -1);
	}

	try {
	    object = type.newInstance();
	} catch (IllegalAccessException e) {
	    throw new RuntimeException(e);
	} catch (InstantiationException e) {
	    throw new RuntimeException(e);
	}

	for (BaseField fieldParseDTO : template.getFieldParseDTOs()) {

	    Field field = fieldParseDTO.getField();

	    Converter conv = fieldParseDTO.getConverter();

	    String fieldName = field.getName();
	    String strValue = null;
	    Object objValue = null;
	    Class<? extends FieldConverter<Object>> clazzConv = null;

	    try {
		if (template instanceof PipedClass) {
		    int indx = ((PipedField) fieldParseDTO).getPipeField().value();
		    // if is a piped file, get value from the array
		    strValue = values[indx];
		} else {
		    Pic pic = ((CopyField) fieldParseDTO).getPic();
		    // template.type is equals to ClassParseDTO.Type.PIC
		    // if is a piped file, get value from data
		    // using beginIndex and size values in Pic Annotation
		    int begin = pic.beginIndex();
		    int size = pic.size();

		    if ((begin + size) > data.length()) {
			// avoid IndexOutOfBoundsException if data is shorter than expected
			strValue = data.substring(begin);
		    } else {
			strValue = data.substring(begin, begin + size);
		    }

		}

		field.setAccessible(Boolean.TRUE);
		try {
		    String ifNull = fieldParseDTO.getIfNull();
		    if ((isPiped && !strValue.equals(ifNull)) || (!isPiped && !strValue.trim().equals(ifNull))) {

			if (conv == null) {
			    objValue = ParseUtils.parse(strValue, field.getType());
			} else {
			    clazzConv = (Class<? extends FieldConverter<Object>>) conv.value();
			    // get the converter in the ClassParseDTO
			    FieldConverter<?> c = template.getConverter(clazzConv);
			    objValue = c.toObject(strValue, conv.args());
			}
		    }

		} catch (Exception e) {
		    throw new ParseException(e.getMessage(), e);
		}

		field.set(object, objValue);

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
     * Creates a {@code String} object representing the received object
     * 
     * @param data     the {@code Object} to be converted to a {@code Strings}
     * @param template DTO with the the required converters to convert the object
     * @return a string representation of the value of the received object
     * 
     */
    @SuppressWarnings("unchecked")
    private static String convertPipedObjectToString(Object data, PipedClass template) {

	StringBuilder sb = new StringBuilder();

	int last = template.getLastIndex();

	for (int idx = 0; idx <= last; idx++) {

	    PipedField extract = template.getFieldParseDTO(idx);

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

		    String strValue;

		    if (value == null && extract.getIfNull() != null) {
			strValue = extract.getIfNull();
		    } else if (extract.getConverter() == null) {
			strValue = value != null ? value.toString() : null;
		    } else {
			conv = extract.getConverter().value();
			FieldConverter<Object> c = (FieldConverter<Object>) template.getConverter(conv);
			strValue = c.toString(value, extract.getConverter().args());
		    }

		    // cut if is longer than max size
		    if (extract.getMaxSize() != null && strValue.length() > extract.getMaxSize()) {
			// when the values is longer tha
			strValue = strValue.substring(ParseUtils.ZERO, extract.getMaxSize());
		    } else if (extract.getMaxSize() != null && extract.getPipeField().fixedSize()) {
			// when the fixed size was specified
			if (extract.getPipeField().rightAlign()) {
			    strValue = StringUtils.leftPad(strValue, extract.getMaxSize(), ParseUtils.SPACE);
			} else {
			    strValue = StringUtils.rightPad(strValue, extract.getMaxSize(), ParseUtils.SPACE);
			}
		    }

		    sb.append(strValue);

		} catch (InstantiationException e) {
		    String convName = conv != null ? "converter: " + conv.getCanonicalName() + "; " : ParseUtils.EMPTY;
		    throw new RecordParserException("An error has occurred while converting value: '" + value + "'; " + convName + "class: " + data.getClass().getCanonicalName() + "; field: " + name + ";", e);
		} catch (IllegalAccessException e) {
		    String convName = conv != null ? "converter: " + conv.getCanonicalName() + "; " : ParseUtils.EMPTY;
		    throw new RecordParserException("An error has occurred while converting value: '" + value + "'; " + convName + "class: " + data.getClass().getCanonicalName() + "; field: " + name + ";", e);
		} catch (ClassCastException e) {
		    String convName = conv != null ? "converter: " + conv.getCanonicalName() + "; " : ParseUtils.EMPTY;
		    throw new RecordParserException("An error has occurred while converting value: '" + value + "'; " + convName + "class: " + data.getClass().getCanonicalName() + "; field: " + name + ";", e);
		} catch (Exception e) {
		    String convName = conv != null ? "converter: " + conv.getCanonicalName() + "; " : ParseUtils.EMPTY;
		    throw new RecordParserException("An error has occurred while converting value: '" + value + "'; " + convName + "class: " + data.getClass().getCanonicalName() + "; field: " + name + ";", e);
		}

	    } else {
		sb.append(ParseUtils.EMPTY);
	    }

	    if (idx < last) {
		sb.append(ParseUtils.PIPE);
	    }
	}

	return sb.toString();
    }

    /**
     * Creates a {@code String} object representing the received object
     * 
     * @param data     the {@code Object} to be converted to a {@code Strings}
     * @param template DTO with the the required converters to convert the object
     * @return a string representation of the value of the received object
     * 
     */
    @SuppressWarnings("unchecked")
    private static String convertCopyObjectToString(Object data, CopyClass template) {

	int size = template.getSize();

	StringBuilder sb = new StringBuilder(size);
	for (int i = 0; i < size; i++) {
	    sb.append(ParseUtils.SPACE);
	}

	Collection<CopyField> extracts = template.getFieldParseDTOs();

	for (CopyField extract : extracts) {

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

		if (value == null && extract.getIfNull() != null) {
		    val = extract.getIfNull();
		} else if (extract.getConverter() == null) {
		    val = value != null ? value.toString() : ParseUtils.EMPTY;
		} else {
		    conv = extract.getConverter().value();
		    FieldConverter<Object> c = (FieldConverter<Object>) template.getConverter(conv);
		    val = c.toString(value, extract.getConverter().args());
		}

		int fieldSize = extract.getPic().size();
		// cut string if is longer than fieldSize
		if (val.length() > fieldSize) {
		    val = val.substring(0, fieldSize);
		} else if (val.length() < fieldSize) {
		    // add spaces is is shorter than fieldSize
		    StringBuilder ssb = new StringBuilder(fieldSize);
		    ssb.append(val);
		    int lim = fieldSize - val.length();
		    for (int i = 0; i < lim; i++) {
			ssb.append(ParseUtils.SPACE);
		    }
		    val = ssb.toString();
		}

		int start = extract.getPic().beginIndex();
		sb.replace(start, start + fieldSize, val);

	    } catch (InstantiationException e) {
		String convName = conv != null ? "converter: " + conv.getCanonicalName() + "; " : ParseUtils.EMPTY;
		throw new RecordParserException("An error has occurred while converting value: '" + value + "'; " + convName + "class: " + data.getClass().getCanonicalName() + "; field: " + name + ";", e);
	    } catch (IllegalAccessException e) {
		String convName = conv != null ? "converter: " + conv.getCanonicalName() + "; " : ParseUtils.EMPTY;
		throw new RecordParserException("An error has occurred while converting value: '" + value + "'; " + convName + "class: " + data.getClass().getCanonicalName() + "; field: " + name + ";", e);
	    } catch (ClassCastException e) {
		String convName = conv != null ? "converter: " + conv.getCanonicalName() + "; " : ParseUtils.EMPTY;
		throw new RecordParserException("An error has occurred while converting value: '" + value + "'; " + convName + "class: " + data.getClass().getCanonicalName() + "; field: " + name + ";", e);
	    } catch (Exception e) {
		String convName = conv != null ? "converter: " + conv.getCanonicalName() + "; " : ParseUtils.EMPTY;
		throw new RecordParserException("An error has occurred while converting value: '" + value + "'; " + convName + "class: " + data.getClass().getCanonicalName() + "; field: " + name + ";", e);
	    }

	}

	return sb.toString();
    }

}
