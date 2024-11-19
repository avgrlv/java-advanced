package org.avgrlv.practice;

public class Multithreading {
    public static void main(String[] args) {
        practice1();
    }

    private static void practice1() {
        /*
         * создать алгоритм, который будет n раз:
         * - спать по х миллисекунд и выволить в консоль
         * процент выполнения этой "работы"
         * По завершению написать "конец"
         *
         * Запустить параллельно 3 потока, выполняющих
         * этот алгоритм с разными настройками х, n
         * */

        for (int i = 1; i <= 3; i++) {
            Thread worker = new Worker("worker %s".formatted(i),
                    i * 1000,
                    i + 2);
            worker.start();
        }
    }

}

class Worker extends Thread {
    int sleepTime;
    String name;
    double sleepCount;

    public Worker(String name,
                  int sleepTime,
                  int sleepCount) {
        super("**" + name + "**");
        this.sleepTime = sleepTime;
        this.name = name;
        this.sleepCount = sleepCount;
    }

    @Override
    public void run() {
        try {
            System.out.println("Запуск " + name);
            System.out.println(name + " будет спать " + sleepCount + " раз." +
                    "\n В течение " + sleepTime + " мс");
            for (int i = 1; i <= sleepCount; i++) {
                Thread.sleep(sleepTime);
                System.out.println("Процент высыпания {" + name + "} : " + Math.round((i / sleepCount) * 100) + " %");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("Finish " + name);
        }
    }
}
