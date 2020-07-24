package ru.otus.proxy;

import ru.otus.annotations.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class Ioc {

    public static MyClassInterface createMyClass() {
        InvocationHandler handler = new MyInvocationHandler(new MyClassImpl());
        return (MyClassInterface) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                new Class[]{MyClassInterface.class}, handler);
    }

    static class MyInvocationHandler implements InvocationHandler {
        private final MyClassImpl myClass;

        MyInvocationHandler(MyClassImpl myClass) {
            this.myClass = myClass;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Method implMethod = MyClassImpl.class.getMethod(method.getName(), method.getParameterTypes());

            if (implMethod.isAnnotationPresent(Log.class)) {
                System.out.println("executed method: " + method.getName() + ", param: " + Arrays.toString(args));
            }

            return method.invoke(myClass, args);
        }

    }

}
