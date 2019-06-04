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

    /** */
    private List<FieldParseDTO> extracts = new ArrayList<FieldParseDTO>();

    /** The type (pic or pipe) */
    private Type type;

    /** Map of converter classes a sigleton instance */
    private Map<Class<?>, FieldConverter<?>> map = new HashMap<Class<?>, FieldConverter<?>>();

    /** Map of Extracts by index (used in piped file) **/
    private Map<Integer, FieldParseDTO> extractMap = new HashMap<Integer, FieldParseDTO>();

    /** Last index */
    private Integer lastIndex;

    /**
     * 
     * @author
     *
     */
    public enum Type {
	PIC, PIPE
    }

    /**
     * 
     * @param type
     */
    public Template(Type type) {
	this.type = type;
    }

    /**
     * 
     * @param converter
     */
    void addConverter(FieldConverter<?> converter) {
	this.map.put(converter.getClass(), converter);
    }

    /**
     * 
     * @param clazz
     * @return
     */
    FieldConverter<?> getConverter(Class<? extends FieldConverter<?>> clazz) {
	return this.map.get(clazz);
    }

    /**
     * 
     * @return
     */
    public List<FieldParseDTO> getExtracts() {
	return this.extracts;
    }

    /**
     * 
     * @param extract
     */
    public void add(FieldParseDTO extract) {
	this.extracts.add(extract);
    }

    /**
     * Gets the extractMap
     * 
     * @return {@code Map<Integer,Extract>} the extractMap
     */
    public Map<Integer, FieldParseDTO> getExtractMap() {
	return extractMap;
    }

    /**
     * Sets the extractMap
     *
     * @param extractMap {@code Map<Integer,Extract>} the extractMap to set
     */
    public void addExtractMap(int key, FieldParseDTO extract) {
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
