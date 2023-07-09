package com.company.excutors;

import com.company.tasks.InterferingTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class CachedThreadPool2 {
    public void start() {
        ExecutorService exec
            = Executors.newCachedThreadPool();
        IntStream.range(0, 10)
            .mapToObj(InterferingTask::new)
            .forEach(exec::execute);
        exec.shutdown();
    }
}
