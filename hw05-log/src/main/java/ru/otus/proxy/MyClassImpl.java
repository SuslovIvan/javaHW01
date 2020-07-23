package ru.otus.proxy;

import ru.otus.annotations.Log;

public class MyClassImpl implements MyClassInterface {

    @Log
    public int multiply(int i1, int i2) {
        return i1 * i2;
    }

    /* проверка перегруженных методов без аннотации */
    public double multiply(double d1, double d2) {
        return d1 * d2;
    }

    @Log
    public double sqrt(double d) {
        return Math.sqrt(d);
    }

    @Log
    public double sum(double d1, double d2) {
        return d1 + d2;
    }

    @Log
    public double sum(double d1, double d2, double d3) {
        return d1 + d2 + d3;
    }

    @Log
    public double sum(long l1, long l2) {
        return l1 + l2;
    }

    @Log
    public void mix(String s1, int i1, double d1) {

    }

    @Log
    public void empty() {
    }

    public void sout(int arg1, int arg2) {
        System.out.println(arg1 + " " + arg2);
    }
}
