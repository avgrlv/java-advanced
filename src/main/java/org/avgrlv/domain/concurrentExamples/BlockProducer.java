package org.avgrlv.domain.concurrentExamples;

import java.util.concurrent.ArrayBlockingQueue;

public class BlockProducer extends Producer {
    Weakness fullProduced;

    public BlockProducer(String name,
                         ArrayBlockingQueue<String> queue,
                         Weakness weaknessFlag) {
        super(name, queue);
        this.fullProduced = weaknessFlag;
    }

    @Override
    public void run() {
        System.out.println("Block producer [" + name + "] begin working...");
        try {
            for (int i = 0; i < 10; i++) {
                String msg = "Message " + i;
                System.out.println(this.name + " created msg = " + msg);
                ((ArrayBlockingQueue<String>) stringQueue).put(msg);
                System.out.println(this.name + " produced message [" + msg + "] after put");
                Thread.sleep(300);
            }
            System.out.println("Block producer [" + name + "] all message produced... Stopping...");
            this.fullProduced.switchOff();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
