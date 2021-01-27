/**
 * 
 */
package com.ivoslabs.records.dtos;

import java.lang.reflect.Field;

import com.ivoslabs.records.annontation.Converter;
import com.ivoslabs.records.annontation.Piped;

/**
 * @author www.ivoslabs.com
 *
 */
public class PipedField extends BaseField {

    /** The PipedField annotation */
    private Piped pipeField;

    /** The maxSize (piped field) */
    private Integer maxSize;

    /**
     * Create a new FieldParseDTO to will be used with a piped file field
     * 
     * @param field
     * @param pipeField
     * @param converter
     */
    public PipedField(Field field, Piped pipeField, Converter converter, String ifNull, Integer maxSize) {
        super(field, converter, ifNull);
        this.pipeField = pipeField;
        this.maxSize = maxSize;
    }

    /**
     * Gets the pipeField
     * 
     * @return {@code Piped} The pipeField
     */
    public Piped getPipeField() {
        return this.pipeField;
    }

    /**
     * Gets the maxSize
     * 
     * @return {@code Integer} The maxSize
     */
    public Integer getMaxSize() {
        return this.maxSize;
    }

}
