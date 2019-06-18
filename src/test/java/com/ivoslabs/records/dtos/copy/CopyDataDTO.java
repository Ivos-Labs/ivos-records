/**
 * 
 */
package com.ivoslabs.records.dtos.copy;

import java.util.Date;

import com.ivoslabs.records.annontation.Converter;
import com.ivoslabs.records.annontation.Pic;
import com.ivoslabs.records.converters.Boolean10Converter;
import com.ivoslabs.records.converters.DateLatinConverver;

/**
 * @author www.ivoslabs.com
 *
 */
public class CopyDataDTO {

    @Pic(beginIndex = 0, size = 1)
    private String field;

    @Pic(beginIndex = 1, size = 1)
    private Integer field2;

    @Pic(beginIndex = 2, size = 1)
    private int field3;

    @Converter(Boolean10Converter.class)
    @Pic(beginIndex = 3, size = 1)
    private Boolean field4;

    @Pic(beginIndex = 4, size = 3)
    private Double field5;

    @Converter(DateLatinConverver.class)
    @Pic(beginIndex = 7, size = 14)
    private Date field6;

    /**
     * Gets the field
     *
     * @return {@code String} The field
     */
    public String getField() {
	return this.field;
    }

    /**
     * Sets the field
     *
     * @param field {@code String} The field to set
     */
    public void setField(String field) {
	this.field = field;
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

    /**
     * Gets the field3
     *
     * @return {@code int} The field3
     */
    public int getField3() {
	return this.field3;
    }

    /**
     * Sets the field3
     *
     * @param field3 {@code int} The field3 to set
     */
    public void setField3(int field3) {
	this.field3 = field3;
    }

    /**
     * Gets the field4
     *
     * @return {@code Boolean} The field4
     */
    public Boolean getField4() {
	return this.field4;
    }

    /**
     * Sets the field4
     *
     * @param field4 {@code Boolean} The field4 to set
     */
    public void setField4(Boolean field4) {
	this.field4 = field4;
    }

    /**
     * Gets the field5
     *
     * @return {@code Double} The field5
     */
    public Double getField5() {
	return this.field5;
    }

    /**
     * Sets the field5
     *
     * @param field5 {@code Double} The field5 to set
     */
    public void setField5(Double field5) {
	this.field5 = field5;
    }

    /**
     * Gets the field6
     *
     * @return {@code Date} The field6
     */
    public Date getField6() {
	return this.field6;
    }

    /**
     * Sets the field6
     *
     * @param field6 {@code Date} The field6 to set
     */
    public void setField6(Date field6) {
	this.field6 = field6;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "CopyOkDTO [field=" + field + ", field2=" + field2 + ", field3=" + field3 + ", field4=" + field4 + ", field5=" + field5 + ", field6=" + field6 + "]";
    }

}