/**
 * 
 */
package com.ivoslabs.records.core;

import java.util.ArrayList;
import java.util.List;

/**
 * @author www.ivoslabs.com
 *
 */
public class Template {

    private Type type;

    public enum Type {
	PIC, PIPE
    }

    public Template(Type type) {
	this.type = type;
    }

    private List<Extract> extracts = new ArrayList<Extract>();

    void add(Extract extract) {
	this.extracts.add(extract);
    }

    List<Extract> getExtracts() {
	return this.extracts;
    }

    /**
     * Gets the Type
     *
     * @return {@code Type} The type
     */
    public Type getType() {
	return this.type;
    }

}
