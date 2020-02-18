/**
 * 
 */
package com.ivoslabs.records.parsers;

import java.util.List;
import java.util.Stack;

import com.ivoslabs.records.annontation.Piped;
import com.ivoslabs.records.core.Extractor;
import com.ivoslabs.records.function.Consumer;

/**
 * Utility to parse piped data to POJOs (using {@code @PipedField } annotation) and viceversa
 * 
 * @author www.ivoslabs.com
 * @see Piped &#64;PipedField
 * @see com.ivoslabs.records.annontation.Piped#value() &#64;PipedField#value
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
     * @param      <T> Required type
     * @param data values separated by pipes
     * @param type required type
     * @return a T instance
     */
    public <T> T toObject(String data, Class<T> type) {
	return Extractor.convertStringToObject(data, type, Piped.class);
    }

    /**
     * Creates a new instance of T using each item in the received data list
     * 
     * @param      <T> Required type
     * @param data list of values separated by pipes
     * @param type required type
     * @return a List of T instance
     */
    public <T> List<T> toObjects(List<String> data, Class<T> type) {
	return Extractor.convertStringsToObjects(data, type, Piped.class);
    }

    /**
     * Creates an instance of H, D, or T for each row in a file and execute the respective received ObjectConsumer
     * 
     * @param                <H> Header type
     * @param                <D> Data type
     * @param                <T> Tail type
     * @param file           file path
     * @param headerType     header type
     * @param headerSize     number of first rows to be processed with headerType and headerConsumer
     * @param headerConsumer action to do for each header instance
     * @param dataType       data type
     * @param dataConsumer   action to do for each data instance
     * @param tailType       tail type
     * @param tailSize       number of last rows to be processed with tailType and tailConsumer
     * @param tailConsumer   action to do for each tail instance
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

	Extractor.processFile(file, headerType, headerSize, headerConsumer, dataType, dataConsumer, tailType, tailSize, tailConsumer, Piped.class);
    }

    /**
     * Creates an instance of H or T for each row in a file and execute the respective received ObjectConsumer
     * 
     * @param                <H> Header type
     * @param                <D> Data type
     * @param file           file path
     * @param headerType     header type
     * @param headerSize     number of first rows to be processed with headerType and headerConsumer
     * @param headerConsumer action to do for each header instance
     * @param dataType       data type
     * @param dataConsumer   action to do for each data instance
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
     * @param              <D> Data type
     * @param              <T> Tail type
     * @param file         file path
     * @param dataType     data type
     * @param dataConsumer action to do for each data instance
     * @param tailType     tail type
     * @param tailSize     number of last rows to be processed with tailType and tailConsumer
     * @param tailConsumer action to do for each tail instance
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
     * @param              <D> Data type
     * @param file         file path
     * @param dataType     fata type
     * @param dataConsumer action to do for each data instance
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
     */
    public String toString(Object data) {
	return Extractor.convertObjectToString(data, Piped.class);
    }

    /**
     * Creates a {@code String} object representing each item in the received object list
     * 
     * @param data the {@code Object} list to be converted to a {@code Strings}
     * @return a list of string representation of the value of each item in the received object list
     */
    public List<String> toStrings(List<?> data) {
	return Extractor.convertObjectsToStrings(data, Piped.class);
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
     * @param        <H> Header type
     * @param        <D> Data type
     * @param        <T> Tail type
     * @param file   path file to save string representations of the value of each item in the received object lists
     * @param header objects to be appended into the fist rows of the file
     * @param data   objects to be appended into after header rows
     * @param tails  objects to be appended into the last rows of the file
     */
    public <H, D, T> void objectsToFile(String file, Stack<H> header, Stack<D> data, Stack<T> tails) {
	Extractor.convertObjectsToFile(file, header, data, tails, Piped.class);
    }

    /**
     * Creates a {@code String} object representing each item in the received object lists and append it into a file
     * 
     * @param        <H> Header type
     * @param        <D> Data type
     * @param file   path file to save string representations of the value of each item in the received object lists
     * @param header objects to be appended into the fist rows of the file
     * @param data   objects to be appended into after header rows
     */
    public <H, D> void objectsToFileHD(String file, Stack<H> header, Stack<D> data) {
	this.objectsToFile(file, header, data, null);
    }

    /**
     * Creates a {@code String} object representing each item in the received object lists and append it into a file
     * 
     * @param       <D> Data type
     * @param       <T> Tail type
     * @param file  path file to save string representations of the value of each item in the received object lists
     * @param data  objects to be appended into after header rows
     * @param tails objects to be appended into the last rows of the file
     */
    public <D, T> void objectsToFileDT(String file, Stack<D> data, Stack<T> tails) {
	this.objectsToFile(file, null, data, tails);
    }

    /**
     * Creates a {@code String} object representing each item in the received object list and append it into a file
     * 
     * @param      <D> Data type
     * @param file path file to save string representations of the value of each item in the received object list
     * @param data objects to be appended into the file
     */
    public <D> void objectsToFile(String file, Stack<D> data) {
	this.objectsToFile(file, null, data, null);
    }

}
