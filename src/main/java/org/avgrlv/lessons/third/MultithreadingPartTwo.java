package org.avgrlv.lessons.third;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MultithreadingPartTwo {
    public static void main(String[] args) throws InterruptedException {
//        example1();
//        example2();
//        example3();
        example4();
    }

    private static void example4() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
        for (int i = 0; i < 3; i++) {
            // Чтобы передать i надо сделать копию
            final int fi = i;
            new Thread(() -> {
                try {
                    System.out.println("Thread " + fi + " is not ready");
                    Thread.sleep((long) (100 + (30 * Math.random())));
                    System.out.println("Thread " + fi + " created");
                    cyclicBarrier.await();
                    System.out.println("Thread " + fi + " started");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }


    private static void example3() {
        Lock lock = new ReentrantLock();
        Thread tA = new Thread(() -> {
            System.out.println("поток А стартует");
            lock.lock();
            try {
                System.out.println("поток А вошел в критическую секцию");
                for (int i = 0; i < 5; i++) {
                    Thread.sleep(50);
                    System.out.println("поток А работает");
                }
                System.out.println("поток А выходит из критической секции");
            } catch (InterruptedException e) {
                System.out.println("ой-ой");
            } finally {
                lock.unlock();
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("поток А заканчивается");
        });
        Thread tB = new Thread(() -> {
            System.out.println("поток B стартует");
            lock.lock();
            try {
                System.out.println("поток B вошел в критическую секцию");
                for (int i = 0; i < 4; i++) {
                    try {
                        Thread.sleep(60);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("поток B работает");
                }
                System.out.println("поток B выходит из критической секции");
            } finally {
                lock.unlock();
            }
            try {
                Thread.sleep(60);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("поток B заканчивается");
        });
        tA.start();
        tB.start();

        Thread tC = new Thread(() -> {
            System.out.println("поток C стартует");

            if (lock.tryLock()) {
                try {
                    System.out.println("поток C вошел в критическую секцию");
                    for (int i = 0; i < 5; i++) {
                        Thread.sleep(30);
                    }
                    System.out.println("поток C работает");
                } catch (InterruptedException e) {
                    System.out.println("ой");
                } finally {
                    System.out.println("поток C выходит из критической секции");
                    lock.unlock();
                }
            } else {
                System.out.println("поток C плюнул и ушел");
            }

            try {
                Thread.sleep(60);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("поток C заканчивается");
        });
        tC.start();
        Thread tD = new Thread(() -> {
            System.out.println("поток D стартует");
            try {
                if (lock.tryLock(600, TimeUnit.MILLISECONDS)) {
                    System.out.println("поток D вошел в критическую секцию");
                    for (int i = 0; i < 5; i++) {
                        try {
                            Thread.sleep(30);
                        } catch (InterruptedException e) {
                            System.out.println("ой");
                        }
                        System.out.println("поток D работает");
                    }
                    System.out.println("поток D выходит из критической секции");
                    lock.unlock();
                } else System.out.println("поток D плюнул и ушел");
                Thread.sleep(60);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("поток D заканчивается");
        });
        tD.start();
    }

    private static void example2() throws InterruptedException {
        AtomicReferenceFieldUpdater<Person, String> renamer = AtomicReferenceFieldUpdater.newUpdater(Person.class, String.class, "name");
        Person p = new Person("person1");
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + " p.getName() = " + p.name);
            renamer.getAndSet(p, "person2");
            System.out.println(Thread.currentThread().getName() + " p.getName() = " + p.name);
        });
        t1.start();
        System.out.println("p.getName() = " + p.name);
        t1.join();
        System.out.println("p.getName() = " + p.name);
    }

    private static void example1() {
        AtomicInteger a = new AtomicInteger(1);
        Thread t1 = new Thread(() -> {
            System.out.println("Begin 1");
            for (int i = 0; i < 7; i++) {
//                a++;      Нельзя, так как она не Atomic
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(a + " increment"); // Можем
                a.getAndSet(a.get() + 1);
            }
            System.out.println("End 1");
        });
        t1.start();

        Thread t2 = new Thread(() -> {
            System.out.println("Begin 2");
            for (int i = 0; i < 7; i++) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(a + " multiply 2");
                a.getAndSet(a.get() * 2);
            }
            System.out.println("End 2");
        });
        t2.start();
    }
}


class Person {

    public volatile String name;

    public Person(String name) {
        this.name = name;
    }
}