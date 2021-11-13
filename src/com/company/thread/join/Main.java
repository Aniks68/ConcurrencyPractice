package com.company.thread.join;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        List<Long> inputNumbers = Arrays.asList(3000000000L, 3435L, 3454L, 2384L, 4765L, 23L, 2457L, 5566L);
        // To calculate the factorials...
        List<FactorialThread> threads = inputNumbers.stream().mapToLong(inputNumber -> inputNumber).mapToObj(FactorialThread::new).collect(Collectors.toList());

        for (Thread thread : threads) {
            thread.setDaemon(true);
            thread.start();
        }

        for (int i = 0; i < inputNumbers.size(); i++) {
            FactorialThread factorialThread = threads.get(i);
            System.out.println(factorialThread.isFinished() ? "Factorial of " + inputNumbers.get(i) + " is " + factorialThread.getResult() : "The calculation for " + inputNumbers.get(i) + " is still in progress");
        }

        for (Thread thread : threads) {
            thread.join(500);
        }

    }

    public static class FactorialThread extends Thread{
        private long inputNumber;
        private BigInteger result = BigInteger.ZERO;
        private boolean isFinished = false;

        public FactorialThread(long inputNumber) {
            this.inputNumber =inputNumber;
        }

        @Override
        public void run() {
            this.result = factorial(inputNumber);
            this.isFinished = true;
        }

        public BigInteger factorial(long n) {
            BigInteger tempResult = BigInteger.ONE;

            for (long i = n; i > 0; i--) {
                tempResult = tempResult.multiply(new BigInteger(Long.toString(i)));
            }
            return tempResult;
        }

        public boolean isFinished() {
            return isFinished;
        }

        public BigInteger getResult() {
            return result;
        }
    }
}
