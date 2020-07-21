package ru.otus.helper;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestHelper {

    public Result runTests(List<Class<?>> classes) {
        Result result = new Result();

        for (Class<?> clazz : classes) {
            Info info = executeClass(clazz);
            result.add(clazz, info);
        }

        return result;
    }

    private Info executeClass(Class<?> clazz) {
        Info info = new Info();

        Method[] beforeMethods = getAllAnnotatedMethods(clazz, Before.class);
        Method[] testMethods = getAllAnnotatedMethods(clazz, Test.class);
        Method[] afterMethods = getAllAnnotatedMethods(clazz, After.class);

        for (Method testMethod : testMethods) {
            Object instance = getInstance(clazz);

            boolean isPassedBefore = executeMethods(instance, beforeMethods);
            boolean isPassedTest = executeMethods(instance, testMethod);
            boolean isPassedAfter = executeMethods(instance, afterMethods);

            if (isPassedBefore && isPassedTest && isPassedAfter) {
                info.incSuccess();
            } else {
                info.incFailed();
            }

        }

        info.setTotal(testMethods.length);

        return info;
    }

    private boolean executeMethods(Object instance, Method... methods) {
        for (Method method : methods) {
            try {
                method.setAccessible(true);
                method.invoke(instance);
            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
        }

        return true;
    }

    private Method[] getAllAnnotatedMethods(Class<?> clazz, Class<? extends Annotation>... annotations) {
        List<Method> methodList = new ArrayList<>();

        for (Class<? extends Annotation> annotation : annotations) {
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(annotation)) {
                    methodList.add(method);
                }
            }
        }

        return methodList.toArray(Method[]::new);
    }

    private <T> T getInstance(Class<T> clazz) {
        T object = null;

        try {
            object = clazz.getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException ex) {
            throw new IllegalStateException("getInstance: object instantiation error (class-name=" + clazz.getSimpleName() + ")");
        }

        return object;
    }

    public Class<?> getClass(String className) {
        Class<?> clazz = null;

        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException ex) {
            System.out.println("getClass: error, class not found (class-name=" + className + ")");
        }

        return clazz;
    }
}
