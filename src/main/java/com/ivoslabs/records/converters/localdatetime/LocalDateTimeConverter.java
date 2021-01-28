/**
 *
 */
package com.ivoslabs.records.converters.localdatetime;

import java.text.ParseException;
import java.time.LocalDateTime;
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
 <code> &#64;Converter(value=LocalDateTimeConverter.class, args="MM-dd-yyyy hh:mm:ss")
  &#64PipedField(0)
  private Date field1;</code>
 * </pre>
 *
 * @author www.ivoslabs.com
 *
 *
 */
public class LocalDateTimeConverter implements FieldConverter<LocalDateTime> {

    /** */
    public LocalDateTimeConverter() {
        super();
    }

    /*
     *
     * (non-Javadoc)
     *
     * @see com.ivoslabs.records.converters.FieldConverter#toString(java.lang.Object, java.lang.String[])
     */
    @Override
    public String toString(LocalDateTime object, String... args) {
        String date = null;
        Validate.notNull(args, "LocalDateTimeConverter requires arguments (pattern date)");
        Validate.isTrue(args.length == ParseUtils.NUM_1 && !args[ParseUtils.NUM_0].isEmpty(), "LocalDateTimeConverter requires one arguments (pattern date)");

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
    public LocalDateTime toObject(String string, String... args) throws ParseException {
        LocalDateTime localDate = null;
        Validate.notNull(args, "LocalDateTimeConverter requires arguments (pattern date)");
        Validate.isTrue(args.length == ParseUtils.NUM_1 && !args[ParseUtils.NUM_0].isEmpty(), "LocalDateTimeConverter requires one arguments (pattern date)");

        if (string != null && !string.trim().isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(args[ParseUtils.NUM_0]);
            localDate = LocalDateTime.parse(string, formatter);
        }

        return localDate;
    }
}
