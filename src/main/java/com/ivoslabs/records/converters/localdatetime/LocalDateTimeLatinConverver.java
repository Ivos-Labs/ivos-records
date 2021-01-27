/**
 *
 */
package com.ivoslabs.records.converters.localdatetime;

import java.text.ParseException;
import java.time.LocalDateTime;

/**
 * Converter to parse Date fields to String and vice versa using the 'yyyyMMddHHmmss' format <br>
 * <br>
 * <b>Example</b>
 *
 * <pre>
 <code> &#64;Converter(DateTimeLatinConverver.class )
  &#64PipedField(0)
  private Date field1;</code>
 * </pre>
 *
 * @author www.ivoslabs.com
 *
 */
public class LocalDateTimeLatinConverver extends LocalDateTimeConverter {

    /** The date format to use */
    private static final String FORMAT = "yyyyMMddHHmmss";

    /**
     *
     */
    public LocalDateTimeLatinConverver() {
        super();
    }

    /*
     *
     * (non-Javadoc)
     *
     * @see com.ivoslabs.records.converters.localdatetime.LocalDateTimeConverter#toString(java.time.LocalDateTime, java.lang.String[])
     */
    @Override
    public String toString(LocalDateTime object, String... args) {
        return super.toString(object, FORMAT);
    }

    /*
     *
     * (non-Javadoc)
     *
     * @see com.ivoslabs.records.converters.localdatetime.LocalDateTimeConverter#toObject(java.lang.String, java.lang.String[])
     */
    @Override
    public LocalDateTime toObject(String string, String... args) throws ParseException {
        return super.toObject(string, FORMAT);
    }

}
