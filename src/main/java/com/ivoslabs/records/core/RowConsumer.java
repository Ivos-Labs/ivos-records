/**
 * 
 */
package com.ivoslabs.records.core;

/**
 * Interface to be implemented to process a file row<br>
 * Evaluate if the line is a header, data or tail<br>
 * Used to process line from a file
 * 
 * @author www.ivoslabs.mx
 *
 */
public interface RowConsumer {

    /**
     * @param row
     */
    void process(String row) throws Exception;

}
