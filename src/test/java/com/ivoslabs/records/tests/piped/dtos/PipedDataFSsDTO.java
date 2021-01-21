/**
 *
 */
package com.ivoslabs.records.tests.piped.dtos;

import java.util.Date;

import com.ivoslabs.records.annontation.Converter;
import com.ivoslabs.records.annontation.Piped;
import com.ivoslabs.records.converters.DateLatinConverver;
import com.ivoslabs.records.tests.commons.dtos.SubConverter;
import com.ivoslabs.records.tests.commons.dtos.SubField;

/**
 * @since
 * @author imperezivan
 *
 */
public class PipedDataFSsDTO {

    @Piped(value = 0)
    private String field1;

    @Piped(value = 1, maxSize = 3)
    private String field2;

    @Piped(value = 2, maxSize = 3, fixedSize = true)
    private String field3;

    @Piped(value = 3, maxSize = 3, fixedSize = true, rightAlign = true)
    private String field4;

    //

    @Piped(value = 4)
    private String field5;

    @Piped(value = 5, maxSize = 3)
    private String field6;

    @Piped(value = 6, maxSize = 3, fixedSize = true)
    private String field7;

    @Piped(value = 7, maxSize = 3, fixedSize = true, rightAlign = true)
    private String field8;

    // Integer

    @Piped(value = 8)
    private Integer field9;

    @Piped(value = 9, maxSize = 3)
    private Integer field10;

    @Piped(value = 10, maxSize = 5, fixedSize = true)
    private Integer field11;

    @Piped(value = 11, maxSize = 5, fixedSize = true, rightAlign = true)
    private Integer field12;

    @Piped(value = 12)
    private Integer field13;

    @Piped(value = 13, maxSize = 3)
    private Integer field14;

    @Piped(value = 14, maxSize = 3, fixedSize = true)
    private Integer field15;

    @Piped(value = 15, maxSize = 3, fixedSize = true, rightAlign = true)
    private Integer field16;

    @Piped(16)
    private int field17;

    @Piped(value = 17, maxSize = 12, fixedSize = true)
    private Boolean field18;

    @Piped(value = 18, maxSize = 15, fixedSize = true)
    private Double field19;

    @Converter(DateLatinConverver.class)
    @Piped(value = 19, maxSize = 20, fixedSize = true, rightAlign = true)
    private Date field20;

    @Converter(SubConverter.class)
    @Piped(20)
    private SubField field21;

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
     * @return {@code String} The field2
     */
    public String getField2() {
        return this.field2;
    }

    /**
     * Gets the field3
     *
     * @return {@code String} The field3
     */
    public String getField3() {
        return this.field3;
    }

    /**
     * Gets the field4
     *
     * @return {@code String} The field4
     */
    public String getField4() {
        return this.field4;
    }

    /**
     * Gets the field5
     *
     * @return {@code String} The field5
     */
    public String getField5() {
        return this.field5;
    }

    /**
     * Gets the field6
     *
     * @return {@code String} The field6
     */
    public String getField6() {
        return this.field6;
    }

    /**
     * Gets the field7
     *
     * @return {@code String} The field7
     */
    public String getField7() {
        return this.field7;
    }

    /**
     * Gets the field8
     *
     * @return {@code String} The field8
     */
    public String getField8() {
        return this.field8;
    }

    /**
     * Gets the field9
     *
     * @return {@code Integer} The field9
     */
    public Integer getField9() {
        return this.field9;
    }

    /**
     * Gets the field10
     *
     * @return {@code Integer} The field10
     */
    public Integer getField10() {
        return this.field10;
    }

    /**
     * Gets the field11
     *
     * @return {@code Integer} The field11
     */
    public Integer getField11() {
        return this.field11;
    }

    /**
     * Gets the field12
     *
     * @return {@code Integer} The field12
     */
    public Integer getField12() {
        return this.field12;
    }

    /**
     * Gets the field13
     *
     * @return {@code Integer} The field13
     */
    public Integer getField13() {
        return this.field13;
    }

    /**
     * Gets the field14
     *
     * @return {@code Integer} The field14
     */
    public Integer getField14() {
        return this.field14;
    }

    /**
     * Gets the field15
     *
     * @return {@code Integer} The field15
     */
    public Integer getField15() {
        return this.field15;
    }

    /**
     * Gets the field16
     *
     * @return {@code Integer} The field16
     */
    public Integer getField16() {
        return this.field16;
    }

    /**
     * Gets the field17
     *
     * @return {@code int} The field17
     */
    public int getField17() {
        return this.field17;
    }

    /**
     * Gets the field18
     *
     * @return {@code Boolean} The field18
     */
    public Boolean getField18() {
        return this.field18;
    }

    /**
     * Gets the field19
     *
     * @return {@code Double} The field19
     */
    public Double getField19() {
        return this.field19;
    }

    /**
     * Gets the field20
     *
     * @return {@code Date} The field20
     */
    public Date getField20() {
        return this.field20;
    }

    /**
     * Gets the field21
     *
     * @return {@code SubField} The field21
     */
    public SubField getField21() {
        return this.field21;
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
     * @param field2 {@code String} The field2 to set
     */
    public void setField2(String field2) {
        this.field2 = field2;
    }

    /**
     * Sets the field3
     *
     * @param field3 {@code String} The field3 to set
     */
    public void setField3(String field3) {
        this.field3 = field3;
    }

    /**
     * Sets the field4
     *
     * @param field4 {@code String} The field4 to set
     */
    public void setField4(String field4) {
        this.field4 = field4;
    }

    /**
     * Sets the field5
     *
     * @param field5 {@code String} The field5 to set
     */
    public void setField5(String field5) {
        this.field5 = field5;
    }

    /**
     * Sets the field6
     *
     * @param field6 {@code String} The field6 to set
     */
    public void setField6(String field6) {
        this.field6 = field6;
    }

    /**
     * Sets the field7
     *
     * @param field7 {@code String} The field7 to set
     */
    public void setField7(String field7) {
        this.field7 = field7;
    }

    /**
     * Sets the field8
     *
     * @param field8 {@code String} The field8 to set
     */
    public void setField8(String field8) {
        this.field8 = field8;
    }

    /**
     * Sets the field9
     *
     * @param field9 {@code Integer} The field9 to set
     */
    public void setField9(Integer field9) {
        this.field9 = field9;
    }

    /**
     * Sets the field10
     *
     * @param field10 {@code Integer} The field10 to set
     */
    public void setField10(Integer field10) {
        this.field10 = field10;
    }

    /**
     * Sets the field11
     *
     * @param field11 {@code Integer} The field11 to set
     */
    public void setField11(Integer field11) {
        this.field11 = field11;
    }

    /**
     * Sets the field12
     *
     * @param field12 {@code Integer} The field12 to set
     */
    public void setField12(Integer field12) {
        this.field12 = field12;
    }

    /**
     * Sets the field13
     *
     * @param field13 {@code Integer} The field13 to set
     */
    public void setField13(Integer field13) {
        this.field13 = field13;
    }

    /**
     * Sets the field14
     *
     * @param field14 {@code Integer} The field14 to set
     */
    public void setField14(Integer field14) {
        this.field14 = field14;
    }

    /**
     * Sets the field15
     *
     * @param field15 {@code Integer} The field15 to set
     */
    public void setField15(Integer field15) {
        this.field15 = field15;
    }

    /**
     * Sets the field16
     *
     * @param field16 {@code Integer} The field16 to set
     */
    public void setField16(Integer field16) {
        this.field16 = field16;
    }

    /**
     * Sets the field17
     *
     * @param field17 {@code int} The field17 to set
     */
    public void setField17(int field17) {
        this.field17 = field17;
    }

    /**
     * Sets the field18
     *
     * @param field18 {@code Boolean} The field18 to set
     */
    public void setField18(Boolean field18) {
        this.field18 = field18;
    }

    /**
     * Sets the field19
     *
     * @param field19 {@code Double} The field19 to set
     */
    public void setField19(Double field19) {
        this.field19 = field19;
    }

    /**
     * Sets the field20
     *
     * @param field20 {@code Date} The field20 to set
     */
    public void setField20(Date field20) {
        this.field20 = field20;
    }

    /**
     * Sets the field21
     *
     * @param field21 {@code SubField} The field21 to set
     */
    public void setField21(SubField field21) {
        this.field21 = field21;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "PipedDataFSsDTO [field1=" + field1 + ", field2=" + field2 + ", field3=" + field3 + ", field4=" + field4 + ", field5=" + field5 + ", field6=" + field6 + ", field7=" + field7 + ", field8=" + field8 + ", field9=" + field9 + ", field10=" + field10 + ", field11=" + field11 + ", field12=" + field12 + ", field13=" + field13 + ", field14=" + field14 + ", field15=" + field15 + ", field16="
                + field16 + ", field17=" + field17 + ", field18=" + field18 + ", field19=" + field19 + ", field20=" + field20 + ", field21=" + field21 + "]";
    }

}
