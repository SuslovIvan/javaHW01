package ru.otus;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

public class HelloOtus {

    public static void main(String[] args) {
        List<String> numberList = Lists.newArrayList("4", "3", "5", "2");
        String joinedList = Joiner.on(",").join(numberList);

        System.out.println("array: " + Arrays.toString(numberList.toArray()));
        System.out.println("joined string: " + joinedList);
    }

}
