package ru.otus.proxy;

import ru.otus.annotations.Log;

public interface MyClassInterface {
    int multiply(int i1, int i2);
    double multiply(double d1, double d2);
    double sqrt(double d);
    double sum(double d1, double d2);
    double sum(double d1, double d2, double d3);
    double sum(long l1, long l2);
    void mix(String s1, int i1, double d1);
    void empty();
}
