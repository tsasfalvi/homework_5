package com.homework4.behavior;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class RemoveProductsOfThree extends AbstractConsumerBehavior<String> {
    private final BlockingQueue<String> result;
    private final CountDownLatch latch;

    public RemoveProductsOfThree(BlockingQueue<String> queue, BlockingQueue<String> result, CountDownLatch latch) {
        super(queue);
        this.result = result;
        this.latch = latch;
    }

    @Override
    String getLoggerName() {
        return Thread.currentThread().getName();
    }

    @Override
    boolean found(String element) {
        if (!INTEGER_PATTERN.matcher(element).matches()) {
            return false;
        }

        if (Integer.parseInt(element) % 3 != 0) {
            System.out.println(getLoggerName() + ": reAdd: " + element);
            getQueue().add(element);
            return true;
        } else {
            result.add(element);
            latch.countDown();
        }

        return false;
    }
}
