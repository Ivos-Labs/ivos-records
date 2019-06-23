/**
 * 
 */
package com.ivoslabs.records.annontation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.ivoslabs.records.converters.FieldConverter;

/**
 * Annotation to indicate the Class to be used to convert a String to a field and a field to a String<br>
 * <br>
 * <b>Example</b>
 * 
 * <pre>
 * <code>   &#64;Converter(DateLatinConverver.class)
    &#64;PipedField(0)
    private Date field1;
    
    &#64;Converter(value=DateConverver.class, args="yyyy-MM-dd'T'HH:mm:ss")
    &#64;PipedField(1)
    private Date field2;
    
    &#64;Converter(BooleanYNConverter.class)
    &#64;PipedField(2)
    private Boolean field3;
    
    &#64;Converter(BooleanConverter.class, args={"trueValue","falseValue"})
    &#64;PipedField(2)
    private Boolean field3;</code>
 * </pre>
 * 
 * 
 * @author www.ivoslabs.com
 * @see FieldConverter
 *
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Converter {

    /**
     * Gets the FieldConverter
     * 
     * @return the converter
     */
    Class<? extends FieldConverter<? extends Object>> value();

    /**
     * Gets the arguments
     * 
     * @return arguments to be passed to the converter
     */
    String[] args() default "";
}
