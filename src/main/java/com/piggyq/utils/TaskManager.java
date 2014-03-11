package com.piggyq.utils;

import com.piggyq.common.Task;
import com.piggyq.pq.Consumer;
import com.piggyq.pq.PriorityTransferQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by ryan on 3/11/14.
 * TM Example
 */
public class TaskManager {
    private static final Logger log = LoggerFactory.getLogger(TaskManager.class);
    private static final int N_THREADS = 5;

    public final PriorityTransferQueue<Task> taskQueue = new PriorityTransferQueue<Task>();

    public final ExecutorService consumers = Executors.newFixedThreadPool(N_THREADS);
    private static TaskManager ourInstance = new TaskManager();

    public static TaskManager getInstance() {
        return ourInstance;
    }

    private TaskManager() {
    }

    public void startLoop() {
        for (int i = 0; i != N_THREADS; i++)
            consumers.submit(new Consumer(i));
    }


    public void shutdown() {
        consumers.shutdown();
    }

    public void awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        consumers.awaitTermination(timeout, unit);
    }

    public void shutdownNow() {
        consumers.shutdownNow();
    }
}
