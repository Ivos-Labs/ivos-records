/**
 * 
 */
package com.ivoslabs.records.tests.copy.dtos;

import com.ivoslabs.records.annontation.Pic;

/**
 * @author www.ivoslabs.com
 *
 */
public class CopyHeader {

    @Pic(beginIndex = 0, size = 7)
    private String field1;

    @Pic(beginIndex = 7, size = 5)
    private int field2;

    @Pic(beginIndex = 12, size = 5)
    private Integer field3;

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
     * @return {@code int} The field2
     */
    public int getField2() {
	return this.field2;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "CopyHeader [field1=" + field1 + ", field2=" + field2 + ", field3=" + field3 + "]";
    }

    /**
     * Gets the field3
     *
     * @return {@code Integer} The field3
     */
    public Integer getField3() {
	return this.field3;
    }

    /**
     * Sets the field3
     *
     * @param field3 {@code Integer} The field3 to set
     */
    public void setField3(Integer field3) {
	this.field3 = field3;
    }

    /**
     * Sets the field2
     *
     * @param field2 {@code int} The field2 to set
     */
    public void setField2(int field2) {
	this.field2 = field2;
    }

}
