package com.homework4;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public class Producer implements Runnable {
    private BlockingQueue<String> queue;

    Producer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        while (!Thread.currentThread().isInterrupted()) {
            String generated = generate();

            System.out.println(name + " : adding " + generated);
            queue.add(generated);

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println(name + " has been interrupted. Shutting down.");
    }

    private String generate() {
        if (System.currentTimeMillis() %5 == 0) {
            return "string";
        }
        return ThreadLocalRandom.current().nextInt(1, 1001) + "";
    }
}
