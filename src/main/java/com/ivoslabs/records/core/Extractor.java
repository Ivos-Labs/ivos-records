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
import com.ivoslabs.records.core.Template.Type;

/**
 * @author www.ivoslabs.com
 *
 */
public class Extractor {
    
    /** The constant true (1) */
    private static final String TRUE = "1";

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
		
		try {
		    
		    if (extracts.getType().equals(Template.Type.PIPE) && pf != null) {
			int indx = pf.value();
			value = values[indx];
		    } else if (extracts.getType().equals(Template.Type.PIC) && pic != null) {
			int begin = pic.beginIndex();
			int size = pic.size();
			value = data.substring(begin, begin + size);
		    }

		    if (value != null) {
			field.setAccessible(Boolean.TRUE);
			Object v = Extractor.parse(value, field.getType());
			field.set(object, v);
		    }
		    
		} catch (Exception e) {
		    throw new RuntimeException("Error extracting " + fieldName + " " + rowNum != null ? "at row " + rowNum : "", e);
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
	    v = value.equals(TRUE);
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
		v = value.equals(TRUE);
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
