package com.company.completablefutures;

import com.company.statemachine.Machina;
import java.util.concurrent.CompletableFuture;

public class CompletableApplyChained {
    public void start() {
        CompletableFuture.completedFuture(new Machina(0))
            .thenApply(Machina::work)
            .thenApply(Machina::work)
            .thenApply(Machina::work)
            .thenApply(Machina::work);
        System.out.println("work has been finished");
    }
}
