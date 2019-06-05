package com.ivoslabs.records.core;

/**
 * Interface to be implemented to get a String in Piped format or COPY format from an Object<br>
 * Used to append lines into a file
 * 
 * @author www.ivoslabs.com
 *
 */
public interface RowSuplier<T> {

    /**
     * Gets a String in Piped format or COPY format from an Object
     * 
     * @param object Instance to be parsed
     * @return formed String
     */
    String get(T object);
}
