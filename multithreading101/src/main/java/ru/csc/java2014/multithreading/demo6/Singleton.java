package ru.csc.java2014.multithreading.demo6;

public class Singleton {

    private int foo;
    private String bar;

    private Singleton() {
        foo = 13;
        bar = "zap";
    }

    private static Singleton instance;
    private static volatile Singleton instance2;

    public synchronized static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    // double checked locking anti-pattern
    public static Singleton getInstance2() {
        if (instance2 == null) {
            synchronized (Singleton.class) {
                if (instance2 == null) {
                    instance2 = new Singleton();
                }
            }
        }
        return instance2;
    }
}
