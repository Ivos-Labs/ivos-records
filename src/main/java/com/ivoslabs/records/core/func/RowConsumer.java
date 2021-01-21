/**
 *
 */
package com.ivoslabs.records.core.func;

import org.apache.commons.lang3.mutable.MutableBoolean;

/**
 * Interface to be implemented to process a file row<br>
 * Evaluate if the line is a header, data or tail<br>
 *
 * @author www.ivoslabs.com
 *
 */
public interface RowConsumer {

    /**
     * Evaluate if the line is a header, data or tail en execute the respetive Parser
     *
     * @param row       line content
     * @param rowNumber row number
     * @param keep      flag to stop
     * @throws Exception
     */
    void process(String row, int rowNumber, MutableBoolean keep) throws Exception;

}
