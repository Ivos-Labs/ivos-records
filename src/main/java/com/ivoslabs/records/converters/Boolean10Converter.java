/**
 * 
 */
package com.ivoslabs.records.converters;

/**
 * @author www.ivoslabs.com
 *
 */
public class Boolean10Converter extends BooleanConverter {

    /** The constant true */
    public static final String TRUE = "1";

    /** The constant false */
    public static final String FALSE = "0";

    /**
     * 
     */
    public Boolean10Converter() {
	super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ivoslabs.records.converters.BooleanConverter#toString(java.lang.Boolean, java.lang.String[])
     */
    @Override
    public String toString(Boolean object, String... args) {
	return super.toString(object, TRUE, FALSE);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ivoslabs.records.converters.BooleanConverter#toObject(java.lang.String, java.lang.String[])
     */
    @Override
    public Boolean toObject(String string, String... args) {
	return super.toObject(string, TRUE, FALSE);
    }

}
