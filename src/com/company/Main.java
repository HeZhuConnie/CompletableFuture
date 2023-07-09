package com.company;

import com.company.completablefutures.CompletableApply;
import com.company.completablefutures.CompletableApplyAsync;
import com.company.completablefutures.CompletableApplyChained;
import com.company.completablefutures.CompletableOperations;
import com.company.completablefutures.CompletedMachina;
import com.company.excutors.CachedThreadPool;
import com.company.excutors.CachedThreadPool2;
import com.company.excutors.CachedThreadPool3;
import com.company.excutors.CountingStream;
import com.company.excutors.QuittingCompletable;
import com.company.excutors.QuittingTasks;
import com.company.excutors.SingleThreadExecutor3;

public class Main {

    public static void main(String[] args) throws InterruptedException {
//        new CachedThreadPool().start();
//        new CachedThreadPool2().start();
        // 采用单线程，让线程不安全的InterferingTask变得安全，因为没有多个线程同时操作val的值
//        new SingleThreadExecutor3().start();
//        new CachedThreadPool3().start();
//        new CountingStream().start();
//          new QuittingTasks().start();
//          new QuittingCompletable().start();
//        new CompletedMachina().start();
//        new CompletableApply().start();
//        new CompletableApplyChained().start();
//        new CompletableApplyAsync().start();
        new CompletableOperations().start();
    }
}
