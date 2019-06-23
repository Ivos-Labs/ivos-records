package com.ivoslabs.records.exceptions;

/**
 * Exception to be thrown when occurs an exception preparing, parsing, reading or saving data
 * 
 * @author www.ivoslabs.com
 *
 */
public class RecordParserException extends RuntimeException {

    /** The constant serialVersionUID */
    private static final long serialVersionUID = 1L;

    /**
     * Create a RecordParserException
     * 
     * @param message the message
     * @param cause   the cause
     */
    public RecordParserException(String message, Throwable cause) {
	super(message, cause);
    }
}
