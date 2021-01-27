/**
 *
 */
package com.ivoslabs.records.converters.localdate;

import java.text.ParseException;
import java.time.LocalDate;

/**
 * Converter to parse Date fields to String and vice versa using the 'MMddyyyyHHmmss' format <br>
 * <br>
 * <b>Example</b>
 *
 * <pre>
 <code> &#64;Converter(DateTimeUSAConverver.class )
  &#64PipedField(0)
  private Date field1;</code>
 * </pre>
 *
 * @author www.ivoslabs.com
 *
 */
public class LocalDateUSAConverver extends LocalDateConverter {

    /** The date format to use */
    private static final String FORMAT = "MMddyyyyH";

    /**
     *
     */
    public LocalDateUSAConverver() {
        super();
    }

    /*
     *
     * (non-Javadoc)
     *
     * @see com.ivoslabs.records.converters.localdate.LocalDateConverter#toString(java.time.LocalDate, java.lang.String[])
     */
    @Override
    public String toString(LocalDate object, String... args) {
        return super.toString(object, FORMAT);
    }

    /*
     *
     * (non-Javadoc)
     *
     * @see com.ivoslabs.records.converters.localdate.LocalDateConverter#toObject(java.lang.String, java.lang.String[])
     */
    @Override
    public LocalDate toObject(String string, String... args) throws ParseException {
        return super.toObject(string, FORMAT);
    }
}
