/**
 * 
 */
package com.ivoslabs.records.tests.commons.dtos;

import com.ivoslabs.records.converters.FieldConverter;

/**
 * @author www.ivoslabs.com
 *
 */
public class SubConverter implements FieldConverter<SubField> {

    /*
     * (non-Javadoc)
     * 
     * @see com.ivoslabs.records.converters.FieldConverter#toString(java.lang.Object, java.lang.String[])
     */
    public String toString(SubField object, String... args) throws Exception {
	return object.getA() + "," + object.getB();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ivoslabs.records.converters.FieldConverter#toObject(java.lang.String, java.lang.String[])
     */
    public SubField toObject(String string, String... args) throws Exception {
	return new SubField(string.split(",")[0], string.split(",")[1]);
    }

}
