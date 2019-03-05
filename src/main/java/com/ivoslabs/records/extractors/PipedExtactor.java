/**
 * 
 */
package com.ivoslabs.records.extractors;

import java.util.List;

import com.ivoslabs.records.annontation.PipedField;
import com.ivoslabs.records.core.Extractor;

/**
 * @author www.ivoslabs.com
 *
 */
public class PipedExtactor {

    public <T extends Object> T convert(String data, Class<T> type) {
	return Extractor.convert(data, type, PipedField.class);
    }

    public <T extends Object> List<T> convert(List<String> data, Class<T> type) {
	return Extractor.convert(data, type, PipedField.class);
    }

}