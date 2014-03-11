package com.piggyq.pq;

import com.piggyq.common.Task;
import com.piggyq.utils.TaskManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

/**
 * Created by ryan on 3/10/14.
 * Consumer Example
 */
public class Consumer implements Callable<String> {
    private static final Logger log = LoggerFactory.getLogger(Consumer.class);
    private int id;

    public Consumer(int id) {
        this.id = id;
    }

    @Override
    public String call() throws Exception {
        while (true) {
            log.debug("[Task consumer " + this.id + "] create ok. Waiting valid task to exec.");
            try {
                Task task = TaskManager.getInstance().taskQueue.take();
                log.debug("consumer " + this.id + " invoked, starting exec task " + task.tNo);
                task.setResultValue(task.execAndWaitFor());
                log.debug("task " + task.tNo + " exec finished!!!");
            } catch (InterruptedException e) {
                log.error("Execute task failed.");
                throw new RuntimeException();
            }
        }
//        return null;
    }
}
