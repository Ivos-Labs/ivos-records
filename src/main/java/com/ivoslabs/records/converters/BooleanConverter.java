/**
 * 
 */
package com.ivoslabs.records.converters;

import org.apache.commons.lang3.Validate;

/**
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
	Validate.notNull(args, "BooleanConverter requiere two arguments");
	Validate.isTrue(args.length == 2, "BooleanConverter requiere two arguments");
	Validate.isTrue(!args[0].isEmpty(), "BooleanConverter requiere two arguments");

	if (object instanceof Boolean && ((Boolean) object)) {
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
	Validate.notNull(args, "BooleanConverter requiere two arguments");
	Validate.isTrue(args.length == 2, "BooleanConverter requiere two arguments");
	Validate.isTrue(!args[0].isEmpty(), "BooleanConverter requiere two arguments");

	return string != null && string.equals(args[0]);
    }

}
