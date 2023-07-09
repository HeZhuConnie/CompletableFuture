package com.company.tasks;

import java.util.concurrent.atomic.AtomicBoolean;

public class QuittableTask implements Runnable {
    final int id;

    public QuittableTask(int id) {
        this.id = id;
    }

    // 当涉及到running被赋值时，就会出现多个线程同时改变这个值的情况，就要用atomic的类型
    private AtomicBoolean running = new AtomicBoolean(true);

    public void quit() {
        // 这里是被main线程调了
        System.out.println(id + " " + Thread.currentThread().getName() + " q");
        running.set(false);
    }

    @Override
    public void run() {
        while (running.get()) {
            System.out.println(id + " " + Thread.currentThread().getName());

            new Nap(0.2);
        }
        System.out.println(id + " ");
    }
}
