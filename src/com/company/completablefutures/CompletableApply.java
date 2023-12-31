package com.company.completablefutures;

import com.company.statemachine.Machina;

import java.util.concurrent.CompletableFuture;

public class CompletableApply {
    public void start() {
        CompletableFuture<Machina> cf = CompletableFuture.completedFuture(new Machina(0));
        CompletableFuture<Machina> cf2 = cf.thenApply(Machina::work);
        CompletableFuture<Machina> cf3 = cf2.thenApply(Machina::work);
        CompletableFuture<Machina> cf4 = cf3.thenApply(Machina::work);
        CompletableFuture<Machina> cf5 = cf4.thenApply(Machina::work);
        System.out.println("work has been finished");
    }
}
