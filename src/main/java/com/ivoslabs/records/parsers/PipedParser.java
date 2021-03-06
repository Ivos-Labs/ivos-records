/**
 *
 */
package com.ivoslabs.records.parsers;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.function.Predicate;

import org.apache.commons.lang3.mutable.Mutable;
import org.apache.commons.lang3.mutable.MutableObject;

import com.ivoslabs.records.annontation.Piped;
import com.ivoslabs.records.core.Extractor;
import com.ivoslabs.records.core.FileType;
import com.ivoslabs.records.core.ParserTask;

/**
 * Utility to parse piped data to POJOs (using {@code @PipedField } annotation) and viceversa
 *
 * @since 1.0.0
 * @author www.ivoslabs.com
 * @see Piped &#64;Piped
 * @see com.ivoslabs.records.annontation.Piped#value() &#64;Piped#value
 * @see com.ivoslabs.records.annontation.Converter Converter
 */
public class PipedParser {

    /***
     * *
     ***/

    /***
     * *
     ***/

    /**********************************
     * Start String-To-Object methods *
     **********************************/

    /**
     * Creates a new instance of T using the received data
     *
     * @param <T>  Required type
     * @param data values separated by pipes
     * @param type required type
     * @return a T instance
     * @since 1.0.0
     * @author www.ivoslabs.com
     */
    public <T> T toObject(String data, Class<T> type) {
        return Extractor.convertStringToObject(data, type, FileType.PIPED);
    }

    /**
     * Creates a new instance of T using each item in the received data list
     *
     * @param <T>  Required type
     * @param data list of values separated by pipes
     * @param type required type
     * @return a List of T instance
     * @since 1.0.0
     * @author www.ivoslabs.com
     */
    public <T> List<T> toObjects(List<String> data, Class<T> type) {
        return Extractor.convertStringsToObjects(data, type, FileType.PIPED);
    }

    /**
     * Find first element which matches with the condition
     *
     * @param <D>
     * @param file       file name
     * @param headerSize header size
     * @param tailSize   tail size
     * @param dataType   data type
     * @param condition  condition
     * @return the first object found
     * @since 1.0.0
     * @author www.ivoslabs.com
     *
     */
    public <D> D findFirst(String file, Integer headerSize, Integer tailSize, Class<D> dataType, Predicate<D> condition) {

        Mutable<D> mutable = new MutableObject<>();

        ParserTask<Object, D, Object> parserTask = new ParserTask<>(file, dataType, mutable::setValue);
        parserTask.setHeaderInfo(null, headerSize, null);
        parserTask.setTailInfo(null, tailSize, null);

        Extractor.processFile(parserTask, condition, FileType.PIPED, Boolean.TRUE);

        return mutable.getValue();
    }

    /**
     * Find all elements which match with the condition
     *
     * @param <D>
     * @param file       file name
     * @param headerSize header size
     * @param tailSize   tail size
     * @param dataType   data type
     * @param condition  condition
     * @return the first object found
     * @since 1.0.0
     * @author www.ivoslabs.com
     *
     */
    public <D> List<D> find(String file, Integer headerSize, Integer tailSize, Class<D> dataType, Predicate<D> condition) {

        List<D> list = new ArrayList<>();

        ParserTask<Object, D, Object> parserTask = new ParserTask<>(file, dataType, list::add);
        parserTask.setHeaderInfo(null, headerSize, null);
        parserTask.setTailInfo(null, tailSize, null);

        Extractor.processFile(parserTask, condition, FileType.PIPED, null);

        return list;
    }

    /**
     * Creates an instance of H, D, or T for each row in a file and execute the respective received task
     *
     * @param <H>        Header type
     * @param <D>        Data type
     * @param <T>        Tail type
     * @param parserTask ParserTask
     * @since 1.0.0
     * @author www.ivoslabs.com
     */
    public <H, D, T> void processFile(ParserTask<H, D, T> parserTask) {
        Extractor.processFile(parserTask, null, FileType.PIPED, null);
    }

    /********************************
     * End String-To-Object methods *
     ********************************/

    /***
     * *
     ***/

    /***
     * *
     ***/

    /**********************************
     * Start Object-To-String methods *
     **********************************/

    /**
     * Creates a {@code String} object representing the received object
     *
     * @param data the {@code Object} to be converted to a {@code Strings}
     * @return a string representation of the value of the received object
     * @since 1.0.0
     * @author www.ivoslabs.com
     */
    public String toString(Object data) {
        return Extractor.convertObjectToString(data, FileType.PIPED);
    }

    /**
     * Creates a {@code String} object representing each item in the received object list
     *
     * @param data the {@code Object} list to be converted to a {@code Strings}
     * @return a list of string representation of the value of each item in the received object list
     * @since 1.0.0
     * @author www.ivoslabs.com
     */
    public List<String> toStrings(List<?> data) {
        return Extractor.convertObjectsToStrings(data, FileType.PIPED);
    }

    /********************************
     * End Object-To-String methods *
     ********************************/

    /***
     * *
     ***/

    /***
     * *
     ***/

    /********************************
     * Start Object-To-File methods *
     ********************************/

    /**
     * Creates a {@code String} object representing each item in the received object lists and append it into a file
     *
     * @param <H>    Header type
     * @param <D>    Data type
     * @param <T>    Tail type
     * @param file   path file to save string representations of the value of each item in the received object lists
     * @param header objects to be appended into the fist rows of the file
     * @param data   objects to be appended into after header rows
     * @param tails  objects to be appended into the last rows of the file
     * @since 1.0.0
     * @author www.ivoslabs.com
     */
    public <H, D, T> void objectsToFile(String file, Deque<H> header, Deque<D> data, Deque<T> tails) {
        Extractor.convertObjectsToFile(file, header, data, tails, FileType.PIPED);
    }

    /**
     * Creates a {@code String} object representing each item in the received object lists and append it into a file
     *
     * @param <H>    Header type
     * @param <D>    Data type
     * @param file   path file to save string representations of the value of each item in the received object lists
     * @param header objects to be appended into the fist rows of the file
     * @param data   objects to be appended into after header rows
     * @since 1.0.0
     * @author www.ivoslabs.com
     */
    public <H, D> void objectsToFileHD(String file, Deque<H> header, Deque<D> data) {
        this.objectsToFile(file, header, data, null);
    }

    /**
     * Creates a {@code String} object representing each item in the received object lists and append it into a file
     *
     * @param <D>   Data type
     * @param <T>   Tail type
     * @param file  path file to save string representations of the value of each item in the received object lists
     * @param data  objects to be appended into after header rows
     * @param tails objects to be appended into the last rows of the file
     * @since 1.0.0
     * @author www.ivoslabs.com
     */
    public <D, T> void objectsToFileDT(String file, Deque<D> data, Deque<T> tails) {
        this.objectsToFile(file, null, data, tails);
    }

    /**
     * Creates a {@code String} object representing each item in the received object list and append it into a file
     *
     * @param <D>  Data type
     * @param file path file to save string representations of the value of each item in the received object list
     * @param data objects to be appended into the file
     * @since 1.0.0
     * @author www.ivoslabs.com
     */
    public <D> void objectsToFile(String file, Deque<D> data) {
        this.objectsToFile(file, null, data, null);
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

    /***
     * *
     ***/

    /*******************
     * private methods *
     ******************/

}
