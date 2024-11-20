package org.avgrlv.lessons.second;

import org.avgrlv.domain.Box;

import java.util.List;
import java.util.concurrent.*;

public class MultithreadingPartTwo {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println("=======Example 1========");
//        example1();
        System.out.println("=======Example 2========");
//        example2();
        System.out.println("=======Example 3========");
//        example3();
        System.out.println("=======Example 4========");
        example4();
    }

    private static void example4() throws ExecutionException, InterruptedException {
        /*
         * Callback функции обратного вызова.
         * Это объекты инерфейса Callable
         * */
        int a = 23;
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        Future<Integer> future1 = executorService.submit(new Callable<Integer>() {

            @Override
            public Integer call() {
                for (int i = 0; i < 5; i++) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("что-то вычисляем " + i);
                }
                return 501;
            }
        });

        Future<Integer> future2 = executorService.submit(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("длительное вычисление " + i);
            }
            return 500;
        });
        Thread.sleep(3000);
        if (future1.isDone()) {
            System.out.println("future1 = " + future1.get());
            System.out.println("future2 = " + future2.get());
        } else {
            System.out.println("future2 = " + future2.get());
            System.out.println("future1 = " + future1.get());
        }
//        System.out.println("a = " + a);
        executorService.shutdown();
    }

    private static void example3() {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.execute(() -> System.out.println("асинхронный поток " + Thread.currentThread()));
        executorService.execute(() -> System.out.println("другой асинхронный поток " + Thread.currentThread()));
        executorService.execute(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("длиннный поток " + i);
            }
        });
        executorService.shutdown();
    }

    private static void example2() {
        Box commonBox = new Box();
        List<Thread> threads = List.of(
                new Thread(() -> workWithListBox(commonBox, "a", 7, 150)),
                new Thread(() -> workWithListBox(commonBox, "B", 5, 200)),
                new Thread(() -> workWithListBox(commonBox, "C", 25, 50))
        );
        threads.forEach(Thread::start);
        try {
            Thread.sleep(1300);
            System.out.println("commonBox = " + commonBox);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void workWithListBox(Box commonBox,
                                        String msg,
                                        int n,
                                        int x) {

        for (int i = 0; i < n; i++) {
            try {
                Thread.sleep(x);
                commonBox.appendToList(msg);
                System.out.println(Thread.currentThread().getName() + " " + (int) ((i / (double) n) * 100) + " %");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private static void example1() {
        Box commonBox = new Box();
        List<Thread> threads = List.of(
                new Thread(() -> workWithBox(commonBox, "a", 7, 150)),
                new Thread(() -> workWithBox(commonBox, "B", 5, 200)),
                new Thread(() -> workWithBox(commonBox, "C", 25, 50))
        );
        threads.forEach(Thread::start);
        try {
//            Thread.sleep(1100);
            for (Thread thread : threads) {
                thread.join();  // Будет дожидаться самого последнего потока,
                // и только после этого продолжит выполнение
            }
            System.out.println("commonBox = " + commonBox);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("a: " + commonBox.getValue().chars().filter(x -> x == 'a').count());
        System.out.println("B: " + commonBox.getValue().chars().filter(x -> x == 'B').count());
        System.out.println("C: " + commonBox.getValue().chars().filter(x -> x == 'C').count());
    }

    public static void workWithBox(Box box,
                                   String msg,
                                   int n,
                                   int x) {
        for (int i = 0; i < n; i++) {
            try {
                Thread.sleep(x);
                box.append(msg);  // если метод синхронизированный
                // то одновременные вызовы будут обработаны последовательно
                System.out.println(Thread.currentThread().getName() + " " + (int) ((i / (double) n) * 100) + " %");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
