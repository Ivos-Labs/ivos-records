/**
 * 
 */
package com.ivoslabs.records.tests.piped.dtos;

import java.util.Date;

import com.ivoslabs.records.annontation.Converter;
import com.ivoslabs.records.annontation.PipedField;
import com.ivoslabs.records.converters.DateLatinConverver;
import com.ivoslabs.records.tests.commons.dtos.SubConverter;
import com.ivoslabs.records.tests.commons.dtos.SubField;

/**
 * @since
 * @author VI9XXL8
 *
 */
public class PipedDataFSsDTO {

    @PipedField(value = 0, maxSize = 3)
    private String field1;

    @PipedField(1)
    private Integer field2;

    @PipedField(2)
    private int field3;

    @PipedField(value = 3, maxSize = 12, fixedSize = true)
    private Boolean field4;

    @PipedField(value = 4, maxSize = 15, fixedSize = true)
    private Double field5;

    @Converter(DateLatinConverver.class)
    @PipedField(value = 5, maxSize = 20, fixedSize = true, rightAlign = true)
    private Date field6;

    @Converter(SubConverter.class)
    @PipedField(6)
    private SubField field7;

    /**
     * Crea una instancia PipedDataFSsDTO
     */
    public PipedDataFSsDTO() {
        super();
    }

    /**
     * Gets the field1
     *
     * @return {@code String} The field1
     */
    public String getField1() {
        return this.field1;
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
     * Gets the field3
     *
     * @return {@code int} The field3
     */
    public int getField3() {
        return this.field3;
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
     * Gets the field5
     *
     * @return {@code Double} The field5
     */
    public Double getField5() {
        return this.field5;
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
     * Gets the field7
     *
     * @return {@code SubField} The field7
     */
    public SubField getField7() {
        return this.field7;
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
     * Sets the field2
     *
     * @param field2 {@code Integer} The field2 to set
     */
    public void setField2(Integer field2) {
        this.field2 = field2;
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
     * Sets the field4
     *
     * @param field4 {@code Boolean} The field4 to set
     */
    public void setField4(Boolean field4) {
        this.field4 = field4;
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
     * Sets the field6
     *
     * @param field6 {@code Date} The field6 to set
     */
    public void setField6(Date field6) {
        this.field6 = field6;
    }

    /**
     * Sets the field7
     *
     * @param field7 {@code SubField} The field7 to set
     */
    public void setField7(SubField field7) {
        this.field7 = field7;
    }

}