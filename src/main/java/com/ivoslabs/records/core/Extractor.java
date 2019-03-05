/**
 * 
 */
package com.ivoslabs.records.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.ivoslabs.records.annontation.Pic;
import com.ivoslabs.records.annontation.PipedField;
import com.ivoslabs.records.converters.Converter;
import com.ivoslabs.records.converters.DefalutConverter;
import com.ivoslabs.records.core.Template.Type;

/**
 * @author www.ivoslabs.com
 *
 */
public class Extractor {

    /** The constant true (1) */
    private static final String TRUE_1 = "1";

    /** The constant true (Y) */
    private static final String TRUE_Y = "Y";

    /** The constant true (T) */
    private static final String TRUE_T = "T";

    /** The constant pipe separator */
    private static final String PIPE_SEPARATOR = "\\|";

    /**
     * 
     * @param data
     * @param type
     * @param annon
     * @return
     */
    public static <T extends Object> T convert(String data, Class<T> type, Class<? extends Annotation> annon) {
	T obj;
	Template extracts = Extractor.getExtract(type, annon);
	obj = Extractor.convert(data, type, extracts, null);

	return obj;
    }

    /**
     * 
     * @param data
     * @param type
     * @param annon
     * @return
     */
    public static <T extends Object> List<T> convert(List<String> data, Class<T> type, Class<? extends Annotation> annon) {

	List<T> list = new ArrayList<T>();

	int rowNum = 0;
	Template extracts = Extractor.getExtract(type, annon);

	for (String row : data) {
	    rowNum++;

	    T object;

	    object = Extractor.convert(row, type, extracts, rowNum);

	    list.add(object);
	}

	return list;

    }

    /**
     * 
     * @param type
     * @param annon
     * @return
     */
    private static Template getExtract(Class<?> type, Class<? extends Annotation> annon) {

	Type t = annon.equals(PipedField.class) ? Type.PIPE : Type.PIC;

	Template template = new Template(t);

	Field fields[] = type.getDeclaredFields();

	for (Field field : fields) {
	    Extract ex = null;

	    if (annon.equals(PipedField.class)) {
		PipedField pf = field.getAnnotation(PipedField.class);
		if (pf != null) {
		    ex = new Extract(field, pf);
		}
	    } else {
		Pic pic = field.getAnnotation(Pic.class);
		if (pic != null) {
		    ex = new Extract(field, pic);
		}
	    }

	    if (ex != null) {
		template.add(ex);
	    }
	}
	return template;

    }

    /**
     * 
     * @param data
     * @param type
     * @param extracts
     * @param rowNum
     * @return
     */
    @SuppressWarnings("unchecked")
    private static <T extends Object> T convert(String data, Class<T> type, Template extracts, Integer rowNum) {

	T object;

	String values[] = null;

	if (extracts.getType().equals(Template.Type.PIPE)) {
	    values = data.split(PIPE_SEPARATOR, -1);
	}

	try {
	    object = type.newInstance();
	} catch (IllegalAccessException e) {
	    throw new RuntimeException(e);
	} catch (InstantiationException e) {
	    throw new RuntimeException(e);
	}

	for (Extract ex : extracts.getExtracts()) {

	    Field field = ex.getField();
	    PipedField pf = ex.getPipeField();
	    Pic pic = ex.getCopyField();

	    if (pf != null || pic != null) {
		String fieldName = field.getName();
		String value = null;
		Object v = null;
		Class<? extends Converter<Object>> clazzConv = null;

		if (extracts.getType().equals(Template.Type.PIPE) && pf != null) {
		    int indx = pf.value();
		    value = values[indx];
		    clazzConv = (Class<? extends Converter<Object>>) pf.converter();
		} else if (extracts.getType().equals(Template.Type.PIC) && pic != null) {
		    int begin = pic.beginIndex();
		    int size = pic.size();
		    value = data.substring(begin, begin + size);
		    clazzConv = (Class<? extends Converter<Object>>) pic.converter();
		}

		try {

		    if (value != null) {
			field.setAccessible(Boolean.TRUE);
			// TODO save converters new instances

			if (clazzConv.equals(DefalutConverter.class)) {
			    clazzConv = null;
			    v = Extractor.parse(value, field.getType());
			} else {
			    v = clazzConv.newInstance().toObject(value);
			}

			field.set(object, v);
		    }

		} catch (Exception e) {
		    String row = rowNum != null ? "at row " + rowNum : "";
		    String conv = clazzConv != null ? " converter: " + clazzConv.getCanonicalName() + "; " : "";
		    throw new RecordParserException("An error occurred setting value, original value: " + value + "; converted value: " + v + "; " + conv + " class: " + type.getCanonicalName() + "; " + "field: " + fieldName + "; " + row, e);
		}

	    }
	}

	return object;
    }

    /**
     * 
     * @param value
     * @param type
     * @return
     */
    private static Object parse(String value, Class<?> type) {
	Object v = null;

	if (type == int.class) {
	    v = Integer.parseInt(value);
	} else if (type == double.class) {
	    v = Double.parseDouble(value);
	} else if (type == float.class) {
	    v = Float.parseFloat(value);
	} else if (type == boolean.class) {
	    v = value.equals(TRUE_1) || value.equals(TRUE_T) || value.equals(TRUE_Y);
	    if (v.equals(Boolean.FALSE)) {
		v = Boolean.parseBoolean(value);
	    }
	} else if (type.equals(Integer.class)) {
	    if (!value.isEmpty()) {
		v = Integer.parseInt(value);
	    }
	} else if (type.equals(Double.class)) {
	    if (!value.isEmpty()) {
		v = Double.parseDouble(value);
	    }
	} else if (type.equals(Float.class)) {
	    if (!value.isEmpty()) {
		v = Float.parseFloat(value);
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

}
