/**
 * 
 */
package com.ivoslabs.records.function;

/**
 * Action to do for each Object, <br>
 * Ej. save in data base
 * 
 * @author www.ivoslabs.com
 *
 */
public interface ObjectConsumer<T> {

    /**
     * Action to do for each Object, <br>
     * Ej. save in data base
     * 
     * @param object
     */
    void process(T object) throws Exception;
}
