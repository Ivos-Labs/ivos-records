package com.ivoslabs.records.exceptions;

public class RecordParserException extends RuntimeException {

    /** */
    private static final long serialVersionUID = 1L;

    /**
     * 
     * @param message
     * @param cause
     */
    public RecordParserException(String message, Throwable cause) {
	super(message, cause);
    }
}
