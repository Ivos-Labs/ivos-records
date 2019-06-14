/**
 * 
 */
package com.ivoslabs.records.converters;

import java.text.ParseException;
import java.util.Date;

/**
 * Format date YYYYMMDD
 * 
 * @author www.ivoslabs.com
 *
 */
public class DateLatinConverver extends DateConverter {

    /** */
    private static final String FORMAT = "YYYYMMDD";

    /**
     * 
     */
    public DateLatinConverver() {
	super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ivoslabs.records.converters.Converter#toString(java.lang.Object)
     */
    @Override
    public String toString(Date object, String... args) {
	return super.toString(object, FORMAT);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ivoslabs.records.converters.Converter#toObject(java.lang.String)
     */
    @Override
    public Date toObject(String string, String... args) throws ParseException {
	return super.toObject(string, FORMAT);
    }

}
