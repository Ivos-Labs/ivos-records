/**
 * 
 */
package com.ivoslabs.records.converters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Format date YYYYMMDDHHmmss
 * 
 * @author www.ivoslabs.com
 *
 */
public class DateLatinConverver implements FieldConverter<Date> {

    private static final String FORMAT = "yyyyMMddHHmmss";

    /*
     * (non-Javadoc)
     * 
     * @see com.ivoslabs.records.converters.Converter#toString(java.lang.Object)
     */
    public String toString(Date object) {
	String date = null;

	if (object != null) {
	    SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
	    date = sdf.format(object);
	}

	return date;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ivoslabs.records.converters.Converter#toObject(java.lang.String)
     */
    public Date toObject(String string) throws ParseException {
	Date date = null;

	if (string != null && !string.trim().isEmpty()) {
	    SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
	    date = sdf.parse(string);
	}

	return date;
    }

}
