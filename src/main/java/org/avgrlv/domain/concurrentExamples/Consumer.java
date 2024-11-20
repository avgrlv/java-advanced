package org.avgrlv.domain.concurrentExamples;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Consumer implements Runnable {
    String name;
    Queue<String> stringQueue;
    int minMessageAmount;
    int currentMessageAmount = 0;

    public String getName() {
        return name;
    }

    public Consumer(String name, Queue<String> queue, int minMessageAmount) {
        this.name = name;
        this.stringQueue = queue;
        this.minMessageAmount = minMessageAmount;
    }

    @Override
    public void run() {
        System.out.println("Consumer [" + name + "] begin working...");
        String msg;
        try {
            /*При таком подходе возникнет ситуация, что после запуска треда очередь будет ещё пустая.
             * Надо как-то дождаться заполненеия очереди
             *   while (!stringQueue.isEmpty())
             * Узнать можно, конечно, из сотояния потока producer, но это очень опасно, да и в целом так делать лучше не надо
             * Можно укзаать минимальное количество сообщений, начиная с которого он будет слушать
             * */
            if(stringQueue instanceof ConcurrentLinkedQueue) {
                // Если сделать время чтения быстрее, чем продюсер успевает создавать сообщения,
                // то может воникнуть ситуация, что мы не дочитаем сообщения.
                // Можно решить эту проблему при помощи ArrayBlockingQueue
                executeWithSleepingDelay(500);
            }
            if(stringQueue instanceof ArrayBlockingQueue<String>){
                executeWithSleepingDelay(250);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Consumer [" + name + "] stopped...");
    }

    protected void executeWithSleepingDelay(long delay) throws InterruptedException {
        String msg;
        while (!stringQueue.isEmpty() || currentMessageAmount < minMessageAmount) {
            if ((msg = stringQueue.poll()) != null) {
                System.out.println(name + " reading message  [" + msg + "]");
                currentMessageAmount++;
            }
            Thread.sleep(delay);
        }
    }
}