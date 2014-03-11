package com.piggyq.pq;

import com.piggyq.utils.TaskManager;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

/**
 * Created by ryan on 3/11/14.
 * UT
 */
public class MultiProducerMultiConsumer {
    @Test
    public void testPQ() throws InterruptedException {
        TaskManager tm = TaskManager.getInstance();
        tm.startLoop();
        for (int i = 0; i != 100; ++i) {
            Producer.product();
        }
        TimeUnit.SECONDS.sleep(15);
    }
}
