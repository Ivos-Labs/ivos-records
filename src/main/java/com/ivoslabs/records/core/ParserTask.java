/**
 *
 */
package com.ivoslabs.records.core;

import java.util.function.Consumer;

/**
 * @since
 * @author
 *
 */
public class ParserTask<H, D, T> {

//    * @param file           file path
//    * @param headerType     header type
//    * @param headerSize     number of first rows to be processed with headerType and headerConsumer
//    * @param headerConsumer action to do for each header instance
//    * @param dataType       data type
//    * @param dataConsumer   action to do for each data instance
//    *
//    * @param tailType       tail type
//    * @param tailSize       number of last rows to be processed with tailType and tailConsumer
//    * @param tailConsumer   action to do for each tail instance

    private String file;

    // header
    private Class<H> headerType;
    private Integer headerSize;
    private Consumer<H> headerConsumer;
    // data
    private Class<D> dataType;
    private Consumer<D> dataConsumer;

    // tail
    private Class<T> tailType;
    private Integer tailSize;
    private Consumer<T> tailConsumer;

    /**
     * Creates a ParserTask instance
     */
    public ParserTask(String file, Class<D> dataType, Consumer<D> dataConsumer) {
        super();
        this.file = file;
        this.dataType = dataType;
        this.dataConsumer = dataConsumer;
    }

    public void setHeaderInfo(Class<H> headerType, Integer headerSize, Consumer<H> headerConsumer) {
        this.headerType = headerType;
        this.headerSize = headerSize;
        this.headerConsumer = headerConsumer;
    }

    public void setTailInfo(Class<T> tailType, Integer tailSize, Consumer<T> tailConsumer) {
        this.tailType = tailType;
        this.tailSize = tailSize;
        this.tailConsumer = tailConsumer;
    }

    /**
     * Gets the file
     *
     * @return {@code String} The file
     */
    public String getFile() {
        return this.file;
    }

    /**
     * Gets the headerType
     *
     * @return {@code Class<H>} The headerType
     */
    public Class<H> getHeaderType() {
        return this.headerType;
    }

    /**
     * Gets the headerSize
     *
     * @return {@code Integer} The headerSize
     */
    public Integer getHeaderSize() {
        return this.headerSize;
    }

    /**
     * Gets the headerConsumer
     *
     * @return {@code Consumer<H>} The headerConsumer
     */
    public Consumer<H> getHeaderConsumer() {
        return this.headerConsumer;
    }

    /**
     * Gets the dataType
     *
     * @return {@code Class<D>} The dataType
     */
    public Class<D> getDataType() {
        return this.dataType;
    }

    /**
     * Gets the dataConsumer
     *
     * @return {@code Consumer<D>} The dataConsumer
     */
    public Consumer<D> getDataConsumer() {
        return this.dataConsumer;
    }

    /**
     * Gets the tailType
     *
     * @return {@code Class<T>} The tailType
     */
    public Class<T> getTailType() {
        return this.tailType;
    }

    /**
     * Gets the tailSize
     *
     * @return {@code Integer} The tailSize
     */
    public Integer getTailSize() {
        return this.tailSize;
    }

    /**
     * Gets the tailConsumer
     *
     * @return {@code Consumer<T>} The tailConsumer
     */
    public Consumer<T> getTailConsumer() {
        return this.tailConsumer;
    }

}
