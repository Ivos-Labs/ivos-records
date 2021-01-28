package com.ivoslabs.records.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Deque;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ivoslabs.records.core.RowConsumer;
import com.ivoslabs.records.exceptions.ParseException;
import com.ivoslabs.records.exceptions.RecordsException;

/**
 * Utilities to read, write and parse java types
 *
 * @since 1.0.0
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
    public static final int NUM_0 = 0;

    /** The constant 1 */
    public static final int NUM_1 = 1;

    /** The constant 2 */
    public static final int NUM_2 = 2;

    /** The constant -1 */
    public static final int NUM_1_NEG = -1;

    /** The constant 1024 */
    public static final int NUM_1024 = 1024;

    /** The constant space */
    public static final String SPACE = " ";

    /** The constant pipe separator */
    public static final String PIPE_SEPARATOR = "\\|";

    /** The constant \n */
    public static final char BREAK_LINE = '\n';

    /** The constant true (1) used to parse a boolean without converter */
    private static final String TRUE_1 = "1";

    /** The constant true (Y) used to parse a boolean without converter */
    private static final String TRUE_Y = "Y";

    /** The constant true (T) used to parse a boolean without converter */
    private static final String TRUE_T = "T";

    /** The constant true (T) used to parse a boolean without converter */
    private static final String TRUE_TRUE = "true";

    /** Predicate to validate empty Strings */
    private static final Predicate<String> PREDICATE_EMPTY = String::isEmpty;

    /** Predicate to validate not empty String */
    private static final Predicate<String> PREDICATE_NOT_EMPTY = PREDICATE_EMPTY.negate();

    /**
     *
     * Creates a ParseUtils instance
     */
    private ParseUtils() {
        super();
    }

    /**
     * Creates a new instance of the class represented by the received class
     *
     * @param <T>   expected type
     * @param clazz class to be instantiated
     * @return a newly allocated instance of the class represented by thisobject.
     * @since 1.0.0
     * @author www.ivoslabs.com
     *
     */
    public static <T> T newInstance(Class<T> clazz) {
        T ins;

        try {
            ins = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RecordsException("An error occurred trying to create an instance of {}", clazz, e);
        }

        return ins;
    }

    /**
     * Parse a string value to an Object
     *
     * @param value a string representation of an Object
     * @param type  required type
     * @return the object represented by the received String
     * @since 1.0.0
     * @author www.ivoslabs.com
     */
    public static Object parse(String value, Class<?> type) {

        Object v;

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
            v = value.equals(TRUE_1) || value.equalsIgnoreCase(TRUE_T) || value.equalsIgnoreCase(TRUE_Y) || value.equalsIgnoreCase(TRUE_TRUE);
        } else {
            v = ParseUtils.parseWrapped(value, type);
        }

        return v;
    }

    /**
     * Read a file and for each row execute the received RowConsumer
     *
     * @param <H>      header type
     * @param <D>      data type
     * @param <T>      tail type
     * @param fileName the name of the file to read from
     * @param action   action to do for each row
     * @since 1.0.0
     * @author www.ivoslabs.com
     */
    public static <H, D, T> void readTextFile(String fileName, RowConsumer<H, D, T> action) {

        int rowNumber = NUM_0;

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            String currentLine;

            // mutable to receive the flag to stop the iteration
            MutableBoolean stop = new MutableBoolean();

            while ((currentLine = br.readLine()) != null) {

                rowNumber++;

                action.process(currentLine, rowNumber, stop);

                if (stop.getValue()) {
                    break;
                }
            }

        } catch (RecordsException | ParseException e) {
            throw e;
        } catch (Exception e) {
            if (rowNumber > 0) {
                throw new RecordsException("An error occurred while processing file: {}; row: {}; Detail: {};", fileName, rowNumber, e.getMessage(), e);
            } else {
                throw new RecordsException("An error occurred reading the file: {};", fileName, e);
            }
        }

    }

    /**
     * Append to a file the received data
     *
     * @param <H>              header type
     * @param <D>              data type
     * @param <T>              tail type
     * @param fileName         The system-dependent filename to write
     * @param headers          list of headers
     * @param rowHeaderSuplier function to parse the header objects to String
     * @param data             list of data
     * @param rowDataSuplier   function to parse the data objects to String
     * @param tails            list of tails
     * @param rowTailSuplier   function to parse the tail objects to String
     * @since 1.0.0
     * @author www.ivoslabs.com
     *
     */
    public static <H, D, T> void writeFile(String fileName, Deque<H> headers, Function<H, String> rowHeaderSuplier, Deque<D> data, Function<D, String> rowDataSuplier, Deque<T> tails, Function<T, String> rowTailSuplier) {

        try (BufferedWriter out = new BufferedWriter(new FileWriter(fileName, Boolean.TRUE))) {

            LOGGER.info("writing file: {}", fileName);

            while (headers != null && !headers.isEmpty()) {
                out.write(rowHeaderSuplier.apply(headers.pollFirst()) + BREAK_LINE);
            }

            while (!data.isEmpty()) {
                out.write(rowDataSuplier.apply(data.pollFirst()) + BREAK_LINE);
            }

            while (tails != null && !tails.isEmpty()) {
                out.write(rowTailSuplier.apply(tails.pollFirst()) + BREAK_LINE);
            }

            LOGGER.info("written: {};", fileName);

        } catch (IOException e) {
            throw new RecordsException("An error occurred saving the file: " + fileName, e);
        }
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
     * Parse a string value to a Object
     *
     * @param value a string representation of an Object
     * @param type  required type
     * @return the object represented by the received String
     * @since 1.0.0
     * @author www.ivoslabs.com
     */
    private static Object parseWrapped(String value, Class<?> type) {

        Object v = null;

        if (type.equals(Long.class)) {
            v = Optional.of(value).filter(PREDICATE_NOT_EMPTY).map(Long::parseLong).orElse(null);
        } else if (type.equals(Integer.class)) {
            v = Optional.of(value).filter(PREDICATE_NOT_EMPTY).map(Integer::parseInt).orElse(null);
        } else if (type.equals(Short.class)) {
            v = Optional.of(value).filter(PREDICATE_NOT_EMPTY).map(Short::parseShort).orElse(null);
        } else if (type.equals(Double.class)) {
            v = Optional.of(value).filter(PREDICATE_NOT_EMPTY).map(Double::parseDouble).orElse(null);
        } else if (type.equals(Float.class)) {
            v = Optional.of(value).filter(PREDICATE_NOT_EMPTY).map(Float::parseFloat).orElse(null);
        } else if (type.equals(BigDecimal.class)) {
            v = Optional.of(value).filter(PREDICATE_NOT_EMPTY).map(BigDecimal::new).orElse(null);
        } else if (type.equals(Boolean.class)) {
            if (!value.isEmpty()) {
                v = value.equals(TRUE_1) || value.equalsIgnoreCase(TRUE_T) || value.equalsIgnoreCase(TRUE_Y) || value.equalsIgnoreCase(TRUE_TRUE);
            }
        } else {
            v = value;
        }

        return v;

    }
}
