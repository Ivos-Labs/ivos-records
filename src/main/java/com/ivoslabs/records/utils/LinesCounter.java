/**
 *
 */
package com.ivoslabs.records.utils;

import static com.ivoslabs.records.utils.ParseUtils.BREAK_LINE;
import static com.ivoslabs.records.utils.ParseUtils.NUM_0;
import static com.ivoslabs.records.utils.ParseUtils.NUM_1;
import static com.ivoslabs.records.utils.ParseUtils.NUM_1024;
import static com.ivoslabs.records.utils.ParseUtils.NUM_1_NEG;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.ivoslabs.records.exceptions.RecordsException;

/**
 * Class to count the number of lines in a file
 *
 * @since 1.0.0
 * @author www.ivoslabs.com
 *
 */
public class LinesCounter {

    /** counter */
    private int count;

    /** read chars */
    private int readChars;

    /** flag to indicate whether the las last char was a break line */
    private boolean lastCharWasBL;

    /** block of bytes to read */
    private byte[] bytes;

    /**
     * Creates a LinesCounter instance
     *
     * @since 1.0.0
     * @author www.ivoslabs.com
     */
    public LinesCounter() {
        super();
    }

    /**
     * Counts the number of lines in a file
     *
     * @param fileName the system-dependent file name to be read
     * @return the number of lines
     * @since 1.0.0
     * @author www.ivoslabs.com
     */
    public int countLines(String fileName) {

        this.count = NUM_0;
        this.lastCharWasBL = Boolean.FALSE;
        this.bytes = new byte[NUM_1024];

        try (InputStream is = new BufferedInputStream(new FileInputStream(fileName))) {

            this.readChars = is.read(bytes);

            if (this.readChars != NUM_1_NEG) {
                // the file is not empty
                this.count++;

                // count lines by blocks of 1024 bytes
                readByBlocks(is);

                if (this.readChars == NUM_1_NEG && this.lastCharWasBL) {
                    // the last char was a break line, so the next line is empty
                    this.count--;
                } else {
                    // count lines in remaining bytes
                    this.readRemainingBytes(is);
                }
            }

        } catch (Exception e) {
            throw new RecordsException("An error ocurred counting the lines of file: {}", fileName, e);
        }

        return count;
    }

    /***
     * *
     ***/

    /***
     * *
     ***/

    /***
     * *
     ***/

    /*******************
     * Private methods *
     *******************/

    /***
     * *
     ***/

    /**
     * Counts the break lines
     *
     * @param is InputStream
     * @throws IOException
     * @since 1.0.0
     * @author www.ivoslabs.com
     *
     */
    private void readByBlocks(InputStream is) throws IOException {

        while (this.readChars == NUM_1024) {

            this.lastCharWasBL = Boolean.FALSE;

            for (int i = NUM_0; i < NUM_1024; i++) {

                if (this.bytes[i] == BREAK_LINE) {
                    this.count++;
                    if (i == NUM_1024 - NUM_1) {
                        this.lastCharWasBL = Boolean.TRUE;
                    }
                }

            }

            readChars = is.read(bytes);

        }
    }

    /**
     * Counts the break lines in the remaining bytes
     *
     * @param is InputStream
     * @throws IOException
     * @since 1.0.0
     * @author www.ivoslabs.com
     *
     */
    private void readRemainingBytes(InputStream is) throws IOException {

        while (this.readChars != NUM_1_NEG) {
            for (int i = NUM_0; i < this.readChars; i++) {
                if (this.bytes[i] == BREAK_LINE && i < this.readChars - NUM_1) {
                    this.count++;
                }
            }
            this.readChars = is.read(bytes);
        }
    }

}
