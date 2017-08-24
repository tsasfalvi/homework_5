package com.homework4;

import com.homework4.behavior.Remove;
import com.homework4.behavior.RemoveEndsWithOne;
import com.homework4.behavior.RemoveProductsOfThree;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class Main {
    public static void main(String[] args) {
        System.out.println("START");

        BlockingQueue<String> queue = new LinkedBlockingQueue<>();
        BlockingQueue<String> result = new LinkedBlockingQueue<>();
        CountDownLatch latch = new CountDownLatch(10);

        ExecutorService producerExecutor = Executors.newSingleThreadExecutor();
        producerExecutor.execute(new Producer(queue));

        ExecutorService consumerExecutor = new ScheduledThreadPoolExecutor(3);
        consumerExecutor.execute(new Consumer(new Remove(queue)));
        consumerExecutor.execute(new Consumer(new RemoveEndsWithOne(queue, result, latch)));
        consumerExecutor.execute(new Consumer(new RemoveProductsOfThree(queue, result, latch)));

        try {
            boolean timeout = latch.await(20, MILLISECONDS);
            if (!timeout) {
                System.err.println("latch could not reach zero in time: Interrupting threads");
                producerExecutor.shutdownNow();
                consumerExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            System.out.println("Main thread has been interrupted!!!!");
            Thread.currentThread().interrupt();
        }

        System.err.println(result.size() + " " + result);
    }
}
