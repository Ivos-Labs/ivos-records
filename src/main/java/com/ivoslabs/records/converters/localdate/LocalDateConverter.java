/**
 *
 */
package com.ivoslabs.records.converters.localdate;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.Validate;

import com.ivoslabs.records.converters.FieldConverter;
import com.ivoslabs.records.utils.ParseUtils;

/**
 * Converter to parse Date fields to String and vice versa <br>
 * <br>
 * <b>Example</b>
 *
 * <pre>
 <code> &#64;Converter(value=LocalDateConverter.class, args="MM-dd-yyyy")
  &#64PipedField(0)
  private Date field1;</code>
 * </pre>
 *
 * @author www.ivoslabs.com
 *
 *
 */
public class LocalDateConverter implements FieldConverter<LocalDate> {

    /** */
    public LocalDateConverter() {
        super();
    }

    /*
     *
     * (non-Javadoc)
     *
     * @see com.ivoslabs.records.converters.FieldConverter#toString(java.lang.Object, java.lang.String[])
     */
    @Override
    public String toString(LocalDate object, String... args) {
        String date = null;
        Validate.notNull(args, "LocalDateConverter requires arguments (pattern date)");
        Validate.isTrue(args.length == ParseUtils.NUM_1 && !args[ParseUtils.NUM_0].isEmpty(), "LocalDateConverter requires one arguments (pattern date)");

        if (object != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(args[ParseUtils.NUM_0]);
            date = object.format(formatter);
        }

        return date;
    }

    /*
     *
     * (non-Javadoc)
     *
     * @see com.ivoslabs.records.converters.FieldConverter#toObject(java.lang.String, java.lang.String[])
     */
    @Override
    public LocalDate toObject(String string, String... args) throws ParseException {
        LocalDate localDate = null;
        Validate.notNull(args, "LocalDateConverter requires arguments (pattern date)");
        Validate.isTrue(args.length == ParseUtils.NUM_1 && !args[ParseUtils.NUM_0].isEmpty(), "LocalDateConverter requires one arguments (pattern date)");

        if (string != null && !string.trim().isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(args[ParseUtils.NUM_0]);
            localDate = LocalDate.parse(string, formatter);
        }

        return localDate;
    }
}
