/**
 * 
 */
package com.ivoslabs.records.converters;

/**
 * Converter to parse Boolean fields to String and vice versa using the 'true' String as the true value and the 'false' String as the false value<br>
 * <br>
 * <b>Example</b>
 * 
 * <pre>
 <code> &#64;Converter(BooleanDefConverter.class )
  &#64PipedField(0)
  private Boolean field1;</code>
 * </pre>
 * 
 * @author www.ivoslabs.com
 *
 */
public class BooleanDefConverter extends BooleanConverter {

    /** The constant true */
    public static final String TRUE = "true";

    /** The constant false */
    public static final String FALSE = "false";

    /**
     * 
     */
    public BooleanDefConverter() {
	super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ivoslabs.records.converters.BooleanConverter#toString(java.lang.Boolean, java.lang.String[])
     */
    @Override
    public String toString(Boolean object, String... args) {
	return super.toString(object, TRUE, FALSE);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ivoslabs.records.converters.BooleanConverter#toObject(java.lang.String, java.lang.String[])
     */
    @Override
    public Boolean toObject(String string, String... args) {
	return super.toObject(string, TRUE, FALSE);
    }
}
