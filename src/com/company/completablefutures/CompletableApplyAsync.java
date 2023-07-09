package com.company.completablefutures;

import com.company.statemachine.Machina;

import java.util.concurrent.CompletableFuture;

public class CompletableApplyAsync {
    public void start() {
        CompletableFuture<Machina> cf = CompletableFuture.completedFuture(new Machina(0));
        CompletableFuture<Machina> cf2 = cf.thenApplyAsync(Machina::work)
            .thenApplyAsync(Machina::work)
            .thenApplyAsync(Machina::work)
            .thenApplyAsync(Machina::work);
        System.out.println("work has been created");
        System.out.println(cf2.join());
        System.out.println("work has been joined");
    }
}
