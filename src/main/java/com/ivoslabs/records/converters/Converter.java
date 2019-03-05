/**
 * 
 */
package com.ivoslabs.records.converters;

/**
 * @author www.ivoslabs.com
 *
 */
public interface Converter<E extends Object> {

    /**
     * 
     * @param object
     * @return
     */
    String toString(E object);

    /**
     * 
     * @param string
     * @return
     */
    E toObject(String string);
}
