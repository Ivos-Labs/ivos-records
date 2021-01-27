/**
 *
 */
package com.ivoslabs.records.dtos;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ivoslabs.records.converters.FieldConverter;

/**
 * DTO
 *
 * @author www.ivoslabs.com
 *
 */
public abstract class BaseClass {

    /** Map of the class that requires converter and their converter */
    private Map<Class<?>, FieldConverter<?>> fieldConverterMap = new HashMap<Class<?>, FieldConverter<?>>();

    /**
     * Gets the FieldConverter
     *
     * @param clazz type of the required converter
     * @return the converter
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public FieldConverter<?> getConverter(Class<? extends FieldConverter<?>> clazz) throws InstantiationException, IllegalAccessException {
        FieldConverter<?> c = this.fieldConverterMap.get(clazz);

        if (c == null) {
            c = clazz.newInstance();
            this.fieldConverterMap.put(clazz, c);
        }

        return c;
    }

    /**
     *
     * @return
     * @since
     * @author www.ivoslabs.com
     *
     */
    abstract public Collection<? extends BaseField> getFieldParseDTOs();
}
