/**
 * 
 */
package com.ivoslabs.records.tests.piped.dtos;

import com.ivoslabs.records.annontation.Piped;

/**
 * @author
 *
 */
public class PipedTail {

    @Piped(0)
    private Integer field1;

    @Piped(1)
    private String field2;

    /**
     * Gets the field1
     *
     * @return {@code Integer} The field1
     */
    public Integer getField1() {
	return this.field1;
    }

    /**
     * Sets the field1
     *
     * @param field1 {@code Integer} The field1 to set
     */
    public void setField1(Integer field1) {
	this.field1 = field1;
    }

    /**
     * Gets the field2
     *
     * @return {@code String} The field2
     */
    public String getField2() {
	return this.field2;
    }

    /**
     * Sets the field2
     *
     * @param field2 {@code String} The field2 to set
     */
    public void setField2(String field2) {
	this.field2 = field2;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "PipedTail [field1=" + field1 + ", field2=" + field2 + "]";
    }

}
