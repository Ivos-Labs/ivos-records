package com.ivoslabs.records.core;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.mutable.Mutable;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.commons.lang3.mutable.MutableObject;

import com.ivoslabs.records.annontation.Converter;
import com.ivoslabs.records.annontation.Pic;
import com.ivoslabs.records.converters.FieldConverter;
import com.ivoslabs.records.dtos.BaseClass;
import com.ivoslabs.records.dtos.BaseField;
import com.ivoslabs.records.dtos.CopyClass;
import com.ivoslabs.records.dtos.CopyField;
import com.ivoslabs.records.dtos.DTOFactory;
import com.ivoslabs.records.dtos.PipedClass;
import com.ivoslabs.records.dtos.PipedField;
import com.ivoslabs.records.exceptions.ParseException;
import com.ivoslabs.records.exceptions.RecordsException;
import com.ivoslabs.records.utils.LinesCounter;
import com.ivoslabs.records.utils.ParseUtils;

/**
 * @author www.ivoslabs.com
 *
 */
public class Extractor {

    private Extractor() {
        super();
    }

    /***
     * *
     ***/

    /***
     * *
     ***/

    /**********************************
     * Start String-To-Object methods *
     **********************************/

    /**
     *
     * Creates a new instance of T using the received data
     *
     * @param <T>   Required type
     * @param data  values separated by pipes
     * @param type  required type
     * @param annon annotation to indicate if is Piped or COPY file
     * @return a T instance
     */
    public static <T extends Object> T convertStringToObject(String data, Class<T> type, FileType annon) {
        Validate.notNull(data, "data must not be null");
        Validate.notNull(type, "type of the object must not be null");

        T obj;
        BaseClass extracts = DTOFactory.getTemplate(type, annon, Boolean.TRUE);
        obj = Extractor.convertStringRowToObject(data, type, extracts);

        return obj;
    }

    /**
     *
     * Creates a new instance of T using each item in the received data list
     *
     * @param <T>   Required type
     * @param data  list of values separated by pipes
     * @param type  required type
     * @param annon annotation to indicate if is Piped or COPY file
     * @return a List of T instance
     */
    public static <T extends Object> List<T> convertStringsToObjects(List<String> data, Class<T> type, FileType annon) {
        Validate.notNull(data, "list of data  must not be null");
        Validate.notNull(type, "type of the objects must not be null");

        List<T> list = new ArrayList<>();

        if (!data.isEmpty()) {
            int rowNum = 0;
            BaseClass extracts = DTOFactory.getTemplate(type, annon, Boolean.TRUE);

            try {
                for (String row : data) {
                    rowNum++;
                    T object;
                    object = Extractor.convertStringRowToObject(row, type, extracts);
                    list.add(object);

                }
            } catch (RecordsException e) {
                throw new RecordsException("An error has occurred while processing row: {}; Detail: {}", rowNum, e.getMessage(), e);
            } catch (Exception e) {
                throw new RecordsException("An error has occurred while processing row: {};", rowNum, e);
            }

        }

        return list;

    }

    /**
     *
     * Creates an instance of H, D or T for each row in a file and execute the respective received ObjectConsumer
     *
     * @param <H>        Header type
     * @param <D>        Data type
     * @param <T>        Tail type
     * @param parserTask ParserTask
     * @param condition  condition function
     * @param annon      annotation to indicate if is Piped or COPY file
     * @param findFirst  flag to indicate if is a findFirst execution
     *
     */
    public static <H, D, T> void processFile(ParserTask<H, D, T> parserTask, Predicate<D> condition, FileType annon, Boolean findFirst) {
        Validate.notNull(parserTask, "parserTask must not be null");

        String file = parserTask.getFile();
        // header
        Class<H> headerType = parserTask.getHeaderType();
        Integer headerSize = parserTask.getHeaderSize();
        // data
        Class<D> dataType = parserTask.getDataType();
        Consumer<D> dataConsumer = parserTask.getDataConsumer();
        // tail
        Class<T> tailType = parserTask.getTailType();
        Integer tailSize = parserTask.getTailSize();

        Validate.notNull(file, "file must not be null");
        Validate.notNull(dataType, "type must not be null");
        Validate.notNull(dataConsumer, "action must not be null");

        Validate.isTrue(headerType == null || (headerSize != null && headerSize > 0), "headerSize have to be greater than 0");
        Validate.isTrue(tailType == null || (tailSize != null && tailSize > 0), "tailSize have to be greater than 0");

        final BaseClass extracts = DTOFactory.getTemplate(dataType, annon, Boolean.TRUE);
        final BaseClass headerExtracts;
        final BaseClass tailExtracts;

        final Integer tailLine;

        if (headerType != null) {
            headerExtracts = DTOFactory.getTemplate(headerType, annon, Boolean.TRUE);
        } else {
            headerExtracts = null;
        }

        if (tailType != null) {
            tailLine = new LinesCounter().countLines(file) - tailSize;
            tailExtracts = DTOFactory.getTemplate(tailType, annon, Boolean.TRUE);
        } else {
            tailLine = null;
            tailExtracts = null;
        }

        final MutableInt rowNum = new MutableInt();

        RowConsumer<H, D, T> actionForRow = new RowConsumer<>(rowNum, extracts, headerExtracts, tailExtracts, parserTask, condition, tailLine);
        if (findFirst != null && findFirst) {
            actionForRow.activeFindFirst();
        }

        try {

            ParseUtils.readTextFile(file, actionForRow);
        } catch (RecordsException e) {
            throw new RecordsException("An error has occurred while processing file: {}; row: {}; detail: {};", file, rowNum.getValue(), e.getMessage(), e);
        } catch (Exception e) {
            throw new RecordsException("An error has occurred while processing file: {}; row: {};", file, rowNum.getValue(), e);
        }

    }

    /********************************
     * End String-To-Object methods *
     ********************************/

    /***
     * *
     ***/

    /***
     * *
     ***/

    /**********************************
     * Start Object-To-String methods *
     **********************************/

    /**
     *
     * Creates a {@code String} object representing the received object
     *
     * @param object the {@code Object} to be converted to a {@code Strings}
     * @param annon  annotation to indicate if is Piped or COPY file
     *
     * @return a string representation of the value of the received object
     */
    public static String convertObjectToString(Object object, FileType annon) {
        Validate.notNull(object, "object must not be null");
        String obj;
        BaseClass template = DTOFactory.getTemplate(object.getClass(), annon, Boolean.FALSE);

        if (template instanceof PipedClass) {
            obj = Extractor.convertPipedObjectToString(object, (PipedClass) template);
        } else {
            obj = Extractor.convertCopyObjectToString(object, (CopyClass) template);
        }

        return obj;
    }

    /**
     * Creates a {@code String} object representing each item in the received object list
     *
     * @param objects the {@code Object} list to be converted to a {@code Strings}
     * @param annon   annotation to indicate if is Piped or COPY file
     * @return a list of string representation of the value of each item in the received object list
     */
    public static List<String> convertObjectsToStrings(List<?> objects, FileType annon) {
        Validate.notNull(objects, "objects must not be null");
        Validate.isTrue(!objects.isEmpty(), "data must not be empty");

        List<String> list = new ArrayList<>();

        if (!objects.isEmpty()) {
            BaseClass template = DTOFactory.getTemplate(objects.get(ParseUtils.NUM_0).getClass(), annon, Boolean.FALSE);

            if (template instanceof PipedClass) {
                for (Object row : objects) {
                    String object;
                    object = Extractor.convertPipedObjectToString(row, (PipedClass) template);
                    list.add(object);
                }
            } else {
                for (Object row : objects) {
                    String object;
                    object = Extractor.convertCopyObjectToString(row, (CopyClass) template);
                    list.add(object);
                }
            }

        }

        return list;

    }

    /********************************
     * End Object-To-String methods *
     ********************************/

    /***
     * *
     ***/

    /***
     * *
     ***/

    /********************************
     * Start Object-To-File methods *
     ********************************/

    /**
     * Creates a {@code String} object representing each item in the received object lists and append it into a file
     *
     * @param <H>     Header type
     * @param <D>     Data type
     * @param <T>     Tail type
     * @param file    path file to save string representations of the value of each item in the received object lists
     * @param header  objects to be appended into the fist rows of the file
     * @param objects objects to be appended into after header rows
     * @param tails   objects to be appended into the last rows of the file
     * @param annon   annotation to indicate if is Piped or COPY file
     *
     */
    public static <H, D, T> void convertObjectsToFile(String file, Deque<H> header, Deque<D> objects, Deque<T> tails, FileType annon) {

        Validate.notNull(file, "file must not be null");
        Validate.notNull(objects, "list of objects must not be null");
        Validate.isTrue(!objects.isEmpty(), "data must not be empty");

        Function<H, String> rowHeaderSuplier = null;
        Function<D, String> rowDataSuplier;
        Function<T, String> rowTailSuplier = null;

        final BaseClass headerTemplate;
        final BaseClass dataTemplate = DTOFactory.getTemplate(objects.getFirst().getClass(), annon, Boolean.FALSE);
        final BaseClass tailTemplate;

        if (header != null && !header.isEmpty()) {
            headerTemplate = DTOFactory.getTemplate(header.getFirst().getClass(), annon, Boolean.FALSE);
        } else {
            headerTemplate = null;
        }

        if (tails != null && !tails.isEmpty()) {
            tailTemplate = DTOFactory.getTemplate(tails.getFirst().getClass(), annon, Boolean.FALSE);
        } else {
            tailTemplate = null;
        }

        if (headerTemplate instanceof PipedClass) {
            rowHeaderSuplier = object -> Extractor.convertPipedObjectToString(object, (PipedClass) headerTemplate);
        } else if (headerTemplate instanceof CopyClass) {
            rowHeaderSuplier = object -> Extractor.convertCopyObjectToString(object, (CopyClass) headerTemplate);
        }

        if (dataTemplate instanceof PipedClass) {
            rowDataSuplier = object -> Extractor.convertPipedObjectToString(object, (PipedClass) dataTemplate);
        } else {
            rowDataSuplier = object -> Extractor.convertCopyObjectToString(object, (CopyClass) dataTemplate);
        }

        if (tailTemplate instanceof PipedClass) {
            rowTailSuplier = object -> Extractor.convertPipedObjectToString(object, (PipedClass) tailTemplate);
        } else if (tailTemplate instanceof CopyClass) {
            rowTailSuplier = object -> Extractor.convertCopyObjectToString(object, (CopyClass) tailTemplate);
        }

        ParseUtils.writeFile(file, header, rowHeaderSuplier, objects, rowDataSuplier, tails, rowTailSuplier);
    }

    /***
     * *
     ***/

    /***
     * *
     ***/

    /***
     * *
     ***/

    /***
     * *
     ***/

    /***
     * *
     ***/

    /***
     * *
     ***/

    /*************************
     * Start private methods *
     *************************/

    /**
     * Conver a string to a DTO object
     *
     * @param <T>      Required type
     * @param data     a string representation of a DTO object
     * @param type     required type
     * @param template DTO with the the required converters to convert the object
     * @return a new T instance or null when the data is empty
     */
    static <T extends Object> T convertStringRowToObject(String data, Class<T> type, BaseClass template) {

        T object = null;

        if (StringUtils.isNotBlank(data)) {

            // array with values when is a piped String
            String[] values = null;

            boolean isPiped = template instanceof PipedClass;

            if (isPiped) {
                values = data.split(ParseUtils.PIPE_SEPARATOR, -1);
            }

            object = ParseUtils.newInstance(type);

            for (BaseField fieldParseDTO : template.getFieldParseDTOs()) {

                Field field = fieldParseDTO.getField();

                Converter conv = fieldParseDTO.getConverter();

                String fieldName = field.getName();
                String strValue = null;
                Object objValue = null;

                Mutable<Class<? extends FieldConverter<Object>>> clazzConv = new MutableObject<>();

                try {

                    if (template instanceof PipedClass) {
                        int indx = ((PipedField) fieldParseDTO).getPipeField().value();
                        // if is a piped file, get value from the array
                        strValue = values[indx].trim();
                    } else {
                        Pic pic = ((CopyField) fieldParseDTO).getPic();
                        // template.type is equals to ClassParseDTO.Type.PIC
                        // if is a piped file, get value from data
                        // using beginIndex and size values in Pic Annotation
                        int begin = pic.beginIndex();
                        int size = pic.size();

                        if ((begin + size) > data.length()) {
                            // avoid IndexOutOfBoundsException if data is shorter than expected
                            strValue = data.substring(begin);
                        } else {
                            strValue = data.substring(begin, begin + size);
                        }

                    }

                    field.setAccessible(Boolean.TRUE);

                    objValue = Extractor.getValue(template, fieldParseDTO, conv, field, clazzConv, isPiped, strValue);

                    field.set(object, objValue);

                } catch (IndexOutOfBoundsException e) {
                    // when is an IndexOutOfBoundsException strValue is null
                    throw new RecordsException("An error has occurred while getting value, field_name: {}; of: '{}'; index_not_found: {}; class: {};", fieldName, data, e.getMessage(), type.getCanonicalName(), e);
                } catch (ParseException e) {
                    // when is a ParseException the objValue is null
                    String convDesc = clazzConv.getValue() != null ? clazzConv.getValue().getCanonicalName() : ParseUtils.EMPTY;
                    throw new RecordsException("An error has occurred while setting value, original value: '{}'; converter: {}; class: {}; field name: {}; field type: {};", strValue, convDesc, type.getCanonicalName(), fieldName, field.getType().getCanonicalName(), e);
                } catch (Exception e) {
                    String convDesc = clazzConv.getValue() != null ? clazzConv.getValue().getCanonicalName() : ParseUtils.EMPTY;
                    throw new RecordsException("An error has occurred while setting value, original value: '{}'; converted value: {}; converter: {};  class: {}; field name: {}; field type: {};", strValue, objValue, convDesc, type.getCanonicalName(), fieldName, field.getType().getCanonicalName(), e);
                }

            }
        }

        return object;
    }

    @SuppressWarnings("unchecked")
    private static Object getValue(BaseClass template, BaseField fieldParseDTO, Converter conv, Field field, Mutable<Class<? extends FieldConverter<Object>>> clazzConv, boolean isPiped, String strValue) {

        Object objValue = null;

        try {
            String ifNull = fieldParseDTO.getIfNull();

            if ((isPiped && !strValue.equals(ifNull)) || (!isPiped && !strValue.trim().equals(ifNull))) {

                if (conv == null) {
                    objValue = ParseUtils.parse(strValue, field.getType());
                } else {
                    clazzConv.setValue((Class<? extends FieldConverter<Object>>) conv.value());
                    // get the converter in the ClassParseDTO
                    FieldConverter<?> c = template.getConverter(clazzConv.getValue());
                    objValue = c.toObject(strValue, conv.args());
                }
            }

        } catch (Exception e) {
            throw new ParseException(e.getMessage(), e);
        }

        return objValue;
    }

    /**
     * Creates a {@code String} object representing the received object
     *
     * @param data     the {@code Object} to be converted to a {@code Strings}
     * @param template DTO with the the required converters to convert the object
     * @return a string representation of the value of the received object
     *
     */
    @SuppressWarnings("unchecked")
    private static String convertPipedObjectToString(Object data, PipedClass template) {

        StringBuilder sb = new StringBuilder();

        int last = template.getLastIndex();

        for (int idx = 0; idx <= last; idx++) {

            PipedField extract = template.getFieldParseDTO(idx);

            if (extract != null) {

                String name = extract.getField().getName();
                Object value = Extractor.getValue(extract.getField(), data);

                Class<? extends FieldConverter<?>> conv = null;

                try {

                    String strValue;

                    if (value == null && extract.getIfNull() != null) {
                        strValue = extract.getIfNull();
                    } else if (extract.getConverter() == null) {
                        strValue = value != null ? value.toString() : null;
                    } else {
                        conv = extract.getConverter().value();
                        FieldConverter<Object> c = (FieldConverter<Object>) template.getConverter(conv);
                        strValue = c.toString(value, extract.getConverter().args());
                    }

                    // cut if is longer than max size
                    if (extract.getMaxSize() != null && strValue != null && strValue.length() > extract.getMaxSize()) {
                        // when the values is longer tha
                        strValue = strValue.substring(ParseUtils.NUM_0, extract.getMaxSize());
                    } else if (extract.getMaxSize() != null && extract.getPipeField().fixedSize()) {
                        // when the fixed size was specified
                        if (extract.getPipeField().rightAlign()) {
                            strValue = StringUtils.leftPad(strValue, extract.getMaxSize(), ParseUtils.SPACE);
                        } else {
                            strValue = StringUtils.rightPad(strValue, extract.getMaxSize(), ParseUtils.SPACE);
                        }
                    }

                    sb.append(strValue);

                } catch (Exception e) {
                    String convName = conv != null ? conv.getCanonicalName() : ParseUtils.EMPTY;
                    throw new RecordsException("An error has occurred while converting value: '{}'; converter: {}; class: {}; field: {};", value, convName, data.getClass().getCanonicalName(), name, e);
                }

            } else {
                sb.append(ParseUtils.EMPTY);
            }

            if (idx < last) {
                sb.append(ParseUtils.PIPE);
            }
        }

        return sb.toString();
    }

    /**
     * Creates a {@code String} object representing the received object
     *
     * @param data     the {@code Object} to be converted to a {@code Strings}
     * @param template DTO with the the required converters to convert the object
     * @return a string representation of the value of the received object
     *
     */
    @SuppressWarnings("unchecked")
    private static String convertCopyObjectToString(Object data, CopyClass template) {

        int size = template.getSize();

        StringBuilder sb = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            sb.append(ParseUtils.SPACE);
        }

        Collection<CopyField> extracts = template.getFieldParseDTOs();

        for (CopyField extract : extracts) {

            Class<? extends FieldConverter<?>> conv = null;

            String name = extract.getField().getName();
            Object value = Extractor.getValue(extract.getField(), data);

            try {

                String val;

                if (value == null && extract.getIfNull() != null) {
                    val = extract.getIfNull();
                } else if (extract.getConverter() == null) {
                    val = value != null ? value.toString() : ParseUtils.EMPTY;
                } else {
                    conv = extract.getConverter().value();
                    FieldConverter<Object> c = (FieldConverter<Object>) template.getConverter(conv);
                    val = c.toString(value, extract.getConverter().args());
                }

                int fieldSize = extract.getPic().size();
                // cut string if is longer than fieldSize
                if (val.length() > fieldSize) {
                    val = val.substring(ParseUtils.NUM_0, fieldSize);
                } else if (val.length() < fieldSize) {
                    // use val.length as field size
                    fieldSize = val.length();
                }

                if (!val.isEmpty()) {
                    int start = extract.getPic().beginIndex();
                    sb.replace(start, start + fieldSize, val);
                }

            } catch (Exception e) {
                String convName = conv != null ? conv.getCanonicalName() : ParseUtils.EMPTY;
                throw new RecordsException("An error has occurred while converting value: '{}'; converter: {}; class: {}; field: {};", value, convName, data.getClass().getCanonicalName(), name, e);
            }

        }

        return sb.toString();
    }

    /**
     *
     * @param field
     * @param data
     * @return
     * @since
     * @author
     *
     */
    private static Object getValue(Field field, Object data) {

        Object value;

        field.setAccessible(Boolean.TRUE);

        try {
            value = field.get(data);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new RecordsException("An error has occurred while getting value in class: {}; field: {};", data.getClass().getCanonicalName(), field.getName(), e);
        }

        return value;
    }
}
