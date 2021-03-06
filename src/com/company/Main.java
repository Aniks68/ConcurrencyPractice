package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static final int MAX_PASSWORD = 9999;
    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();

        Vault vault = new Vault(random.nextInt(MAX_PASSWORD));
        List<Thread> threads = new ArrayList<>();
        threads.add(new AscendingHackerThread(vault));
        threads.add(new AscendingHackerThreadHalf(vault));
        threads.add(new AscendingHackerThreadQuart(vault));
        threads.add(new AscendingHackerThreadThreeQuart(vault));
        threads.add(new DescendingHackerThread(vault));
        threads.add(new DescendingHackerThreadHalf(vault));
        threads.add(new DescendingHackerThreadQuart(vault));
        threads.add(new DescendingHackerThreadThreeQuart(vault));
        threads.add(new PoliceThread());

        threads.forEach(Thread::start);
    }

    private static class Vault {
        private int password;
        public Vault(int password) {
            this.password = password;
        }

        public boolean isCorrectPassword(int guess) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {

            }
            return this.password == guess;
        }
    }

    private static abstract class HackerThread extends Thread {
        protected Vault vault;

        public HackerThread(Vault vault) {
            this.vault = vault;
            this.setName(this.getClass().getSimpleName());
            this.setPriority(Thread.MAX_PRIORITY);
        }

        @Override
        public void start() {
            System.out.println("Starting thread " + this.getName());
            super.start();
        }
    }

    private static class AscendingHackerThread extends HackerThread {
        public AscendingHackerThread(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for(int guess = 0; guess < MAX_PASSWORD/4; guess++) {
                if(vault.isCorrectPassword(guess)) {
                    System.out.println(this.getName() + " guessed the password " + guess);
                    System.exit(0);
                }
            }
        }
    }

    private static class AscendingHackerThreadHalf extends HackerThread {
        public AscendingHackerThreadHalf(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for(int guess = MAX_PASSWORD/2; guess < MAX_PASSWORD* (3/4); guess++) {
                if(vault.isCorrectPassword(guess)) {
                    System.out.println(this.getName() + " guessed the password " + guess);
                    System.exit(0);
                }
            }
        }
    }

    private static class AscendingHackerThreadQuart extends HackerThread {
        public AscendingHackerThreadQuart(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for(int guess = MAX_PASSWORD/4; guess < MAX_PASSWORD/2; guess++) {
                if(vault.isCorrectPassword(guess)) {
                    System.out.println(this.getName() + " guessed the password " + guess);
                    System.exit(0);
                }
            }
        }
    }

    private static class AscendingHackerThreadThreeQuart extends HackerThread {
        public AscendingHackerThreadThreeQuart(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for(int guess = MAX_PASSWORD * (3/4); guess < MAX_PASSWORD; guess++) {
                if(vault.isCorrectPassword(guess)) {
                    System.out.println(this.getName() + " guessed the password " + guess);
                    System.exit(0);
                }
            }
        }
    }

    private static class DescendingHackerThread extends HackerThread {
        public DescendingHackerThread(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for(int guess = MAX_PASSWORD; guess > MAX_PASSWORD/2; guess--) {
                if(vault.isCorrectPassword(guess)) {
                    System.out.println(this.getName() + " guessed the password " + guess);
                    System.exit(0);
                }
            }
        }
    }

    private static class DescendingHackerThreadHalf extends HackerThread {
        public DescendingHackerThreadHalf(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for(int guess = MAX_PASSWORD/2; guess > MAX_PASSWORD/4; guess--) {
                if(vault.isCorrectPassword(guess)) {
                    System.out.println(this.getName() + " guessed the password " + guess);
                    System.exit(0);
                }
            }
        }
    }

    private static class DescendingHackerThreadQuart extends HackerThread {
        public DescendingHackerThreadQuart(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for(int guess = MAX_PASSWORD * (3/4); guess > MAX_PASSWORD/2; guess--) {
                if(vault.isCorrectPassword(guess)) {
                    System.out.println(this.getName() + " guessed the password " + guess);
                    System.exit(0);
                }
            }
        }
    }

    private static class DescendingHackerThreadThreeQuart extends HackerThread {
        public DescendingHackerThreadThreeQuart(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for(int guess = MAX_PASSWORD/4; guess > 0; guess--) {
                if(vault.isCorrectPassword(guess)) {
                    System.out.println(this.getName() + " guessed the password " + guess);
                    System.exit(0);
                }
            }
        }
    }

    private static class PoliceThread extends Thread {
        @Override
        public void run() {
            for (int i = 15; i > 0; i--) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {

                }
                System.out.println(i);
            }
            System.out.println("Game over for you hackers");
            System.exit(0);
        }
    }
}
