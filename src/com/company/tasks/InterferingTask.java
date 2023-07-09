package com.company.tasks;

public class InterferingTask implements Runnable {
    final int id;
    //可变共享状态，这就是问题所在：多个任务同时修改同一个变量会产生竞争。结果取决于首先在终点线上执行哪个任务，并修改变量
    private static Integer val = 0;
    public InterferingTask(int id) {
        this.id = id;
    }
    @Override
    public void run() {
        // 缺乏线程安全，当多线程同时改变val的值，那么val的值变得不准确
        for(int i = 0; i < 100; i++)
            val++;
        System.out.println(id + " "+
            Thread.currentThread().getName() + " " + val);
    }
}
