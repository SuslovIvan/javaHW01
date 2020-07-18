package ru.otus.helper;

import java.util.HashMap;
import java.util.Map;

public class Result {

    private Map<Class, Info> result;

    public Result() {
        result = new HashMap<>();
    }

    public void add(Class<?> clazz, Info info) {
        result.put(clazz, info);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Result:\n");
        result.entrySet().stream().forEach(a -> builder.append("[" + a.getKey() + " : " + a.getValue() + "]\n"));
        return builder.toString();
    }
}
