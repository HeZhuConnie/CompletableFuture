package com.company.excutors;

import com.company.tasks.CountingTask;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CachedThreadPool3 {
    public void start() throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        List<CountingTask> tasks = IntStream.range(0, 10).mapToObj(CountingTask::new).collect(Collectors.toList());

        //只有在所有任务完成后，invokeAll()才会返回一个Future列表，每个任务一个Future。
        List<Future<Integer>> futures = exec.invokeAll(tasks);
        Integer sum = futures.stream().map(CachedThreadPool3::extractResult).reduce(0, Integer::sum);
        System.out.println("sum = " + sum);

        exec.shutdown();
    }

    public static Integer extractResult(Future<Integer> future) {
        try {
            // 当future上的任务没有完成，调用get()时，会产生等待（阻塞）直到结果可用
            return future.get();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
