package ru.otus;

import ru.otus.proxy.Ioc;
import ru.otus.proxy.MyClassImpl;
import ru.otus.proxy.MyClassInterface;

public class Main {

    public static void main(String[] args) {
        MyClassInterface myClass = Ioc.createMyClass();

        System.out.println("main: calling annotated multiply(int, int) method:");
        myClass.multiply(2, 5);

        System.out.println("main: calling overridden non-annotated multiply(double, double) method");
        myClass.multiply(0.1, 0.2);

        System.out.println("main: calling annotated sqrt(double) method:");
        myClass.sqrt(4.0);

        System.out.println("main: calling annotated empty() method:");
        myClass.empty();
    }

}
