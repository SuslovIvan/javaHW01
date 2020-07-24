package ru.otus.agent;

import ru.otus.proxy.MyClassImpl;

public class AgentTester {

    public static void main(String[] args) {
        MyClassImpl myClassImpl = new MyClassImpl();

        System.out.println("agent main: calling annotated multiply(int, int) method:");
        myClassImpl.multiply(2, 5);

        System.out.println("agent main: calling overridden non-annotated multiply(double, double) method");
        myClassImpl.multiply(0.1, 0.2);

        System.out.println("agent main: calling annotated sqrt(double) method:");
        myClassImpl.sqrt(4.0);

        System.out.println("agent main: calling annotated empty() method:");
        myClassImpl.empty();

        System.out.println("agent main: calling annotated sum(double, double, double) method:");
        myClassImpl.sum(1.0, 2.0, 3.0);

        System.out.println("agent main: calling annotated sum(long, long) method:");
        myClassImpl.sum(1L, 2L);
    }

}
