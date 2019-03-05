/**
 * 
 */
package com.ivoslabs.records.core;

import java.lang.reflect.Field;

import com.ivoslabs.records.annontation.Converter;
import com.ivoslabs.records.annontation.Pic;
import com.ivoslabs.records.annontation.PipedField;

/**
 * @author www.ivoslabs.com
 *
 */
public class Extract {

    /** */
    private Field field;

    /** */
    private PipedField pipeField;

    /** */
    private Pic copyField;

    /** */
    private Converter converter;

    /**
     * @param field
     * @param copyField
     */
    public Extract(Field field, Pic copyField, Converter converter) {
	super();
	this.field = field;
	this.copyField = copyField;
	this.converter = converter;
    }

    /**
     * @param field
     * @param pipeField
     */
    public Extract(Field field, PipedField pipeField, Converter converter) {
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
     * Gets the Copy
     *
     * @return {@code Copy} The copyField
     */
    public Pic getCopyField() {
	return this.copyField;
    }

    /**
     * Sets the copyField
     *
     * @param copyField {@code Copy} The copyField to set
     */
    public void setCopyField(Pic copyField) {
	this.copyField = copyField;
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
