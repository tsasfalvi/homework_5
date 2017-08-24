package com.homework4.behavior;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;

abstract class AbstractConsumerBehavior<String> implements ConsumerBehavior {
    static final Pattern INTEGER_PATTERN = Pattern.compile("\\d+");
    private final BlockingQueue<String> queue;

    AbstractConsumerBehavior(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public boolean execute() {
        String element;
        try {
            System.out.println(getLoggerName() + " about to poll");
            element = queue.poll(2, TimeUnit.MILLISECONDS);
            System.out.println(getLoggerName() + ": found " + element);
        } catch (InterruptedException e) {
            System.out.println(getLoggerName() + ": has been interrupted");
            Thread.currentThread().interrupt();
            return false;
        }

        if (!isNull(element)) {
            return found(element);
        } else {
            System.out.println(getLoggerName() + ": queue is empty");
        }

        return false;
    }

    Queue<String> getQueue() {
        return queue;
    }

    abstract boolean found(String element);

    abstract String getLoggerName();
}
