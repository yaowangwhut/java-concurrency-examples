package com.piggyq.pq;

import com.piggyq.common.Task;
import com.piggyq.utils.TaskManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ryan on 3/10/14.
 * Producer Example
 */
public class Producer {
    private static final Logger log = LoggerFactory.getLogger(Producer.class);

    public static void product() {
        Task task = new Task();
        TaskManager.getInstance().taskQueue.put(task);
        log.info("Producer create task " + task.tNo);
    }
}
