/**
 * 
 */
package com.ivoslabs.records.converters;

import org.apache.commons.lang3.Validate;

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
     * (non-Javadoc)
     * 
     * @see com.ivoslabs.records.converters.FieldConverter#toString(java.lang.Object, java.lang.String[])
     */

    public String toString(Boolean object, String... args) {
	String str;
	Validate.notNull(args, "BooleanConverter requires two arguments");
	Validate.isTrue(args.length == 2, "BooleanConverter requires two arguments");
	Validate.isTrue(!args[0].isEmpty(), "BooleanConverter requires two arguments");

	if (object != null && object) {
	    str = args[0];
	} else {
	    str = args[1];
	}

	return str;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ivoslabs.records.converters.FieldConverter#toObject(java.lang.String, java.lang.String[])
     */
    public Boolean toObject(String string, String... args) {
	Validate.notNull(args, "BooleanConverter requires two arguments");
	Validate.isTrue(args.length == 2, "BooleanConverter requires two arguments");
	Validate.isTrue(!args[0].isEmpty(), "BooleanConverter requires two arguments");

	return string != null && string.equals(args[0]);
    }

}
