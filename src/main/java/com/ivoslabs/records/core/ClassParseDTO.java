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
 * DTO
 * 
 * @author www.ivoslabs.com
 *
 */
public class ClassParseDTO {

    /** The fieldParseDTOs */
    private List<FieldParseDTO> fieldParseDTOs = new ArrayList<FieldParseDTO>();

    /** The type (pic or pipe) */
    private Type type;

    /** Map of converter classes a their sigleton instance */
    private Map<Class<?>, FieldConverter<?>> fieldConverterMap = new HashMap<Class<?>, FieldConverter<?>>();

    /** Map of Extracts by index (used in piped file) **/
    private Map<Integer, FieldParseDTO> fieldParserDTOMap = new HashMap<Integer, FieldParseDTO>();

    /** Last index */
    private Integer lastIndex;

    /**
     * 
     * @param type
     */
    public ClassParseDTO(Type type) {
	this.type = type;
    }

    /**
     * Gets the FieldConverter
     * 
     * @param clazz type of the required converter
     * @return the converter
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    FieldConverter<?> getConverter(Class<? extends FieldConverter<?>> clazz) throws InstantiationException, IllegalAccessException {
	FieldConverter<?> c = this.fieldConverterMap.get(clazz);

	if (c == null) {
	    c = clazz.newInstance();
	    this.fieldConverterMap.put(clazz, c);
	}
	return c;
    }

    /**
     * Gets the fieldParseDTOs
     * 
     * @return the fieldParseDTOs
     */
    public List<FieldParseDTO> getFieldParseDTOs() {
	return this.fieldParseDTOs;
    }

    /**
     * Appends the specified element to the end of this fieldParseDTOs list
     * 
     * @param fieldParseDTO
     */
    public void add(FieldParseDTO fieldParseDTO) {
	this.fieldParseDTOs.add(fieldParseDTO);
    }

    /**
     * Gets a FieldParseDTO by index (used in piped file)
     * 
     * @return {@code FieldParseDTO} the FieldParseDTO
     */
    public FieldParseDTO getFieldParseDTO(int index) {
	return this.fieldParserDTOMap.get(index);
    }

    /**
     * 
     * 
     * Add a FieldParseDTO to the fieldParserDTOMap (used in piped file)
     *
     * @param key            index of the FieldParseDTO field
     * @param fieldParserDTO the {@code FieldParseDTO} to add
     * 
     */
    public void addFieldParserDTO(int key, FieldParseDTO fieldParserDTO) {
	this.fieldParserDTOMap.put(key, fieldParserDTO);
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
