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
public interface ActionObj<T> {

    void event(T object);
}
