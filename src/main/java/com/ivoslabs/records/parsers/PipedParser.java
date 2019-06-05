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
 * @author www.ivoslabs.com
 *
 */
public class PipedParser {

    /**
     * 
     * @param data
     * @param type
     * @return
     */
    public <T> T toObject(String data, Class<T> type) {
	return Extractor.convertStringToObject(data, type, PipedField.class);
    }

    /**
     * 
     * @param data
     * @param type
     * @return
     */
    public <T> List<T> toObjects(List<String> data, Class<T> type) {
	return Extractor.convertStringsToObjects(data, type, PipedField.class);
    }

    public <T, U, V> void fileToObjects(String file,
	    Class<T> headerType,
	    Integer headerSize,
	    ObjectConsumer<T> headerConsumer,
	    Class<U> dataType,
	    ObjectConsumer<U> dataConsumer,
	    Class<V> tailType,
	    Integer tailSize,
	    ObjectConsumer<V> tailConsumer) {

	Extractor.convertFileToObjects(file, headerType, headerSize, headerConsumer, dataType, dataConsumer, tailType, tailSize, tailConsumer, PipedField.class);
    }

    public <T, U, V> void fileToObjects(String file,
	    Class<T> headerType,
	    Integer headerSize,
	    ObjectConsumer<T> headerConsumer,
	    Class<U> dataType,
	    ObjectConsumer<U> dataConsumer) {

	Extractor.convertFileToObjects(file, headerType, headerSize, headerConsumer, dataType, dataConsumer, PipedField.class);
    }

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
     * @param type
     * @param action
     */
    public <T> void fileToObjects(String file, Class<T> dataType, ObjectConsumer<T> dataConsumer) {
	Extractor.convertFileToObjects(file, dataType, dataConsumer, PipedField.class);
    }

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

    /**
     * 
     */

    /**
     * 
     */

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
