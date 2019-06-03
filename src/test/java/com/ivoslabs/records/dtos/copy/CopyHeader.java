/**
 * 
 */
package com.ivoslabs.records.dtos.copy;

import com.ivoslabs.records.annontation.Pic;

/**
 * @author www.ivoslabs.com
 *
 */
public class CopyHeader {

    /** */
    @Pic(beginIndex = 0, size = 5)
    private String field1;

    /** */
    @Pic(beginIndex = 6, size = 10)
    private String field2;

}
