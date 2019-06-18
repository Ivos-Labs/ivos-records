/**
 * 
 */
package com.ivoslabs.records.parsers;

import java.util.List;
import java.util.Stack;

import com.ivoslabs.records.annontation.PipedField;
import com.ivoslabs.records.core.Extractor;
import com.ivoslabs.records.function.ObjectConsumer;

/**
 * Utility to parse piped data to pojos
 * 
 * @author www.ivoslabs.com
 *
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
     * Creates a new instance of T using the received data content
     * 
     * @param      <T> Required type
     * @param data values separated by pipes
     * @param type Required type
     * @return A T instance
     */
    public <T> T toObject(String data, Class<T> type) {
	return Extractor.convertStringToObject(data, type, PipedField.class);
    }

    /**
     * Creates a list of instances of T using the received data content
     * 
     * @param      <T> Required type
     * @param data List of values separated by pipes
     * @param type Required type
     * @return A List of T instance
     */
    public <T> List<T> toObjects(List<String> data, Class<T> type) {
	return Extractor.convertStringsToObjects(data, type, PipedField.class);
    }

    /**
     * Creates a instance of T for each row in a file and execute the respective received ObjectConsumer
     * 
     * @param                <H> Header type
     * @param                <D> Data type
     * @param                <T> Tail type
     * @param file           file path
     * @param headerType     Header type
     * @param headerSize     Header size
     * @param headerConsumer action to do for each header instance
     * @param dataType       Data type
     * @param dataConsumer   action to do for each data instance
     * @param tailType       Tail type
     * @param tailSize       Tail size
     * @param tailConsumer   action to do for each tail instance
     */
    public <H, D, T> void fileToObjects(String file,
	    Class<H> headerType,
	    Integer headerSize,
	    ObjectConsumer<H> headerConsumer,
	    Class<D> dataType,
	    ObjectConsumer<D> dataConsumer,
	    Class<T> tailType,
	    Integer tailSize,
	    ObjectConsumer<T> tailConsumer) {

	Extractor.convertFileToObjects(file, headerType, headerSize, headerConsumer, dataType, dataConsumer, tailType, tailSize, tailConsumer, PipedField.class);
    }

    /**
     * 
     * @param file
     * @param headerType
     * @param headerSize
     * @param headerConsumer
     * @param dataType
     * @param dataConsumer
     */
    public <T, U, V> void fileToObjects(String file,
	    Class<T> headerType,
	    Integer headerSize,
	    ObjectConsumer<T> headerConsumer,
	    Class<U> dataType,
	    ObjectConsumer<U> dataConsumer) {

	Extractor.convertFileToObjects(file, headerType, headerSize, headerConsumer, dataType, dataConsumer, PipedField.class);
    }

    /**
     * 
     * @param file
     * @param dataType
     * @param dataConsumer
     * @param tailType
     * @param tailSize
     * @param tailConsumer
     */
    public <T, U, V> void fileToObjects(String file,
	    Class<U> dataType,
	    ObjectConsumer<U> dataConsumer,
	    Class<V> tailType,
	    Integer tailSize,
	    ObjectConsumer<V> tailConsumer) {

	Extractor.convertFileToObjects(file, dataType, dataConsumer, tailType, tailSize, tailConsumer, PipedField.class);
    }

    /**
     * 
     * @param file
     * @param dataType
     * @param dataConsumer
     */
    public <T> void fileToObjects(String file, Class<T> dataType, ObjectConsumer<T> dataConsumer) {
	Extractor.convertFileToObjects(file, dataType, dataConsumer, PipedField.class);
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
     * 
     * @param data
     * @return
     */
    public String toString(Object data) {
	return Extractor.convertObjectToString(data, PipedField.class);
    }

    /**
     * 
     * @param data
     * @return
     */
    public List<String> toStrings(List<?> data) {
	return Extractor.convertObjectsToStrings(data, PipedField.class);
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
     * 
     * @param file
     * @param header
     * @param data
     * @param tails
     */
    public <H, D, T> void objectsToFile(String file, Stack<H> header, Stack<D> data, Stack<T> tails) {
	Extractor.convertObjectsToFile(file, header, data, tails, PipedField.class);
    }

    /**
     * 
     * @param file
     * @param header
     * @param data
     */
    public <H, D> void objectsToFileHD(String file, Stack<H> header, Stack<D> data) {
	Extractor.convertObjectsToFile(file, header, data, null, PipedField.class);
    }

    /**
     * 
     * @param file
     * @param data
     * @param tails
     */
    public <D, T> void objectsToFileDT(String file, Stack<D> data, Stack<T> tails) {
	Extractor.convertObjectsToFile(file, null, data, tails, PipedField.class);
    }

    /**
     * 
     * @param file
     * @param data
     */
    public <D> void objectsToFile(String file, Stack<D> data) {
	Extractor.convertObjectsToFile(file, null, data, null, PipedField.class);
    }

}
