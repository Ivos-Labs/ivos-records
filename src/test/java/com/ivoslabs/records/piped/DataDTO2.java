package com.ivoslabs.records.piped;
/**
 * 
 */

import com.ivoslabs.records.annontation.Pic;

/**
 * @author www.ivoslabs.com
 *
 */
public class DataDTO2 {

    /** */
    @Pic(beginIndex = 0, size = 1)
    private String field;

    @Pic(beginIndex = 1, size = 1)
    private Integer field2;

    @Pic(beginIndex = 2, size = 1)
    private Boolean field3;

    @Pic(beginIndex = 3, size = 3)
    private Double field4;

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "DataDTO [field=" + field + ", field2=" + field2 + ", field3=" + field3 + ", field4=" + field4 + "]";
    }

}
