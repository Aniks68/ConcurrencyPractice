package com.company.thread.interrupt;

import java.math.BigInteger;
import java.util.concurrent.PriorityBlockingQueue;

public class Main {

    public static void main(String[] args) {
        //
        Thread thread = new Thread(new BlockingTask());
        thread.start();
        thread.interrupt();

        Thread thread1 = new Thread(new LongComputationTask(new BigInteger("200304"), new BigInteger("1000000000")));
        thread1.setDaemon(true);
        thread1.start();
        thread1.interrupt();

        // Testing ActiveObject
        ActiveObject obj = new ActiveObject();
        // Call doTask in different threads
        Thread t1 = new Thread(() -> {
                obj.doTask("1", 2);
        });
        Thread t2 = new Thread(() -> {
                obj.doTask("2", 0);
        });
        Thread t3 = new Thread(() -> {
                obj.doTask("3", 1);
        });
        t1.start();
        t2.start();
        t3.start();
    }

    private static class BlockingTask implements Runnable {
        @Override
        public void run() {
            // do things
            try{
                Thread.sleep(50000);
            } catch (InterruptedException e) {
                System.out.println("Exiting blocking thread");
            }
        }
    }

    private static class LongComputationTask implements Runnable {
        private BigInteger base;
        private BigInteger power;

        public LongComputationTask(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
            System.out.println(base+"^"+power+" = " + pow(base, power));
        }

        private BigInteger pow(BigInteger base, BigInteger power) {
            BigInteger result = BigInteger.ONE;

            for (BigInteger i = BigInteger.ZERO; i.compareTo(power) !=0; i = i.add(BigInteger.ONE)) {
                if(Thread.currentThread().isInterrupted()) {
                    System.out.println("Prematurely interrupted computation");
                    return BigInteger.ZERO;
                }
                result = result.multiply(base);
            }
            return result;
        }
    }

    public static class ActiveObject {
        class Task implements Comparable < Task > {
            // smaller number means higher priority
            int priority;
            String name;
            Task(String name, int priority) {
                this.name = name;
                this.priority = priority;
            }
            public int compareTo(Task other) {
                return Integer.compare(this.priority, other.priority);
            }
        }
        private PriorityBlockingQueue < Task > dispatchQueue = new PriorityBlockingQueue< >();
        public ActiveObject() {
            // A priority scheduler
            new Thread(() -> {
            while (true) {
                try {
                    Task task = dispatchQueue.take();
                    System.out.println("Executing task " + task.name);
                } catch (InterruptedException e) {
                    break;
                }
            }
      })
      .start();
        }
        public void doTask(String name, int priority) {
            dispatchQueue.put(new Task(name, priority));
        }
    }
}

