package org.avgrlv.lessons;

import java.util.Arrays;
import java.util.concurrent.*;

public class MultithreadingPartThree {
    static final int SIZE = 100;
    static final int HALF = SIZE / 2;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[] arr = new int[SIZE];

//        exampleSchedule();

        exampleSchedule2();

        long a = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = formula(i);
        }
        ;
        System.out.println("a = " + Arrays.toString(arr));
        long duration = System.currentTimeMillis() - a;
        System.out.println("duration simple for = " + duration);

        a = System.currentTimeMillis();
        fillArrayByThread(arr);
        duration = System.currentTimeMillis() - a;
        System.out.println("duration threads = " + duration);

        a = System.currentTimeMillis();
        fillArrayByFuture(arr);
        duration = System.currentTimeMillis() - a;
        System.out.println("duration futures = " + duration);


    }

    private static void exampleSchedule2() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
        scheduledExecutorService.scheduleWithFixedDelay(() -> {
            try {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + "  Suuuuuu");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, 0, 500, TimeUnit.MILLISECONDS);
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            try {
                Thread.sleep(500);
                System.out.println(Thread.currentThread().getName() + " YEEEP by rate");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, 0, 750, TimeUnit.MILLISECONDS);

        scheduledExecutorService.schedule(() -> {
            System.out.println("shutting down");
            scheduledExecutorService.shutdown();
        }, 12, TimeUnit.SECONDS);

    }

    private static void fillArrayByThread(int[] arr) throws InterruptedException {
        /*
         * 1. Разбить на 2 подмассива
         * 2. Запустить потоки по каждому из подмассивов
         * 3. Соединить подмассивы обратно
         * */
        int[] partitionLeft = new int[HALF];
        int[] partitionRight = new int[HALF]; //если бы нас интересовали исходные элементы, то тут было бы копирование

        Thread partA = new Thread(() -> arrayFilling(partitionLeft, 0));
        Thread partB = new Thread(() -> arrayFilling(partitionRight, HALF));

        partA.start();
        partB.start();
        partA.join();
        partB.join();

        System.arraycopy(partitionLeft, 0, arr, 0, HALF);
        System.arraycopy(partitionRight, 0, arr, HALF, HALF);
        System.out.println("Arrays.toString(arr) = " + Arrays.toString(arr));
    }

    private static void fillArrayByFuture(int[] arr) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<int[]> partitionLeft = executorService.submit(() -> arrayFilling(0));
        Future<int[]> partitionRight = executorService.submit(() -> arrayFilling(HALF));

        System.out.println("state = " + partitionLeft.state());
        int[] leftResult = partitionLeft.get();
        int[] rightResult = partitionRight.get();
        System.out.println("state = " + partitionLeft.state());

        System.arraycopy(leftResult, 0, arr, 0, HALF);
        System.arraycopy(rightResult, 0, arr, HALF, HALF);
        System.out.println("Arrays.toString(arr) = " + Arrays.toString(arr));
        executorService.shutdown();
    }

    private static int[] arrayFilling(int n) {
        int[] arr = new int[HALF];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = formula(n++);
        }
        return arr;
    }

    private static void arrayFilling(int[] arr, int n) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = formula(n++);
        }
    }

    public static int formula(int x) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return x * 2;
    }

    public static void exampleSchedule() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
        scheduledExecutorService.schedule(() ->
                        System.out.println(Thread.currentThread().getName() + " Hellooo by schedule"),
                10,
                TimeUnit.SECONDS);
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            System.out.println(Thread.currentThread().getName() + " Hellooo by rate");
        }, 1, 2, TimeUnit.SECONDS);

        scheduledExecutorService.schedule(scheduledExecutorService::shutdown, 12, TimeUnit.SECONDS);
    }
}
