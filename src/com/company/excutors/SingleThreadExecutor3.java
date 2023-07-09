package com.company.excutors;

import com.company.tasks.InterferingTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class SingleThreadExecutor3 {
    public void start() {
        ExecutorService exec = Executors.newSingleThreadExecutor();
        IntStream.range(0, 10).mapToObj(InterferingTask::new).forEach(exec::execute);
        exec.shutdown();
    }
}
