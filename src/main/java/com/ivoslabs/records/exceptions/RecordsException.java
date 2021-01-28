package com.ivoslabs.records.exceptions;

import org.apache.logging.log4j.message.ParameterizedMessage;

/**
 * Exception to be thrown when occurs an exception preparing, parsing, reading or saving data
 *
 * @author www.ivoslabs.com
 *
 */
public class RecordsException extends RuntimeException {

    /** The constant serialVersionUID */
    private static final long serialVersionUID = 1L;

    /**
     * Creates a RecordParserException
     *
     * @param message the message
     * @param cause   the cause
     */
    public RecordsException(String message, Object... args) {
        super(new ParameterizedMessage(message, args).getFormattedMessage(), (Throwable) args[args.length - 1]);
    }
}
