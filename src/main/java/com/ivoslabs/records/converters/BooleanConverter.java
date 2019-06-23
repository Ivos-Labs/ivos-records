/**
 * 
 */
package com.ivoslabs.records.converters;

import com.ivoslabs.records.utils.ParseUtils;

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
	ParseUtils.notNull(args, "BooleanConverter requiere two arguments");
	ParseUtils.notTrue(args.length == 2, "BooleanConverter requiere two arguments");
	ParseUtils.isTrue(args[0].isEmpty(), "BooleanConverter requiere two arguments");

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
	ParseUtils.notNull(args, "BooleanConverter requiere two arguments");
	ParseUtils.notTrue(args.length == 2, "BooleanConverter requiere two arguments");
	ParseUtils.isTrue(args[0].isEmpty(), "BooleanConverter requiere two arguments");

	return string != null && string.equals(args[0]);
    }

}
