/**
 *
 */
package com.ivoslabs.records.converters.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.Validate;

import com.ivoslabs.records.converters.FieldConverter;
import com.ivoslabs.records.utils.ParseUtils;

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
     *
     * (non-Javadoc)
     *
     * @see com.ivoslabs.records.converters.FieldConverter#toString(java.lang.Object, java.lang.String[])
     */
    @Override
    public String toString(Date object, String... args) {
        String date = null;
        Validate.notNull(args, "DateConverter requires arguments (pattern date)");
        Validate.isTrue(args.length == ParseUtils.NUM_1 && !args[ParseUtils.NUM_0].isEmpty(), "DateConverter requires one arguments (pattern date)");

        if (object != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(args[0]);
            date = sdf.format(object);
        }

        return date;
    }

    /*
     *
     * (non-Javadoc)
     *
     * @see com.ivoslabs.records.converters.FieldConverter#toObject(java.lang.String, java.lang.String[])
     */
    public Date toObject(String string, String... args) throws ParseException {
        Date date = null;
        Validate.notNull(args, "DateConverter requires arguments (pattern date)");
        Validate.isTrue(args.length == ParseUtils.NUM_1 && !args[ParseUtils.NUM_0].isEmpty(), "DateConverter requires one arguments (pattern date)");

        if (string != null && !string.trim().isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat(args[0]);
            date = sdf.parse(string);
        }

        return date;
    }
}
