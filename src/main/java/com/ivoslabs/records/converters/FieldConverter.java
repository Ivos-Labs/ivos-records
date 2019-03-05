/**
 * 
 */
package com.ivoslabs.records.converters;

/**
 * @author www.ivoslabs.com
 *
 */
public interface FieldConverter<E extends Object> {

    /**
     * 
     * @param object
     * @return
     */
    String toString(E object) throws Exception;

    /**
     * 
     * @param string
     * @return
     */
    E toObject(String string) throws Exception;
}
