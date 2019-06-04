/**
 * 
 */
package com.ivoslabs.records.parsers;

import java.util.List;
import java.util.Stack;

import com.ivoslabs.records.annontation.PipedField;
import com.ivoslabs.records.core.Extractor;
import com.ivoslabs.records.core.ObjectConsumer;

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

    /**
     * 
     * @param file
     * @param type
     * @param action
     */
    public <T> void fileToObjects(String file, Class<T> type, ObjectConsumer<T> action) {
	Extractor.convertFileToObjects(file, type, action, PipedField.class);
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
     * @param file
     * @param type
     * @param action
     */
    public <T> void objectsToFile(String file, Stack<T> objects) {
	Extractor.convertObjectsToFile(file, objects, PipedField.class);
    }

}
