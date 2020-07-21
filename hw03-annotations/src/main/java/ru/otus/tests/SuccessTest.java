package ru.otus.tests;

import jdk.jfr.BooleanFlag;
import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

public class SuccessTest {

    @Before
    private void beforeTest() {
        System.out.println("SuccessTest: before test method");
    }

    @Test
    public void test01() {
        System.out.println("SuccessTest: test method01");
    }

    @Test
    public void test02() {
        System.out.println("SuccessTest: test method02");
    }

    @Test
    public void test03() {
        System.out.println("SuccessTest: test method03");
    }

    @Test
    public void test04() {
        System.out.println("SuccessTest: test method04");
    }

    @After
    private void afterTest() {
        System.out.println("SuccessTest: after test method");
    }

}
