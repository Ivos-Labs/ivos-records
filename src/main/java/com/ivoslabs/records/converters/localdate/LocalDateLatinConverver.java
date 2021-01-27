/**
 *
 */
package com.ivoslabs.records.converters.localdate;

import java.text.ParseException;
import java.time.LocalDate;

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
public class LocalDateLatinConverver extends LocalDateConverter {

    /** The date format to use */
    private static final String FORMAT = "yyyyMMdd";

    /**
     *
     */
    public LocalDateLatinConverver() {
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
