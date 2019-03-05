/**
 * 
 */
package com.ivoslabs.records.converters;

/**
 * @author www.ivoslabs.com
 *
 */
public class Boolean10Converter implements FieldConverter<Boolean> {

    /** The constant true */
    public static final String TRUE = "1";

    /** The constant false */
    public static final String FALSE = "0";

    /*
     * (non-Javadoc)
     * 
     * @see com.ivoslabs.records.converters.Converter#toString(java.lang.Object)
     */
    public String toString(Boolean object) {

	String str;

	if (object instanceof Boolean && ((Boolean) object)) {
	    str = TRUE;
	} else {
	    str = FALSE;
	}

	return str;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ivoslabs.records.converters.Converter#toObject(java.lang.String)
     */
    public Boolean toObject(String string) {
	return string != null && string.equals(TRUE);
    }

}
