/**
 * 
 */
package com.ivoslabs.records.annontation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to indicate that the field can be handle as a pic in a copy file
 * 
 * @author www.ivoslabs.com
 *
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Pic {

    /**
     * Gets the index where stats the field in copy file
     * 
     * @return
     */
    int beginIndex();

    /**
     * Gets the size of the field in copy file
     * 
     * @return
     */
    int size();

}
