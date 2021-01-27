/**
 *
 */
package com.ivoslabs.records.converters.date;

import java.text.ParseException;
import java.util.Date;

/**
 * Converter to parse Date fields to String and vice versa using the 'MMddyyyy' format <br>
 * <br>
 * <b>Example</b>
 *
 * <pre>
 <code> &#64;Converter(DateUSAConverver.class )
  &#64PipedField(0)
  private Date field1;</code>
 * </pre>
 *
 * @author www.ivoslabs.com
 *
 */
public class DateUSAConverver extends DateConverter {

    /** The date format to use */
    private static final String FORMAT = "MMddyyyy";

    /**
     *
     */
    public DateUSAConverver() {
        super();
    }

    /*
     *
     * (non-Javadoc)
     *
     * @see com.ivoslabs.records.converters.date.DateConverter#toString(java.util.Date, java.lang.String[])
     */
    @Override
    public String toString(Date object, String... args) {
        return super.toString(object, FORMAT);
    }

    /*
     *
     * (non-Javadoc)
     *
     * @see com.ivoslabs.records.converters.date.DateConverter#toObject(java.lang.String, java.lang.String[])
     */
    @Override
    public Date toObject(String string, String... args) throws ParseException {
        return super.toObject(string, FORMAT);
    }
}
