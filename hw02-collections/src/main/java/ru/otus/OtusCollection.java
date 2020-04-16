package ru.otus;

import java.util.*;

public class OtusCollection {

    public static void main(String[] args) {
        Integer[] intArray = new Integer[]{2, 1, 8, 7, 5, 9, 4, 3, 6, 10, 19, 11, 13, 12, 15, 14, 16, 17, 18, 0};
        String[] strArray = new String[]{"one", "two", "three", "four", "five", "six", "seven", "eight"};

        testCollection(Integer.class, intArray);
        testCollection(String.class, strArray);

        List<Integer> intList = new DIYArrayList<>(Arrays.asList(intArray));
        Collections.sort(intList, (e1, e2) -> e1.compareTo(e2));
        System.out.println("sorted integers: " + Arrays.toString(intList.toArray()));

        List<String> strList = new DIYArrayList<>(Arrays.asList(strArray));
        Collections.sort(strList, (e1, e2) -> e1.compareTo(e2));
        System.out.println("sorted strings: " + Arrays.toString(strList.toArray()));
    }

    public static <T> void testCollection(T clazz, T[] dataArray) {
        List<T> filledList = new DIYArrayList<>();

        Collections.addAll(filledList, dataArray);
        System.out.println("filled list: " + Arrays.toString(filledList.toArray()));

        List<T> emptyList = new DIYArrayList<>(filledList.size());
        fillList(emptyList, filledList.size());

        Collections.copy(emptyList, filledList);
        System.out.println("copied list: " + Arrays.toString(emptyList.toArray()));
    }

    public static void fillList(List emptyList, int size) {
        for (int i = 0; i < size; i++) {
            emptyList.add(null);
        }
    }

}
