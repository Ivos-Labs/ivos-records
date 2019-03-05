/**
 * 
 */
package com.ivoslabs.records.piped;

import com.ivoslabs.records.annontation.PipedField;
import com.ivoslabs.records.converters.Boolean10Converter;

/**
 * @author www.ivoslabs.com
 *
 */
public class PipedDTO {

    @PipedField(0)
    private String field;

    @PipedField(1)
    private Integer field2;

    @PipedField(2)
    private Boolean field3;

    @PipedField(value =3, converter= Boolean10Converter.class)
    private Double field4;

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "PipedDTO [field=" + field + ", field2=" + field2 + ", field3=" + field3 + ", field4=" + field4 + "]";
    }

}
