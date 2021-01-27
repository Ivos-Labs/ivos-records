/**
 *
 */
package com.ivoslabs.records.parsers;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.apache.commons.lang3.mutable.Mutable;
import org.apache.commons.lang3.mutable.MutableObject;

import com.ivoslabs.records.core.Extractor;
import com.ivoslabs.records.core.FileType;

/**
 * Utility to parse COPY data to POJOs (using {@code @Pic} annotation) and viceversa
 *
 * @since 1.0.0
 * @author www.ivoslabs.com
 * @see com.ivoslabs.records.annontation.Pic &#64;Pic
 * @see com.ivoslabs.records.annontation.Pic#beginIndex() &#64;Pic#beginIndex
 * @see com.ivoslabs.records.annontation.Pic#size() &#64;Pic#size
 * @see com.ivoslabs.records.annontation.Converter Converter
 */
public class CopyParser {

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
        return Extractor.convertStringToObject(data, type, FileType.COPY);
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
        return Extractor.convertStringsToObjects(data, type, FileType.COPY);
    }

    /**
     * Find first element which matches with the condition
     *
     * @param <D>
     * @param file       file name
     * @param headerSize header size
     * @param dataType   data type
     * @param condition  condition
     * @return the first object found
     * @since 1.0.0
     * @author www.ivoslabs.com
     *
     */
    public <D> D findFirst(String file,
            // header
            Integer headerSize,
            // data
            Class<D> dataType,
            Predicate<D> condition) {

        Mutable<D> mutable = new MutableObject<D>();

        Extractor.processFile(file, null, headerSize, null, dataType, mutable::setValue, condition, null, null, null, FileType.COPY, Boolean.TRUE);

        return mutable.getValue();
    }

    /**
     * Find all elements which match with the condition
     *
     * @param <D>
     * @param file       file name
     * @param headerSize header size
     * @param dataType   data type
     * @param condition  condition
     * @return the first object found
     * @since 1.0.0
     * @author www.ivoslabs.com
     *
     */
    public <D> List<D> find(String file,
            // header
            Integer headerSize,
            // data
            Class<D> dataType,
            Predicate<D> condition) {

        List<D> list = new ArrayList<>();

        Extractor.processFile(file, null, headerSize, null, dataType, list::add, condition, null, null, null, FileType.COPY, null);

        return list;
    }

    /**
     * Creates an instance of H, D, or T for each row in a file and execute the respective received ObjectConsumer
     *
     * @param <H>            Header type
     * @param <D>            Data type
     * @param <T>            Tail type
     * @param file           file path
     * @param headerType     header type
     * @param headerSize     number of first rows to be processed with headerType and headerConsumer
     * @param headerConsumer action to do for each header instance
     * @param dataType       data type
     * @param dataConsumer   action to do for each data instance
     * @param tailType       tail type
     * @param tailSize       number of last rows to be processed with tailType and tailConsumer
     * @param tailConsumer   action to do for each tail instance
     * @since 1.0.0
     * @author www.ivoslabs.com
     */
    public <H, D, T> void processFile(String file,
            // header
            Class<H> headerType,
            Integer headerSize,
            Consumer<H> headerConsumer,
            // data
            Class<D> dataType,
            Consumer<D> dataConsumer,
            // tail
            Class<T> tailType,
            Integer tailSize,
            Consumer<T> tailConsumer) {

        Extractor.processFile(file, headerType, headerSize, headerConsumer, dataType, dataConsumer, null, tailType, tailSize, tailConsumer, FileType.COPY, null);
    }

    /**
     * Creates an instance of H or T for each row in a file and execute the respective received ObjectConsumer
     *
     * @param <H>            Header type
     * @param <D>            Data type
     * @param file           file path
     * @param headerType     header type
     * @param headerSize     number of first rows to be processed with headerType and headerConsumer
     * @param headerConsumer action to do for each header instance
     * @param dataType       data type
     * @param dataConsumer   action to do for each data instance
     * @since 1.0.0
     * @author www.ivoslabs.com
     */
    public <H, D> void processFile(String file,
            // header
            Class<H> headerType,
            Integer headerSize,
            Consumer<H> headerConsumer,
            // data
            Class<D> dataType,
            Consumer<D> dataConsumer) {

        this.processFile(file, headerType, headerSize, headerConsumer, dataType, dataConsumer, null, null, null);
    }

    /**
     * Creates an instance of D or T for each row in a file and execute the respective received ObjectConsumer
     *
     * @param <D>          Data type
     * @param <T>          Tail type
     * @param file         file path
     * @param dataType     data type
     * @param dataConsumer action to do for each data instance
     * @param tailType     tail type
     * @param tailSize     number of last rows to be processed with tailType and tailConsumer
     * @param tailConsumer action to do for each tail instance
     * @since 1.0.0
     * @author www.ivoslabs.com
     */
    public <D, T> void processFile(String file,
            // Data
            Class<D> dataType,
            Consumer<D> dataConsumer,
            // Tail
            Class<T> tailType,
            Integer tailSize,
            Consumer<T> tailConsumer) {

        this.processFile(file, null, null, null, dataType, dataConsumer, tailType, tailSize, tailConsumer);
    }

    /**
     * Creates an instance of D for each row in a file and execute the respective received ObjectConsumer
     *
     * @param <D>          Data type
     * @param file         file path
     * @param dataType     fata type
     * @param dataConsumer action to do for each data instance
     * @since 1.0.0
     * @author www.ivoslabs.com
     */
    public <D> void processFile(String file, Class<D> dataType, Consumer<D> dataConsumer) {
        this.processFile(file, null, null, null, dataType, dataConsumer, null, null, null);
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
        return Extractor.convertObjectToString(data, FileType.COPY);
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
        return Extractor.convertObjectsToStrings(data, FileType.COPY);
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
    public <H, D, T> void objectsToFile(String file, Stack<H> header, Stack<D> data, Stack<T> tails) {
        Extractor.convertObjectsToFile(file, header, data, tails, FileType.COPY);
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
    public <H, D> void objectsToFileHD(String file, Stack<H> header, Stack<D> data) {
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
    public <D, T> void objectsToFileDT(String file, Stack<D> data, Stack<T> tails) {
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
    public <D> void objectsToFile(String file, Stack<D> data) {
        this.objectsToFile(file, null, data, null);
    }
}
