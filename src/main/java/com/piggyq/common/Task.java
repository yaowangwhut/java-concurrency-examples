package com.piggyq.common;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by ryan on 3/11/14.
 * Task Example
 */

public class Task implements Comparable<Task> {


    public long tNo = TaskNoFactory.getTaskNo();

    protected LongTimeProcess process = new LongTimeProcess();

    protected String resultValue = null;

    public String execAndWaitFor() throws InterruptedException {
        this.process.execute();
        return this.process.waitFor();
    }

    public void setResultValue(String resultValue) {
        this.resultValue = resultValue;
    }


    @Override
    public int compareTo(Task o) {
        return (int) (o.tNo - this.tNo);
    }

    private final static class TaskNoFactory {
        private static AtomicLong id = new AtomicLong(0);

        public static long getTaskNo() {
            return id.getAndIncrement();
        }
    }
}
