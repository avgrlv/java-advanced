package org.avgrlv.lessons.third;

import org.avgrlv.domain.concurrentExamples.*;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentDataStructure {
    public static void main(String[] args) {
//        exampleConcurrentLinkedQueue();
        exampleArrayBlockingQueue();

    }

    private static void exampleArrayBlockingQueue() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(4);
        /*
         * В случае если используем обычного Producer надо быть аккуратным,
         *  ведь мы моджем забить очередь больше чем на 4 сообщения
         * и получим java.lang.IllegalStateException: Queue full
         * Сделаем для этого BlockProducer
         * */
        Weakness weaknessFlag = new Weakness();
        BlockProducer blockProducer = new BlockProducer("John", queue, weaknessFlag);
        BlockConsumer consumer = new BlockConsumer("Andrey", queue, 2, weaknessFlag);
        new Thread(blockProducer).start();
        new Thread(consumer).start();
    }

    private static void exampleConcurrentLinkedQueue() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        Producer producer = new Producer("Alex", queue);
        Consumer consumer = new Consumer("Market", queue, 2);
        new Thread(producer).start();
        new Thread(consumer).start();
    }
}




