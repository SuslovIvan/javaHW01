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
    public void empty() {
    }
}
