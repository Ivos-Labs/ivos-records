/**
 * 
 */
package com.ivoslabs.records.converters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.Validate;

/**
 * Converter to parse Date fields to String and vice versa <br>
 * <br>
 * <b>Example</b>
 * 
 * <pre>
 <code> &#64;Converter(value=DateConverver.class, args="MM-dd-yyyy")
  &#64PipedField(0)
  private Date field1;</code>
 * </pre>
 * 
 * @author www.ivoslabs.com
 * 
 *
 */
public class DateConverter implements FieldConverter<Date> {

    /** */
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
	Validate.notNull(args, "DateConverter requiere one arguments (pattern date)");
	Validate.isTrue(args.length == 1, "DateConverter requiere one arguments (pattern date)");
	Validate.isTrue(!args[0].isEmpty(), "DateConverter requiere one argument  (pattern date)");

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
	Validate.notNull(args, "DateConverter requiere one arguments (pattern date)");
	Validate.isTrue(args.length == 1, "DateConverter requiere one arguments (pattern date)");
	Validate.isTrue(!args[0].isEmpty(), "DateConverter requiere one argument  (pattern date)");

	if (string != null && !string.trim().isEmpty()) {
	    SimpleDateFormat sdf = new SimpleDateFormat(args[0]);
	    date = sdf.parse(string);
	}

	return date;
    }
}
