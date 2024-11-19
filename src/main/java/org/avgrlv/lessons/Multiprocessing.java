package org.avgrlv.lessons;

public class Multiprocessing {
    public static void main(String[] args) throws InterruptedException {
//        example0();
//        example1();
        example2();
    }

    private static void example2() {
        Thread t1 = new Thread(() -> {

            System.out.println("Федя: учись, студент!");
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("кто-то рабоатет");
            System.out.println(Thread.currentThread());
        }, "поток t1");

        Thread t2 = new Thread(() -> {

            System.out.println("Шурча: будем перевоспитывать!");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("надо, Федя, надо!");
            System.out.println(Thread.currentThread());
        }, "поток t2");

        t1.start();
        t2.start();
        t1.start(); // так нельзя, если уже он запущен
        System.out.println("Finish");
    }


    private static void example1() {
        Thread w1 = new Worker("worker 1");
        w1.run();
        Thread w2 = new Worker("worker 2");
        w2.run();

        System.out.println("-----------------");
        w1.start();
        w2.start();
    }

    private static void example0() throws InterruptedException {
        System.out.println("Текущий поток (основной) = " + Thread.currentThread());
        Thread.sleep(1000);
    }
}

class Worker extends Thread {
    String name;

    public Worker(String name) {
        super("**" + name + "**");
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println(name + ": учись, студент!");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("кто-то рабоатет");
        System.out.println(Thread.currentThread());
    }
}
