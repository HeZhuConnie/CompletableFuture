package com.company.excutors;

import com.company.tasks.Nap;
import com.company.tasks.QuittableTask;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class QuittingCompletable {
    public void start() {
        List<QuittableTask> tasks = IntStream.range(0, 10)
            .mapToObj(QuittableTask::new)
            .collect(Collectors.toList());

        // 将每个任务都交给CompletableFuture::runAsync
        List<CompletableFuture<Void>> cFutures = tasks.stream()
            .map(CompletableFuture::runAsync)
            .collect(Collectors.toList());

        new Nap(1);

        tasks.forEach(QuittableTask::quit);
        // 调用join来等待任务完成
        cFutures.forEach(CompletableFuture::join);
    }
}
