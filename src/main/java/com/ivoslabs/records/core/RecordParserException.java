/**
 * 
 */
package com.ivoslabs.records.core;

/**
 * @author www.ivoslabs.com
 *
 */
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
