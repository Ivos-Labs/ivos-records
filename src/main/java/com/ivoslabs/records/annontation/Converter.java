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
 * <code>
    
    &#64;Converter(DateLatinConverver.class)
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
    private Boolean field3;
    
    </code>
 * </pre>
 * 
 * <br>
 * <b>Custom converter</b>
 * 
 * <pre>
 * <code>
 public class UserConverter implements FieldConverter&lt;Integer> {

 
    public UserConverter() {
	super();
    }

 
    public String toString(Integer object, String... args) {
	String str;
	ParseUtils.notNull(args, "BooleanConverter requiere two arguments");
	ParseUtils.notTrue(args.length == 2, "BooleanConverter requiere two arguments");
	ParseUtils.isTrue(args[0].isEmpty(), "BooleanConverter requiere two arguments");

	if (object instanceof Boolean && ((Boolean) object)) {
	    str = args[0];
	} else {
	    str = args[1];
	}

	return str;
    }

 
    public Integer toObject(String string, String... args) {
	ParseUtils.notNull(args, "BooleanConverter requiere two arguments");
	ParseUtils.notTrue(args.length == 2, "BooleanConverter requiere two arguments");
	ParseUtils.isTrue(args[0].isEmpty(), "BooleanConverter requiere two arguments");

	return string != null && string.equals(args[0]);
    }</code>
 * </pre>
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
