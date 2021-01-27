package com.ivoslabs.records.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Stack;
import java.util.function.Function;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ivoslabs.records.core.func.RowConsumer;
import com.ivoslabs.records.exceptions.RecordsException;

/**
 *
 * @since
 * @author www.ivoslabs.com
 *
 */
public class ParseUtils {

    /** The constant logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(ParseUtils.class);

    /** The constant pipe */
    public static final String PIPE = "|";

    /** The constant empty */
    public static final String EMPTY = "";

    /** The constant 0 */
    public static final int _0 = 0;

    /** The constant 1 */
    public static final int _1 = 1;

    /** The constant -1 */
    public static final int NEG = -1;

    /** The constant 1024 */
    public static final int _1024 = 1024;

    /** The constant space */
    public static final String SPACE = " ";

    /** The constant pipe separator */
    public static final String PIPE_SEPARATOR = "\\|";

    /** The constant true (1) used to parse a boolean without converter */
    private static final String TRUE_1 = "1";

    /** The constant true (Y) used to parse a boolean without converter */
    private static final String TRUE_Y = "Y";

    /** The constant true (T) used to parse a boolean without converter */
    private static final String TRUE_T = "T";

    /** The constant \n */
    private static final char BREAK_LINE = '\n';

    /**
     *
     * Creates a ParseUtils instance
     */
    private ParseUtils() {
        super();
    }

    /**
     * Parse a string value to a Object
     *
     * @param value a string representation of a Object
     * @param type  required type
     * @return the object represented by the received String
     */
    public static Object parse(String value, Class<?> type) {

        Object v = null;

        value = StringUtils.trimToEmpty(value);

        if (type == long.class) {
            v = Long.parseLong(value);
        } else if (type == int.class) {
            v = Integer.parseInt(value);
        } else if (type == short.class) {
            v = Short.parseShort(value);
        } else if (type == double.class) {
            v = Double.parseDouble(value);
        } else if (type == float.class) {
            v = Float.parseFloat(value);
        } else if (type == boolean.class) {
            v = value.equals(TRUE_1) || value.equals(TRUE_T) || value.equals(TRUE_Y);
            if (v.equals(Boolean.FALSE)) {
                v = Boolean.parseBoolean(value);
            }
        } else if (type.equals(Long.class)) {
            if (!value.isEmpty()) {
                v = Long.parseLong(value);
            }
        } else if (type.equals(Integer.class)) {
            if (!value.isEmpty()) {
                v = Integer.parseInt(value);
            }
        } else if (type.equals(Short.class)) {
            if (!value.isEmpty()) {
                v = Short.parseShort(value);
            }
        } else if (type.equals(Double.class)) {
            if (!value.isEmpty()) {
                v = Double.parseDouble(value);
            }
        } else if (type.equals(Float.class)) {
            if (!value.isEmpty()) {
                v = Float.parseFloat(value);
            }
        } else if (type.equals(BigDecimal.class)) {
            if (!value.isEmpty()) {
                v = new BigDecimal(value);
            }
        } else if (type.equals(Boolean.class)) {
            if (!value.isEmpty()) {
                v = value.equals(TRUE_1) || value.equals(TRUE_T) || value.equals(TRUE_Y);
                if (v.equals(Boolean.FALSE)) {
                    v = Boolean.parseBoolean(value);
                }
            }
        } else {
            v = value;
        }

        return v;
    }

    /**
     * Read a file and for each row execute the received RowConsumer
     *
     * @param url    path file to be read
     * @param action action to do for each row
     * @throws RecordsException when occur an Exception processing a row
     */
    public static void readTextFile(String url, RowConsumer action) throws RecordsException {

        int rowNumber = _0;

        try (BufferedReader br = new BufferedReader(new FileReader(url))) {

            String currentLine;

            // mutable to receive the flag to stop the iteration
            MutableBoolean stop = new MutableBoolean(Boolean.FALSE);

            while ((currentLine = br.readLine()) != null) {

                rowNumber++;

                action.process(currentLine, rowNumber, stop);

                if (BooleanUtils.isTrue(stop.getValue())) {
                    break;
                }
            }

        } catch (RecordsException e) {
            throw e;
        } catch (Exception e) {
            if (rowNumber > 0) {
                throw new RecordsException("An error has occurred while processing file: " + url + "; row: " + rowNumber + "; Detail: " + e.getMessage(), e);
            } else {
                throw new RecordsException("An error occurred reading the file: " + url, e);
            }
        }

    }

    /**
     *
     * @param path
     * @param headers
     * @param rowHeaderSuplier
     * @param data
     * @param rowDataSuplier
     * @param tails
     * @param rowTailSuplier
     */
    public static <H, D, T> void writeFile(String path, Stack<H> headers, Function<H, String> rowHeaderSuplier, Stack<D> data, Function<D, String> rowDataSuplier, Stack<T> tails, Function<T, String> rowTailSuplier) {

        try (BufferedWriter out = new BufferedWriter(new FileWriter(path, Boolean.TRUE))) {

            LOGGER.debug("writing file: {}", path);

            while (headers != null && !headers.empty()) {
                out.write(rowHeaderSuplier.apply(headers.firstElement()) + BREAK_LINE);
                headers.remove(0);
            }

            while (!data.empty()) {
                out.write(rowDataSuplier.apply(data.firstElement()) + BREAK_LINE);
                data.remove(0);
            }

            while (tails != null && !tails.empty()) {
                out.write(rowTailSuplier.apply(tails.firstElement()) + BREAK_LINE);
                tails.remove(0);
            }

            LOGGER.debug("writed: {}", path);

        } catch (IOException e) {
            throw new RecordsException("An error occurred saving the file: " + path, e);
        }
    }

    /**
     * Counts the number of lines in a file
     *
     * @param filename path file
     * @return the number of lines
     */
    public static int countLinesNew(String filename) {

        int count = _0;

        try (InputStream is = new BufferedInputStream(new FileInputStream(filename))) {

            byte[] c = new byte[_1024];

            int readChars = is.read(c);

            if (readChars != NEG) {

                count++;

                byte lastChar = _0;

                while (readChars == _1024) {

                    for (int i = _0; i < _1024; i++) {

                        if (c[i] == BREAK_LINE) {
                            count++;
                        }

                        if (i == _1024 - _1) {
                            lastChar = c[i];
                        }

                    }

                    readChars = is.read(c);

                }

                if (readChars == NEG && lastChar == BREAK_LINE) {
                    count--;
                } else {
                    // count remaining characters
                    while (readChars != NEG) {
                        for (int i = _0; i < readChars; i++) {
                            if (c[i] == BREAK_LINE && i < readChars - _1) {
                                count++;
                            }
                        }
                        readChars = is.read(c);
                    }
                }

            }

        } catch (Exception e) {
            throw new RecordsException("An error ocurred counting the lines of file: " + filename, e);
        }

        return count;
    }

}
