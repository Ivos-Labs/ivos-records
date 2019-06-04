/**
 * 
 */
package com.ivoslabs.records.parsers;

import java.util.List;
import java.util.Stack;

import com.ivoslabs.records.annontation.Pic;
import com.ivoslabs.records.core.Extractor;
import com.ivoslabs.records.core.ObjectConsumer;

/**
 * @author www.ivoslabs.com
 *
 */
public class CopyParser {

    /**
     * 
     * @param data
     * @param type
     * @return
     */
    public <T> T toObject(String data, Class<T> type) {
	return Extractor.convertStringToObject(data, type, Pic.class);
    }

    /**
     * 
     * @param data
     * @param type
     * @return
     */
    public <T> List<T> toObjects(List<String> data, Class<T> type) {
	return Extractor.convertStringsToObjects(data, type, Pic.class);
    }

    /**
     * 
     * @param data
     * @return
     */
    public String toString(Object data) {
	return Extractor.convertObjectToString(data, Pic.class);
    }

    /**
     * 
     * @param data
     * @return
     */
    public List<String> toStrings(List<?> data) {
	return Extractor.convertObjectsToStrings(data, Pic.class);
    }

    /**
     * 
     * @param file
     * @param type
     * @param action
     */
    public <T> void fileToObjects(String file, Class<T> type, ObjectConsumer<T> action) {
	Extractor.convertFileToObjects(file, type, action, Pic.class);
    }

    /**
     * 
     * @param file
     * @param type
     * @param action
     */
    public <T> void objectsToFile(String file, Stack<T> objects) {
	Extractor.convertObjectsToFile(file, objects, Pic.class);
    }
}
