package com.homework4;

import com.homework4.behavior.ConsumerBehavior;

public class Consumer implements Runnable {
    private ConsumerBehavior consumerBehavior;

    public Consumer(ConsumerBehavior consumerBehavior) {
        this.consumerBehavior = consumerBehavior;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            consumerBehavior.execute();
        }
    }
}
