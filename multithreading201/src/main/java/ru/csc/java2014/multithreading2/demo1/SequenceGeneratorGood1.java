package ru.csc.java2014.multithreading2.demo1;

import java.util.ArrayList;
import java.util.List;

public class SequenceGeneratorGood1 {

    private static int counter = 0;

    private static synchronized int nextInt() {
        return counter++;
    }


    public static void main(String[] args) throws Exception {
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 10000; ++i) {
            Thread thread = new Thread(() -> System.out.println(nextInt()));
            thread.start();
            threads.add(thread);
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("Counter final value: " + counter);
    }
}
