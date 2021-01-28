/**
 *
 */
package com.ivoslabs.records.dtos;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ivoslabs.records.converters.FieldConverter;
import com.ivoslabs.records.utils.ParseUtils;

/**
 * DTO
 *
 * @author www.ivoslabs.com
 *
 */
public abstract class BaseClass {

    /** The constant logger */
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseClass.class);

    /** Map of the class that requires converter and their converter */
    private Map<Class<?>, FieldConverter<?>> fieldConverterMap = new HashMap<>();

    /**
     * Creates a BaseClass instance
     */
    public BaseClass() {
        super();
    }

    /**
     *
     * @return
     * @since
     * @author www.ivoslabs.com
     *
     */
    public abstract Collection<? extends BaseField> getFieldParseDTOs();

    /**
     * Gets the FieldConverter
     *
     * @param clazz type of the required converter
     * @return the converter
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public FieldConverter<?> getConverter(Class<? extends FieldConverter<?>> clazz) {
        return this.fieldConverterMap.computeIfAbsent(clazz, k -> ParseUtils.newInstance(clazz));
    }

    /**
     *
     */

}
