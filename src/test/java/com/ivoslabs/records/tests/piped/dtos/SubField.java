/**
 * 
 */
package com.ivoslabs.records.tests.piped.dtos;

/**
 * 
 * @author www.ivoslabs.com
 *
 */
public class SubField {

    /** */
    private String a;

    private String b;

    /**
     * 
     */
    public SubField() {
	super();
    }

    /**
     * @param a
     * @param b
     */
    public SubField(String a, String b) {
	super();
	this.a = a;
	this.b = b;
    }

    /**
     * Gets the a
     *
     * @return The a
     */
    public String getA() {
	return this.a;
    }

    /**
     * Sets the a
     *
     * @param a The a to set
     */
    public void setA(String a) {
	this.a = a;
    }

    /**
     * Gets the b
     *
     * @return The b
     */
    public String getB() {
	return this.b;
    }

    /**
     * Sets the b
     *
     * @param b The b to set
     */
    public void setB(String b) {
	this.b = b;
    }

    /*
     * 
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "SubField [a=" + a + ", b=" + b + "]";
    }

}
