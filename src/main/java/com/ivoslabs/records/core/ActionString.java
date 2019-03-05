package com.ivoslabs.records.core;

/**
 * Action to do for each String, ej. append to a file
 * 
 * @author www.ivoslabs.com
 *
 */
public interface ActionString<T extends Object> {

    String event(T row);
}
