package com.ivoslabs.records.exceptions;

/**
 * Exception to be thrown when occurs an exception parsing data
 *
 * @author www.ivoslabs.com
 *
 */
public class ParseException extends RuntimeException {

    /** The constant serialVersionUID */
    private static final long serialVersionUID = 1L;

    /**
     * Create a ParseException
     *
     * @param message the message
     * @param cause   the cause
     */
    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
