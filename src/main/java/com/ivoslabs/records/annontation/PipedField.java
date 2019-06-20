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
 * <code>
    // text field
    &#64;PipedField(0)
    private String field1;
    
    // number field
    &#64;PipedField(1)
    private Integer field2;
    
    // date field (or whatever that require a converter)
    &#64;Converter(DateLatinConverver.class)
    &#64;PipedField(3)
    private Date field3;
    
    </code>
 * </pre>
 * 
 * @author www.ivoslabs.com
 * @see com.ivoslabs.records.annontation.PipedField#value() PipedField#value
 * @see com.ivoslabs.records.annontation.Converter Converter
 *
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PipedField {

    /**
     * Specify the index where will be found the field in row separated by pipe
     * 
     * @return The index where will be found the field
     * @see com.ivoslabs.records.annontation.PipedField PipedField
     * @see com.ivoslabs.records.annontation.Converter Converter
     * 
     */
    int value();
     

    /**
     * Specify the max size of the field as String
     * 
     * @return The max size
     */
    int maxSize() default 0;

}
