package com.piggyq.pq;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TransferQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by ryan on 3/10/14.
 * PQ Example
 */
public class PriorityTransferQueue<E> extends PriorityBlockingQueue<E> implements TransferQueue<E> {
    private AtomicInteger counter;
    private LinkedBlockingDeque<E> transferred;
    private ReentrantLock lock;

    public PriorityTransferQueue() {
        this.counter = new AtomicInteger(0);
        this.transferred = new LinkedBlockingDeque<E>();
        this.lock = new ReentrantLock();
    }


    @Override
    public boolean tryTransfer(E e) {
        this.lock.lock();
        boolean value;
        if (this.counter.get() == 0) {
            value = false;
        } else {
            put(e);
            value = true;
        }
        this.lock.unlock();
        return value;
    }

    @Override
    public void transfer(E e) throws InterruptedException {
        this.lock.lock();
        if (this.counter.get() != 0) {
            put(e);
            this.lock.unlock();
        } else {
            this.transferred.add(e);
            this.lock.unlock();
            synchronized (e) {
                e.wait();
            }
        }
    }

    @Override
    public boolean tryTransfer(E e, long timeout, TimeUnit unit) throws InterruptedException {
        this.lock.lock();
        if (this.counter.get() != 0) {
            put(e);
            this.lock.unlock();
            return true;
        }
        this.transferred.add(e);
        long newTimeout = TimeUnit.MILLISECONDS.convert(timeout, unit);
        this.lock.unlock();
        e.wait(newTimeout);
        this.lock.lock();
        if (this.transferred.contains(e)) {
            this.transferred.remove(e);
            this.lock.unlock();
            return false;
        }
        this.lock.unlock();
        return true;
    }

    @Override
    public boolean hasWaitingConsumer() {
        return this.counter.get() != 0;
    }

    @Override
    public int getWaitingConsumerCount() {
        return this.counter.get();
    }
}
