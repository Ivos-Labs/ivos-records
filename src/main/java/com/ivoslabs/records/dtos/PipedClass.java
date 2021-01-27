/**
 * 
 */
package com.ivoslabs.records.dtos;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author www.ivoslabs.com
 *
 */
public class PipedClass extends BaseClass {

    /** Map of Extracts by index (used in piped file) **/
    private Map<Integer, PipedField> fieldParserDTOMap = new HashMap<Integer, PipedField>();

    /** Last index (used in the Object to String process) */
    private Integer lastIndex;

    /**
     * 
     * @param annon
     * @since 1.0.0
     * @author www.ivoslabs.mx
     */
    PipedClass() {
        super();
    }

    /**
     * Gets a FieldParseDTO by index (used in piped file)
     * 
     * @return {@code FieldParseDTO} the FieldParseDTO
     */
    public PipedField getFieldParseDTO(int index) {
        return this.fieldParserDTOMap.get(index);
    }

    /**
     * 
     * 
     * Add a FieldParseDTO to the fieldParserDTOMap (used in piped file)
     *
     * @param key            index of the FieldParseDTO field
     * @param fieldParserDTO the {@code FieldParseDTO} to add
     * 
     */
    public void addFieldParserDTO(int key, PipedField fieldParserDTO) {
        this.fieldParserDTOMap.put(key, fieldParserDTO);
    }

    /**
     * Gets the lastIndex
     * 
     * @return {@code Integer} The lastIndex
     */
    public Integer getLastIndex() {
        return this.lastIndex;
    }

    /**
     * Sets the lastIndex
     *
     * @param lastIndex {@code Integer} The lastIndex to set
     */
    public void setLastIndex(Integer lastIndex) {
        this.lastIndex = lastIndex;
    }

    /*
     * 
     * (non-Javadoc)
     * 
     * @see com.ivoslabs.records.dtos.BaseClass#getFieldParseDTOs()
     */
    @Override
    public Collection<PipedField> getFieldParseDTOs() {
        return fieldParserDTOMap.values();
    }

}
