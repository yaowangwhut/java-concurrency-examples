package com.piggyq.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by ryan on 3/11/14.
 * LongTimeProcess Example
 */
public class LongTimeProcess {
    private static final Logger log = LoggerFactory.getLogger(LongTimeProcess.class);

    private String outputResult = null;

    public void execute() throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        outputResult="Done";
    }

    public String waitFor(){
        return outputResult;
    }

}
