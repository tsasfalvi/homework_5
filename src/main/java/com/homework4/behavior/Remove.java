package com.homework4.behavior;

import java.util.concurrent.BlockingQueue;

import static java.lang.Integer.parseInt;

public class Remove extends AbstractConsumerBehavior<String> {

    public Remove(BlockingQueue<String> queue) {
        super(queue);
    }

    @Override
    String getLoggerName() {
        return Thread.currentThread().getName();
    }

    @Override
    boolean found(String element) {
        String threadName = Thread.currentThread().getName();

        if (!element.endsWith("1") && (INTEGER_PATTERN.matcher(element).matches() && parseInt(element) % 3 != 0)) {
            System.out.println(threadName + ": About to remove element: " + element);
            getQueue().remove();
            System.out.println(threadName + ": Removed: " + element);
        }

        return true;
    }
}
