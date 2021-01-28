/**
 *
 */
package com.ivoslabs.records.core;

import java.util.function.Consumer;
import java.util.function.Predicate;

import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableInt;

import com.ivoslabs.records.dtos.BaseClass;

/**
 * Interface to be implemented to process a file row<br>
 * Evaluate if the line is a header, data or tail<br>
 *
 * @author www.ivoslabs.com
 *
 */
public class RowConsumer<H, D, T> {

    private MutableInt rowNum = new MutableInt();

    private BaseClass extracts;
    private BaseClass headerExtracts;
    private BaseClass tailExtracts;

    // header
    private Class<H> headerType;
    private Integer headerSize;
    private Consumer<H> headerConsumer;
    // data
    private Class<D> dataType;
    private Consumer<D> dataConsumer;
    // tail
    private Class<T> tailType;
    private Consumer<T> tailConsumer;

    private Predicate<D> condition;
    private Boolean findFirst;

    private Integer tailLine;

    /**
     *
     * Creates a RowConsumer instance
     *
     * @param rowNum
     * @param extracts
     * @param headerExtracts
     * @param tailExtracts
     * @param parserTask
     * @param condition
     * @param tailLine
     */
    public RowConsumer(MutableInt rowNum, BaseClass extracts, BaseClass headerExtracts, BaseClass tailExtracts, ParserTask<H, D, T> parserTask, Predicate<D> condition, Integer tailLine) {

        this.rowNum = rowNum;
        this.extracts = extracts;
        this.headerExtracts = headerExtracts;
        this.tailExtracts = tailExtracts;

        // header
        headerType = parserTask.getHeaderType();
        headerSize = parserTask.getHeaderSize();
        headerConsumer = parserTask.getHeaderConsumer();
        // data
        dataType = parserTask.getDataType();
        dataConsumer = parserTask.getDataConsumer();
        // tail
        tailType = parserTask.getTailType();
        tailConsumer = parserTask.getTailConsumer();

        this.condition = condition;
        this.tailLine = tailLine;
    }

    public void activeFindFirst() {
        this.findFirst = Boolean.TRUE;
    }

    /**
     * Evaluate if the line is a header, data or tail en execute the respetive Parser
     *
     * @param row       line content
     * @param rowNumber row number
     * @param stop      flag to stop
     * @throws Exception
     */
    public void process(String row, int rowNumber, MutableBoolean stop) {

        rowNum.setValue(rowNumber);

        if (rowNumber <= headerSize) {
            this.processHeader(row);
        } else if (tailLine != null && rowNumber > tailLine) {
            this.processTail(row, stop);
        } else {
            this.processData(row, stop);
        }

    }

    /**
     *
     */

    /**
     *
     */

    /**
     * Execute the respetive Parser
     *
     * @param row line content
     * @throws Exception
     */
    private void processHeader(String row) {
        if (headerExtracts != null && condition == null) {
            // ignores the header if is a query
            H object = Extractor.convertStringRowToObject(row, headerType, headerExtracts);
            if (object != null && headerConsumer != null) {
                headerConsumer.accept(object);
            }
        }

    }

    /**
     * Execute the respetive Parser
     *
     * @param row  line content
     * @param stop flag to stop
     * @throws Exception
     */
    private void processData(String row, MutableBoolean stop) {
        D object = Extractor.convertStringRowToObject(row, dataType, extracts);

        if (object != null) {
            if (condition != null) {
                boolean match = condition.test(object);

                if (findFirst != null && findFirst) {
                    // if match set flag to stop iteration
                    stop.setValue(match);
                }

                if (match) {
                    dataConsumer.accept(object);
                }

            } else {
                dataConsumer.accept(object);
            }
        }
    }

    /**
     * Execute the respetive Parser
     *
     * @param row  line content
     * @param stop flag to stop
     * @throws Exception
     */
    private void processTail(String row, MutableBoolean stop) {
        if (condition == null) {
            T object = Extractor.convertStringRowToObject(row, tailType, tailExtracts);
            if (object != null) {
                tailConsumer.accept(object);
            }
        } else {
            stop.setTrue();
        }

    }

}
