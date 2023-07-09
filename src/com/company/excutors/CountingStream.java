package com.company.excutors;

import com.company.tasks.CountingTask;

import java.util.stream.IntStream;

public class CountingStream {
    public void start() {
        System.out.println(
            IntStream.range(0, 10)
                .parallel()
                .mapToObj(CountingTask::new)
                .map(ct -> ct.call())
                .reduce(0, Integer::sum));
    }
}
