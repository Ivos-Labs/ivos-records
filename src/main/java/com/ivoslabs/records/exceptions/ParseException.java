package com.ivoslabs.records.exceptions;

/**
 * 
 * @author www.ivoslabs.com
 *
 */
public class ParseException extends RuntimeException {

    /** */
    private static final long serialVersionUID = 1L;

    /**
     * 
     * @param message
     * @param cause
     */
    public ParseException(String message, Throwable cause) {
	super(message, cause);
    }
}
