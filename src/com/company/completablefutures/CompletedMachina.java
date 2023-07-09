package com.company.completablefutures;

import com.company.statemachine.Machina;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletedMachina {
    public void start() {
        // completedFuture()创建一个已完成的CompletableFuture, 这之后唯一能做的就是get()，得到里面的值
        CompletableFuture<Machina> completableFuture =
            CompletableFuture.completedFuture(new Machina(0));

        System.out.println("Completed here");
        try {
            completableFuture.get();
        } catch (Exception e) {
            throw  new RuntimeException(e);
        }
    }
}
