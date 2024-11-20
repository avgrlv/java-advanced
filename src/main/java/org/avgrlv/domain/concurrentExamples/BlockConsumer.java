package org.avgrlv.domain.concurrentExamples;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class BlockConsumer extends Consumer {
    Weakness canStopConsumer;

    public BlockConsumer(String name,
                         ArrayBlockingQueue<String> queue,
                         int minMessageAmount,
                         Weakness weakFlag) {
        super(name, queue, minMessageAmount);
        this.canStopConsumer = weakFlag;
    }


    @Override
    public void run() {
        System.out.println("Consumer [" + name + "] begin working...");
        try {
            if (stringQueue instanceof ArrayBlockingQueue<String>) {
                //Будем рабоать пока не опустит флаг Producer
                if (canStopConsumer != null && canStopConsumer.getFlag())
                    super.executeWithSleepingDelay(250);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Consumer [" + name + "] stopped...");
    }
}
