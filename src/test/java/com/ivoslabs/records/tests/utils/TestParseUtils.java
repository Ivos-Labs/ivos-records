/**
 *
 */
package com.ivoslabs.records.tests.utils;

import java.io.File;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.ivoslabs.records.utils.LinesCounter;

/**
 * @since 1.0.0
 * @author www.ivoslabs.com
 *
 */
public class TestParseUtils {

    @Test
    public void test00() {
        int expected = 0;
        int actual;

        actual = new LinesCounter().countLines(new File("./src/test/resources/data/lines_a-empty.data").getAbsolutePath());

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void test01() {
        int expected = 1;
        int actual;

        actual = new LinesCounter().countLines(new File("./src/test/resources/data/lines_b-1.data").getAbsolutePath());

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void test02() {
        int expected = 2;
        int actual;

        actual = new LinesCounter().countLines(new File("./src/test/resources/data/lines_c-2.data").getAbsolutePath());

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void test03() {
        int expected = 3;
        int actual;

        actual = new LinesCounter().countLines(new File("./src/test/resources/data/lines_d-3.data").getAbsolutePath());

        Assertions.assertEquals(expected, actual);
    }

}
