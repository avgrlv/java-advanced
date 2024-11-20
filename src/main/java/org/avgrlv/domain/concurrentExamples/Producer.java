package org.avgrlv.domain.concurrentExamples;

import java.util.Queue;

public class Producer implements Runnable {
    String name;
    Queue<String> stringQueue;

    public String getName() {
        return name;
    }

    public Producer(String name, Queue<String> queue) {
        this.name = name;
        this.stringQueue = queue;
    }

    @Override
    public void run() {
        System.out.println("Producer [" + name + "] begin working...");
        try {
            for (int i = 0; i < 10; i++) {
                String msg = "Message " + i;
                stringQueue.add(msg);
                System.out.println(this.name + " produced [" + msg + "]");
                Thread.sleep(300);
            }
            System.out.println("Producer [" + name + "] all message produced... Stopping...");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}