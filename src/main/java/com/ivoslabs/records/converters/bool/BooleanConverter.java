/**
 *
 */
package com.ivoslabs.records.converters.bool;

import org.apache.commons.lang3.Validate;

import com.ivoslabs.records.converters.FieldConverter;
import com.ivoslabs.records.utils.ParseUtils;

/**
 * Converter to parse Boolean fields to String and vice versa<br>
 * <br>
 * <b>Example</b>
 *
 * <pre>
 <code> &#64;Converter(value=BooleanConverter.class, args={"trueValue","falseValue"} )
  &#64PipedField(0)
  private Boolean field1;</code>
 * </pre>
 *
 * @author www.ivoslabs.com
 *
 */
public class BooleanConverter implements FieldConverter<Boolean> {

    /**
     *
     */
    public BooleanConverter() {
        super();
    }

    /*
     *
     * (non-Javadoc)
     *
     * @see com.ivoslabs.records.converters.FieldConverter#toString(java.lang.Object, java.lang.String[])
     */
    @Override
    public String toString(Boolean object, String... args) {
        String str;
        Validate.notNull(args, "BooleanConverter requires arguments");
        Validate.isTrue(args.length == ParseUtils.NUM_2 && !args[ParseUtils.NUM_0].isEmpty(), "BooleanConverter requires two arguments");

        if (object != null && object) {
            str = args[ParseUtils.NUM_0];
        } else {
            str = args[ParseUtils.NUM_1];
        }

        return str;
    }

    /*
     *
     * (non-Javadoc)
     *
     * @see com.ivoslabs.records.converters.FieldConverter#toObject(java.lang.String, java.lang.String[])
     */
    @Override
    public Boolean toObject(String string, String... args) {
        Validate.notNull(args, "BooleanConverter requires arguments");
        Validate.isTrue(args.length == ParseUtils.NUM_2 && !args[ParseUtils.NUM_0].isEmpty(), "BooleanConverter requires two arguments");

        return string != null && string.equals(args[ParseUtils.NUM_0]);
    }

}
