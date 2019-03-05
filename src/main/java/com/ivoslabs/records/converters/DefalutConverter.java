/**
 * 
 */
package com.ivoslabs.records.converters;

/**
 * @author www.ivoslabs.com
 *
 */
public class DefalutConverter implements Converter<Object> {

    /** The empty string. */
    public static final String EMPTY = "";

    /*
     * (non-Javadoc)
     * 
     * @see com.ivoslabs.records.converters.Converter#toString(java.lang.Object)
     */
    public String toString(Object object) {
	return object != null ? object.toString() : EMPTY;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ivoslabs.records.converters.Converter#toObject(java.lang.String)
     */
    public Object toObject(String string) {
	// TODO Auto-generated method stub
	return null;
    }

}
