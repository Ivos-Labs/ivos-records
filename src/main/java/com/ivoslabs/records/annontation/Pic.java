/**
 * 
 */
package com.ivoslabs.records.annontation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to indicate that the field can be handle as a pic in a copy file <br>
 * <br>
 * <b>Example</b>
 * 
 * <pre>
 * <code>   // text field
    &#64;Pic(beginIndex = 0, size = 7)
    private String field1;
    
    // number fielf
    &#64;Pic(beginIndex = 7, size = 3)
    private Integer field2;
    
    // date field (or whatever that require a converter)
    &#64;Converter(DateLatinConverver.class)
    &#64;Pic(beginIndex = 10, size = 14)
    private Date field3;</code>
 * </pre>
 * 
 * @author www.ivoslabs.com
 * @see com.ivoslabs.records.annontation.Pic#beginIndex() &#64;Pic#beginIndex
 * @see com.ivoslabs.records.annontation.Pic#size() &#64;Pic#size
 * @see com.ivoslabs.records.annontation.Converter Converter
 * 
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Pic {

    /**
     * Gets the index where stats the field in copy file
     * 
     * @return The begin index
     * 
     * @see com.ivoslabs.records.annontation.Pic &#64;Pic
     * @see com.ivoslabs.records.annontation.Pic#size() &#64;Pic#size
     * @see com.ivoslabs.records.annontation.Converter Converter
     */
    int beginIndex();

    /**
     * Gets the size of the field in copy file
     * 
     * @return The field size
     * @see com.ivoslabs.records.annontation.Pic &#64;Pic
     * @see com.ivoslabs.records.annontation.Pic#beginIndex() &#64;Pic#beginIndex
     * @see com.ivoslabs.records.annontation.Converter Converter
     */
    int size();

    /**
     * Specifies whether the value has to be aligned to the right side,<br>
     * 
     * @return true when the value has to be aligned to the right side
     * @since 1.0.0
     * @author imperezivan
     *
     */
    boolean rightAlign() default false;

}
