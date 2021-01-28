/**
 *
 */
package com.ivoslabs.records.dtos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author www.ivoslabs.com
 *
 */
public class CopyClass extends BaseClass {

    /** The fieldParseDTOs */
    private List<CopyField> fieldParseDTOs = new ArrayList<>();

    /** Size (used in the Object to String process) */
    private Integer size;

    /**
     *
     * @param annon
     * @since 1.0.0
     * @author www.ivoslabs.mx
     */
    CopyClass() {
        super();
    }

    /**
     * Gets the fieldParseDTOs
     *
     * @return the fieldParseDTOs
     */
    @Override
    public Collection<CopyField> getFieldParseDTOs() {
        return this.fieldParseDTOs;
    }

    /**
     * Appends the specified element to the end of this fieldParseDTOs list
     *
     * @param fieldParseDTO
     */
    public void add(CopyField fieldParseDTO) {
        this.fieldParseDTOs.add(fieldParseDTO);
    }

    /**
     * Gets the size
     *
     * @return {@code Integer} The size
     */
    public Integer getSize() {
        return this.size;
    }

    /**
     * Sets the size
     *
     * @param size {@code Integer} The size to set
     */
    public void setSize(Integer size) {
        this.size = size;
    }

}
