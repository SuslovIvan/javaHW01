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
        Object testObj = getInstance(clazz);

        List<Method> beforeMethods = getAllAnnotatedMethods(clazz, Before.class);
        executeMethod(beforeMethods, testObj, info);

        List<Method> testMethods = getAllAnnotatedMethods(clazz, Test.class);
        executeMethod(testMethods, testObj, info);

        List<Method> afterMethods = getAllAnnotatedMethods(clazz, After.class);
        executeMethod(afterMethods, testObj, info);

        int total = getAllAnnotatedMethods(clazz, Before.class, Test.class, After.class).size();
        info.setTotal(total);

        return info;
    }

    private void executeMethod(List<Method> methods, Object object, Info info) {
        for (Method method : methods) {
            try {
                method.setAccessible(true);
                method.invoke(object);
                info.incSuccess();
            } catch (Exception ex) {
                ex.printStackTrace();
                info.incFailed();
            }
        }
    }


    private List<Method> getAllAnnotatedMethods(Class<?> clazz, Class<? extends Annotation>... annotations) {
        List<Method> methodList = new ArrayList<>();

        for (Class<? extends Annotation> annotation : annotations) {
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(annotation)) {
                    methodList.add(method);
                }
            }
        }

        return methodList;
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
