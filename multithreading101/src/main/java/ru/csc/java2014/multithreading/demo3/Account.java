package ru.csc.java2014.multithreading.demo3;

public class Account {

    private /*volatile*/ long balance;

    public Account() {
        this(0L);
    }

    Account(long balance) {
        this.balance = balance;
    }

    long getBalance() {
        return balance;
    }

    void deposit(long amount) {
        checkAmountNonNegative(amount);
        balance += amount;
    }

    void withdraw(long amount) {
        checkAmountNonNegative(amount);
        if (balance < amount) {
            throw new IllegalArgumentException("not enough money");
        }
        balance -= amount;
    }

    private static void checkAmountNonNegative(long amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("negative amount");
        }
    }
}
