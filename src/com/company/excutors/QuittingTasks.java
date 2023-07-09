package com.company.excutors;

import com.company.tasks.Nap;
import com.company.tasks.QuittableTask;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class QuittingTasks {
    private static final int COUNT = 150;

    public void start() {
        ExecutorService exec = Executors.newCachedThreadPool();
        List<QuittableTask> tasks = IntStream.range(0, 10)
            .mapToObj(QuittableTask::new)
            .peek(qt -> exec.execute(qt))
            .collect(Collectors.toList());
        new Nap(1);
        tasks.forEach(QuittableTask::quit);
        exec.shutdown();
    }
}
