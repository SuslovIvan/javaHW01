package ru.otus.tests;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

public class FailedTest {

    @Before
    private void beforeMethod() throws Exception{
        System.out.println("FailedTest: before test method");
    }

    @Test
    private void testMethod01() throws Exception {
        throw new ClassNotFoundException("class not found, really ?");
    }

    @Test
    private void testMethod02() throws Exception {
        throw new ArrayIndexOutOfBoundsException("It's not array, sorry");
    }

    @After
    private void afterMethod() throws Exception {
        System.out.println("FailedTest: after test method");
    }
}
