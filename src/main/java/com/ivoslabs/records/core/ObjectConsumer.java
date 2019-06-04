/**
 * 
 */
package com.ivoslabs.records.core;

/**
 * Action to do for each Object, ej. save in data base
 * 
 * @author www.ivoslabs.com
 *
 */
public interface ObjectConsumer<T> {

    /**
     * 
     * @param object
     */
    void process(T object) throws Exception;
}
