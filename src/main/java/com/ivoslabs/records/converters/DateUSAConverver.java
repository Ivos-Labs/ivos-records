/**
 * 
 */
package com.ivoslabs.records.converters;

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

    /** */
    private static final String FORMAT = "MMddyyyy";

    /**
     * 
     */
    public DateUSAConverver() {
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
