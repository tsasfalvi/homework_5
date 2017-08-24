package com.homework4.behavior;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class RemoveEndsWithOne extends AbstractConsumerBehavior<String> {
    private final BlockingQueue<String> result;
    private final CountDownLatch latch;

    public RemoveEndsWithOne(BlockingQueue<String> queue, BlockingQueue<String> result, CountDownLatch latch) {
        super(queue);
        this.result = result;
        this.latch = latch;
    }

    @Override
    boolean found(String element) {
        if (!INTEGER_PATTERN.matcher(element).matches()) {
            return false;
        }

        if (!element.endsWith("1")) {
            System.out.println(getLoggerName() + ": reAdd: " + element);
            getQueue().add(element);

            return true;
        } else {
            result.add(element);
            latch.countDown();
        }

        return false;
    }

    @Override
    String getLoggerName() {
        return Thread.currentThread().getName();
    }
}
