/**
 * 
 */
package com.ivoslabs.records.dtos;

import java.lang.reflect.Field;

import com.ivoslabs.records.annontation.Converter;
import com.ivoslabs.records.annontation.Pic;

/**
 * @author www.ivoslabs.com
 *
 */
public class CopyField extends BaseField {

    /** The PipedField annotation */
    private Pic pic;

    /**
     * Create a new FieldParseDTO to will be used with a piped file field
     * 
     * @param field
     * @param pipeField
     * @param converter
     */
    public CopyField(Field field, Pic pic, Converter converter, String ifNull) {
	super(field, converter, ifNull);
	this.pic = pic;
    }

    /**
     * Gets the pic
     * 
     * @return {@code Pic} The pic
     */
    public Pic getPic() {
	return this.pic;
    }

}
