/**
 * 
 */
package com.ivoslabs.records.converters;

/**
 * Interface to be implemented in converter clases
 * 
 * @param E Required type
 * @author www.ivoslabs.com
 *
 */
public interface FieldConverter<E extends Object> {

    /**
     * Convert a E instance to String using the received arguments
     * 
     * @param object Instance of E to convert
     * @param args   Arguments (Optionals)
     * @return The built String
     */
    String toString(E object, String... args) throws Exception;

    /**
     * Convert a String to E instance using the received arguments
     * 
     * @param string
     * @param args   Arguments (Optionals)
     * @return The built E
     */
    E toObject(String string, String... args) throws Exception;
}
