package com.company.completablefutures.utils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableUtilities {
    public static void showr(CompletableFuture<?> c) {
        try {
            System.out.println(c.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException();
        }
    }

    public static void voidr(CompletableFuture<Void> c) {
        try {
            c.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException();
        }
    }
}
