package com.ivoslabs.records.utils;

/**
 * 
 * @author www.ivoslabs.com
 *
 */
public final class MutableCounter {

    /** The counter value */
    private int value;

    /**
     * increments by one the counter value
     */
    public void increment() {
	this.value++;
    }

    /**
     * Gets the value
     * 
     * @return {@code int} the value
     */
    public int getValue() {
	return this.value;
    }

}
