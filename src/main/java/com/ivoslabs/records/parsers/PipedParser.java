/**
 * 
 */
package com.ivoslabs.records.parsers;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ivoslabs.records.annontation.PipedField;
import com.ivoslabs.records.converters.Converter;
import com.ivoslabs.records.core.Extractor;
import com.ivoslabs.records.core.RecordParserException;

/**
 * @author www.ivoslabs.com
 *
 */
public class PipedParser {

    /**
     * 
     * @param data
     * @param type
     * @return
     */
    public <T extends Object> T toObject(String data, Class<T> type) {
	return Extractor.convert(data, type, PipedField.class);
    }

    /**
     * 
     * @param data
     * @param type
     * @return
     */
    public <T extends Object> List<T> toObjects(List<String> data, Class<T> type) {
	return Extractor.convert(data, type, PipedField.class);
    }

    /**
     * 
     * @param data
     * @return
     */
    public List<String> toStrings(List<?> data) {
	// TODO

	List<String> ar = new ArrayList<String>();
	for (Object object : data) {
	    ar.add(this.toString(object));
	}
	return ar;
    }

    /**
     * 
     * @param data
     * @return
     */
    @SuppressWarnings("unchecked")
    public String toString(Object data) {

	Field fields[] = data.getClass().getDeclaredFields();

	Map<Integer, String> names = new HashMap<Integer, String>();
	Map<Integer, Object> values = new HashMap<Integer, Object>();
	Map<Integer, Class<? extends Converter<Object>>> converters = new HashMap<Integer, Class<? extends Converter<Object>>>();

	int last = 0;
	for (Field field : fields) {
	    PipedField pipedField = field.getAnnotation(PipedField.class);
	    if (pipedField != null) {
		int idx = pipedField.value();

		if (idx > last) {
		    last = idx;
		}

		field.setAccessible(Boolean.TRUE);

		Object val = null;

		try {
		    val = field.get(data);
		} catch (IllegalArgumentException e) {
		    throw new RecordParserException("An error occurred getting value in class: " + data.getClass().getCanonicalName() + "; field: " + field.getName() + ";", e);
		} catch (IllegalAccessException e) {
		    throw new RecordParserException("An error occurred getting value in class: " + data.getClass().getCanonicalName() + "; field: " + field.getName() + ";", e);
		}

		values.put(idx, val);
		converters.put(idx, (Class<? extends Converter<Object>>) pipedField.converter());
		names.put(idx, field.getName());
	    }
	}

	StringBuilder sb = new StringBuilder();

	for (int idx = 0; idx <= last; idx++) {

	    Object value = values.get(idx);
	    Class<? extends Converter<Object>> conv = converters.get(idx);

	    try {
		sb.append(conv.newInstance().toString(value));
	    } catch (InstantiationException e) {
		throw new RecordParserException("An error occurred converting value: '" + value + "'; converter: " + conv.getCanonicalName() + "; class: " + data.getClass().getCanonicalName() + "; field: " + names.get(idx) + ";", e);
	    } catch (IllegalAccessException e) {
		throw new RecordParserException("An error occurred converting value: '" + value + "'; converter: " + conv.getCanonicalName() + "; class: " + data.getClass().getCanonicalName() + "; field: " + names.get(idx) + ";", e);
	    } catch (ClassCastException e) {
		throw new RecordParserException("An error occurred converting value: '" + value + "'; converter: " + conv.getCanonicalName() + "; class: " + data.getClass().getCanonicalName() + "; field: " + names.get(idx) + ";", e);
	    }

	    if (idx < last) {
		sb.append("|");
	    }
	}

	return sb.toString();
    }

}
