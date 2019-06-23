/**
 * 
 */
package com.ivoslabs.records.converters;

/**
 * Converter to parse Boolean fields to String and vice versa using the 'Y' String as the true value and the 'N' String as the false value<br>
 * <br>
 * <b>Example</b>
 * 
 * <pre>
 <code> &#64;Converter(BooleanYNConverter.class )
  &#64PipedField(0)
  private Boolean field1;</code>
 * </pre>
 * 
 * @author www.ivoslabs.com
 *
 */
public class BooleanYNConverter extends BooleanConverter {

    /** The constant true */
    public static final String TRUE = "Y";

    /** The constant false */
    public static final String FALSE = "N";

    /**
     * 
     */
    public BooleanYNConverter() {
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
