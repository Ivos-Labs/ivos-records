/**
 * 
 */
package com.ivoslabs.records.converters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ivoslabs.records.utils.ParseUtils;

/**
 * @author www.ivos.mx
 *
 */
public class DateConverter implements FieldConverter<Date> {

    /**
     * 
     */
    public DateConverter() {
	super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ivoslabs.records.converters.FieldConverter#toString(java.lang.Object, java.lang.String[])
     */

    public String toString(Date object, String... args) {
	String date = null;
	ParseUtils.notNull(args, "DateConverter requiere one arguments (pattern date)");
	ParseUtils.notTrue(args.length == 1, "DateConverter requiere one arguments (pattern date)");
	ParseUtils.isTrue(args[0].isEmpty(), "DateConverter requiere one arguments (pattern date)");

	if (object != null) {
	    SimpleDateFormat sdf = new SimpleDateFormat(args[0]);
	    date = sdf.format(object);
	}

	return date;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ivoslabs.records.converters.FieldConverter#toObject(java.lang.String, java.lang.String[])
     */
    public Date toObject(String string, String... args) throws ParseException {
	Date date = null;
	ParseUtils.notNull(args, "DateConverter requiere one arguments (pattern date)");
	ParseUtils.notTrue(args.length == 1, "DateConverter requiere one arguments (pattern date)");
	ParseUtils.isTrue(args[0].isEmpty(), "DateConverter requiere one arguments (pattern date)");

	if (string != null && !string.trim().isEmpty()) {
	    SimpleDateFormat sdf = new SimpleDateFormat(args[0]);
	    date = sdf.parse(string);
	}

	return date;
    }
}
