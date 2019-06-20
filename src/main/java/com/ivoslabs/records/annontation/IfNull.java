/**
 * 
 */
package com.ivoslabs.records.annontation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to indicate the default value when the field is null<br>
 * <br>
 * <b>Example</b>
 * 
 * <pre>
 * <code> &#64;IfNull(" ")
  &#64PipedField(0)
  private String field1;
 </code>
 * </pre>
 * 
 * 
 * @author www.ivoslabs.com
 *
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface IfNull {

    /**
     * Gets the default value as String when the field is null
     * 
     * @return the converter
     */
    String value();

}
