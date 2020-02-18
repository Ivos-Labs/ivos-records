/**
 * 
 */
package com.ivoslabs.records.dtos;

import java.lang.reflect.Field;

import com.ivoslabs.records.annontation.Converter;

/**
 * DTO
 * 
 * @author www.ivoslabs.com
 *
 */
public abstract class BaseField {

    /** The field */
    private Field field;

    /** The converter */
    private Converter converter;

    /** The if-null */
    private String ifNull;

    /**
     * Create a new FieldParseDTO to will be used with a COPY file field
     * 
     * @param field
     * @param copyField
     * @param converter
     */
    public BaseField(Field field, Converter converter, String ifNull) {
	super();
	this.field = field;
	this.converter = converter;
	this.ifNull = ifNull;
    }

    /**
     * Gets the field
     * 
     * @return {@code Field} The field
     */
    public Field getField() {
	return this.field;
    }

    /**
     * Gets the converter
     * 
     * @return {@code Converter} The converter
     */
    public Converter getConverter() {
	return this.converter;
    }

    /**
     * Gets the ifNull
     * 
     * @return {@code String} The ifNull
     */
    public String getIfNull() {
	return this.ifNull;
    }

}
