/**
 * 
 */
package com.ivoslabs.records.annontation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.ivoslabs.records.converters.Converter;
import com.ivoslabs.records.converters.DefalutConverter;

/**
 * 
 * @author www.ivoslabs.com
 *
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Pic {

    /**
     * 
     * @return
     */
    int beginIndex();

    /**
     * 
     * @return
     */
    int size();

    /**
     * 
     * @return
     */
    Class<? extends Converter<? extends Object>> converter() default DefalutConverter.class;

}
