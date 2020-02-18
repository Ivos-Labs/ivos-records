/**
 * 
 */
package com.ivoslabs.records.tests.piped.dtos;

import com.ivoslabs.records.annontation.Piped;

/**
 * @author
 *
 */
public class PipedHeader {

    @Piped(0)
    private String field1;

    @Piped(1)
    private Integer field2;

    /**
     * Gets the field1
     *
     * @return {@code String} The field1
     */
    public String getField1() {
	return this.field1;
    }

    /**
     * Sets the field1
     *
     * @param field1 {@code String} The field1 to set
     */
    public void setField1(String field1) {
	this.field1 = field1;
    }

    /**
     * Gets the field2
     *
     * @return {@code Integer} The field2
     */
    public Integer getField2() {
	return this.field2;
    }

    /**
     * Sets the field2
     *
     * @param field2 {@code Integer} The field2 to set
     */
    public void setField2(Integer field2) {
	this.field2 = field2;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "PipedHeader [field1=" + field1 + ", field2=" + field2 + "]";
    }

}
