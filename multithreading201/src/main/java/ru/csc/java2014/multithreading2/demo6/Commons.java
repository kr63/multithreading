package ru.csc.java2014.multithreading2.demo6;

class Commons {

    static int[] prepareArray() {
        int[] array = new int[20000000];
        for (int i = 0; i < array.length; ++i) {
            array[i] = i;
        }
        return array;
    }

    static double calculate(int[] array) {
        return calculate(array, 0, array.length);
    }

    static double calculate(int[] array, int start, int end) {
        double sum = 0;
        for (int i = start; i < end; ++i) {
            sum += function(array[i]);
        }
        return sum;
    }

    static double function(int argument) {
        return Math.sin(argument);
    }
}
