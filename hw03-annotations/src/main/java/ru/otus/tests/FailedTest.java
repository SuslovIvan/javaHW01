package ru.otus.tests;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

public class FailedTest {

    @Before
    private void beforeMethod() throws Exception{
        throw new ClassNotFoundException("ERROR");
    }

    @Test
    private void testMethod01() throws Exception {
        throw new ClassNotFoundException("class not found, really ?");
    }

    @Test
    private void testMethod02() throws Exception {
        System.out.println("FailedTest: test success method");
    }

    @After
    private void afterMethod() throws Exception {
        System.out.println("FailedTest: after test method");
    }
}
