package ru.otus;

import ru.otus.helper.Result;
import ru.otus.helper.TestHelper;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        if (args.length == 0) {
            throw new IllegalStateException("Parameter list is null. Please enter names of the testing class!");
        }

        List<Class<?>> testingClasses = new ArrayList<>();
        TestHelper helper = new TestHelper();

        for (String arg : args) {
            Class<?> clazz = helper.getClass(arg);
            if (clazz == null) {
                continue;
            }
            testingClasses.add(clazz);
        }

        Result result = helper.runTests(testingClasses);
        System.out.println(result);
    }

}
