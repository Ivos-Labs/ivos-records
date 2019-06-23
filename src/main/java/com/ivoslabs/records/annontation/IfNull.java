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
 * if is used as class annotation indicates a default value for all fields<br>
 * <br>
 * <b>Example 1: </b> Assign a default value for a specific field
 * 
 * <pre>
 * <code> &#64;IfNull(" ")
  &#64PipedField(0)
  private String field1;</code>
 * </pre>
 * 
 * <b>Example 2: </b> Assign a default value for all declared fields
 * 
 * <pre>
 * <code> &#64;IfNull(" ")
  public class PipedDataDTO </code>
 * </pre>
 * 
 * 
 * @author www.ivoslabs.com
 *
 */
@Target({ ElementType.FIELD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface IfNull {

    /**
     * Gets the default value as String when the field is null
     * 
     * @return the converter
     */
    String value();

}
