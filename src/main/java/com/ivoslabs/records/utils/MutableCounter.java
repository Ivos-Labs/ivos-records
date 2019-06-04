package com.ivoslabs.records.utils;

/**
 * 
 * @author www.ivoslabs.com
 *
 */
public class MutableCounter {

    /** The counter value */
    private int value;

    /**
     * increments one the counter value
     */
    public void increment() {
	value++;
    }

    /**
     * Gets the value
     * 
     * @return {@code int} the value
     */
    public int getValue() {
	return value;
    }

}
