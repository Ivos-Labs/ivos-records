/**
 *
 */
package com.ivoslabs.records.converters.bool;

/**
 * Converter to parse Boolean fields to String and vice versa using the 'T' String as the true value and the 'F' String as the false value<br>
 * <br>
 * <b>Example</b>
 *
 * <pre>
 <code> &#64;Converter(BooleanTFConverter.class )
  &#64PipedField(0)
  private Boolean field1;</code>
 * </pre>
 *
 * @author www.ivoslabs.com
 *
 */
public class BooleanTFConverter extends BooleanConverter {

    /** The constant true */
    public static final String TRUE = "T";

    /** The constant false */
    public static final String FALSE = "F";

    /**
     *
     */
    public BooleanTFConverter() {
        super();
    }

    /*
     *
     * (non-Javadoc)
     *
     * @see com.ivoslabs.records.converters.bool.BooleanConverter#toString(java.lang.Boolean, java.lang.String[])
     */
    @Override
    public String toString(Boolean object, String... args) {
        return super.toString(object, TRUE, FALSE);
    }

    /*
     *
     * (non-Javadoc)
     *
     * @see com.ivoslabs.records.converters.bool.BooleanConverter#toObject(java.lang.String, java.lang.String[])
     */
    @Override
    public Boolean toObject(String string, String... args) {
        return super.toObject(string, TRUE, FALSE);
    }

}
