/**
 *
 */
package com.ivoslabs.records.dtos;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ivoslabs.records.annontation.Converter;
import com.ivoslabs.records.annontation.IfNull;
import com.ivoslabs.records.annontation.Pic;
import com.ivoslabs.records.annontation.Piped;
import com.ivoslabs.records.core.FileType;
import com.ivoslabs.records.utils.ParseUtils;

/**
 * @since 1.0.0
 * @author www.ivoslabs.com
 *
 */
public class DTOFactory {

    private DTOFactory() {
        super();
    }

    /**
     * Gets the BaseClass with Converters and default values to parse Objects to Strings and vice versa
     *
     * @param type    Class to use
     * @param annon   indicates the type of fields to read, Pic or PipedField
     * @param isToObj indicates if the tampleate will be used to parse String to Object
     * @return the ClassParseDTO generated
     * @since 1.0.0
     * @author www.ivoslabs.com
     */
    public static BaseClass getTemplate(Class<?> type, FileType annon, boolean isToObj) {

        BaseClass template = annon == FileType.COPY ? new CopyClass() : new PipedClass();

        Field[] fields = type.getDeclaredFields();

        // generic if-null value
        IfNull genIfNull = type.getDeclaredAnnotation(IfNull.class);
        String genIfNullValue = genIfNull != null ? genIfNull.value() : ParseUtils.EMPTY;

        List<PipedField> pipedFields = new ArrayList<>();
        List<CopyField> copyFields = new ArrayList<>();

        for (Field field : fields) {

            IfNull ifNull = field.getDeclaredAnnotation(IfNull.class);
            String ifNullValue = ifNull != null ? ifNull.value() : genIfNullValue;

            if (template instanceof PipedClass) {
                Piped pf = field.getDeclaredAnnotation(Piped.class);
                if (pf != null) {
                    Integer maxSize = pf.maxSize() > 0 ? pf.maxSize() : null;
                    pipedFields.add(new PipedField(field, pf, field.getAnnotation(Converter.class), ifNullValue, maxSize));
                }
            } else {
                Pic pic = field.getAnnotation(Pic.class);
                if (pic != null) {
                    CopyField copyField = new CopyField(field, pic, field.getAnnotation(Converter.class), ifNullValue);
                    copyFields.add(copyField);
                    ((CopyClass) template).add(copyField);
                }
            }

        }

        if (pipedFields.isEmpty() && copyFields.isEmpty()) {
            throw new IllegalArgumentException("Class: " + type.getCanonicalName() + " doesn't have PipedField or Pic fields");
        }

        if (template instanceof PipedClass) {

            if (!isToObj) {
                // order only in the Object to String process
                Collections.sort(pipedFields, (o1, o2) -> Integer.compare(o1.getPipeField().value(), o2.getPipeField().value()));
            }

            for (PipedField extract : pipedFields) {
                ((PipedClass) template).addFieldParserDTO(extract.getPipeField().value(), extract);
            }

            // use the value of the last field in the array ordered by value, don't use the size of the array because
            // if a index were not mapped with a field, the size won't match with the value of last field
            // Class e.g.:
            // ...PipedField(0)
            // ...private Integer field1
            //
            // ...PipedField(2)
            // ...private int field2
            //
            // piped line e.g:
            // field1Value|nomappedField|field2Value
            if (!isToObj) {
                ((PipedClass) template).setLastIndex(pipedFields.get(pipedFields.size() - 1).getPipeField().value());
            }

        } else if (!isToObj) {
            // order only in the Object to String process
            Collections.sort(copyFields, (o1, o2) -> Integer.compare(o1.getPic().beginIndex(), o2.getPic().beginIndex()));

            CopyField last = copyFields.get(copyFields.size() - 1);

            ((CopyClass) template).setSize(last.getPic().beginIndex() + last.getPic().size());

        }

        return template;

    }
}
