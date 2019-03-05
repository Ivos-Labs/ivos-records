/**
 * 
 */
package com.ivoslabs.records.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ivoslabs.records.converters.FieldConverter;

/**
 * @author www.ivoslabs.com
 *
 */
public class Template {

    private List<Extract> extracts = new ArrayList<Extract>();

    private Type type;

    private Map<Class<?>, FieldConverter<?>> map = new HashMap<Class<?>, FieldConverter<?>>();

    // for piped
    private Map<Integer, Extract> extractMap = new HashMap<Integer, Extract>();

    private Integer lastIndex;

    public enum Type {
	PIC, PIPE
    }

    public Template(Type type) {
	this.type = type;
    }

    void addConverter(FieldConverter<?> converter) {
	this.map.put(converter.getClass(), converter);
    }

    FieldConverter<?> getConverter(Class<? extends FieldConverter<?>> clazz) {
	return this.map.get(clazz);
    }

    public List<Extract> getExtracts() {
	return this.extracts;
    }

    public void add(Extract extract) {
	this.extracts.add(extract);
    }

    /**
     * Gets the extractMap
     * 
     * @return {@code Map<Integer,Extract>} the extractMap
     */
    public Map<Integer, Extract> getExtractMap() {
	return extractMap;
    }

    /**
     * Sets the extractMap
     *
     * @param extractMap {@code Map<Integer,Extract>} the extractMap to set
     */
    public void addExtractMap(int key, Extract extract) {
	this.extractMap.put(key, extract);
    }

    /**
     * Gets the lastIndex
     * 
     * @return {@code Integer} the lastIndex
     */
    public Integer getLastIndex() {
	return lastIndex;
    }

    /**
     * Sets the lastIndex
     *
     * @param lastIndex {@code Integer} the lastIndex to set
     */
    public void setLastIndex(Integer lastIndex) {
	this.lastIndex = lastIndex;
    }

    /**
     * Gets the Type
     *
     * @return {@code Type} The type
     */
    public Type getType() {
	return this.type;
    }

}
