package ru.otus.tests;

import jdk.jfr.BooleanFlag;
import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

public class SuccessTest {

    @Before
    private void beforeTest() {
        System.out.println("testing success before test method");
    }

    @Test
    public void test() {
        System.out.println("testing success test method");
    }

    @After
    private void afterTest() {
        System.out.println("testing success after test method");
    }

}
