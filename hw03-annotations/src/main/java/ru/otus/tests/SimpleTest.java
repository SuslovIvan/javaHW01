package ru.otus.tests;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

public class SimpleTest {

    @Before
    public void beforeTestMethod01() {
        System.out.println("testing before method 01");
    }

    @Before
    private void beforeTestMethod02() {
        System.out.println("testing before method 02");
    }

    @Test
    public void testMethod01() {
        System.out.println("testing test method 01");
    }

    @Test
    private void testMethod02() {
        System.out.println("testing test method 02");
    }

    @Test
    private void testMethod03() throws  Exception{
        throw new Exception("Oh no...");
    }

    @After
    public void afterTestMethod01() {
        System.out.println("testing after method 01");
    }

    @After
    private void afterTestMethod02() {
        System.out.println("testing after method 02");
    }

}
