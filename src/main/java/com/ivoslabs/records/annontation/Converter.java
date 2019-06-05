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
 * Annotation to indicate the Class to be used to convert a String to a field and a field to a String
 * 
 * @author www.ivoslabs.com
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
