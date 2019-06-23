package com.ivoslabs.records.utils;

/**
 * Utility to modify an int in anonymous classes
 * 
 * @author www.ivoslabs.com
 *
 */
public final class MutableInt {

    /** The value */
    private int value;

    /**
     * Gets the value
     *
     * @return The value
     */
    public int getValue() {
	return this.value;
    }

    /**
     * Sets the value
     *
     * @param value The value to set
     */
    public void setValue(int value) {
	this.value = value;
    }

}
