/**
 * 
 */
package com.ivoslabs.records.annontation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to indicate that the field can be handle as a field in a piped file<br>
 * Specify the index where will be found the field in a row separated by pipe <br>
 * <br>
 * <b>Example</b>
 * 
 * <pre>
 * <code>   // text field
    &#64;PipedField(0)
    private String field1;
    
    // number field
    &#64;PipedField(1)
    private Integer field2;
    
    // date field (or whatever that require a converter)
    &#64;Converter(DateLatinConverver.class)
    &#64;PipedField(3)
    private Date field3; </code>
 * </pre>
 * 
 * @author www.ivoslabs.com
 * @see com.ivoslabs.records.annontation.Piped#value() &#64;PipedField#value
 * @see com.ivoslabs.records.annontation.Converter Converter
 * @since 1.0.0
 * @author imperezivan
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Piped {

    /**
     * Specifies the index where will be found the field in row separated by pipe
     * 
     * @return The index where will be found the field
     * @see com.ivoslabs.records.annontation.Piped &#64;PipedField
     * @see com.ivoslabs.records.annontation.Converter Converter
     * @since 1.0.0
     * @author imperezivan
     */
    int value();

    /**
     * Specifies the max size of the field as String
     * 
     * @return The max size
     * @since 1.0.0
     * @author imperezivan
     */
    int maxSize() default 0;

    /**
     * Specifies whether the max size has to be used as the fixed size of the field,<br>
     * if the max size is 0, the fixed sized flag will be ignored
     * 
     * @return true when the max size has to be used as the fixed size of the field
     * @since 1.0.0
     * @author imperezivan
     *
     */
    boolean fixedSize() default false;

    /**
     * Specifies whether the value has to be aligned to the right side,<br>
     * if the fixed size flag is false, the right align flag will be ignored
     * 
     * @return true when the value has to be aligned to the right side
     * @since 1.0.0
     * @author imperezivan
     *
     */
    boolean rightAlign() default false;

}
