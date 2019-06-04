/**
 * 
 */
package com.ivoslabs.records.core;

import java.lang.reflect.Field;

import com.ivoslabs.records.annontation.Converter;
import com.ivoslabs.records.annontation.Pic;
import com.ivoslabs.records.annontation.PipedField;

/**
 * DTO
 * 
 * @author www.ivoslabs.com
 *
 */
public class FieldParseDTO {

    /** The field */
    private Field field;

    /** The PipedField annotation (Required pipeField or copyField) */
    private PipedField pipeField;

    /** The Pic annotation (Required pipeField or copyField) */
    private Pic pic;

    /** The converter */
    private Converter converter;

    /**
     * Create a new FieldParseDTO to will be used with a COPY file field
     * 
     * @param field
     * @param copyField
     * @param converter
     */
    public FieldParseDTO(Field field, Pic copyField, Converter converter) {
	super();
	this.field = field;
	this.pic = copyField;
	this.converter = converter;
    }

    /**
     * Create a new FieldParseDTO to will be used with a piped file field
     * 
     * @param field
     * @param pipeField
     * @param converter
     */
    public FieldParseDTO(Field field, PipedField pipeField, Converter converter) {
	super();
	this.field = field;
	this.pipeField = pipeField;
	this.converter = converter;
    }

    /**
     * Gets the Field
     *
     * @return {@code Field} The field
     */
    public Field getField() {
	return this.field;
    }

    /**
     * Sets the field
     *
     * @param field {@code Field} The field to set
     */
    public void setField(Field field) {
	this.field = field;
    }

    /**
     * Gets the PipeField
     *
     * @return {@code PipeField} The pipeField
     */
    public PipedField getPipeField() {
	return this.pipeField;
    }

    /**
     * Sets the pipeField
     *
     * @param pipeField {@code PipeField} The pipeField to set
     */
    public void setPipeField(PipedField pipeField) {
	this.pipeField = pipeField;
    }

    /**
     * Gets the pic
     *
     * @return {@code Pic} The pic
     */
    public Pic getPic() {
	return this.pic;
    }

    /**
     * Sets the pic
     *
     * @param pic {@code Pic} The pic to set
     */
    public void setCopyField(Pic pic) {
	this.pic = pic;
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
     * Sets the converter
     *
     * @param converter {@code Converter} The converter to set
     */
    public void setConverter(Converter converter) {
	this.converter = converter;
    }

}
