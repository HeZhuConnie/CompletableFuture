package com.company.completablefutures;

import com.company.completablefutures.utils.CompletableUtilities;
import com.company.tasks.Nap;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.company.completablefutures.utils.CompletableUtilities.*;

public class CompletableOperations {
    static CompletableFuture<Integer> cfi(int i) {
        return CompletableFuture.completedFuture(Integer.valueOf(i));
    }

    public void start() {
        System.out.printf("[%s] ", Thread.currentThread().getName());
        showr(cfi(1));

        // 1. run & supply
        // 都可以新建一个CompletableFuture对象
        voidr(
            // 初始化CompletableFuture对象，并运行异步任务，无返回值
            CompletableFuture.runAsync(() ->
                {
                    new Nap(1);
                    System.out.printf("[%s] runAsync is static\n", Thread.currentThread().getName());
                }
                // 可以传入Executor作为第二个参数，不传则默认去执行任务的线程池是ForkJoinPool
            )
        );
        showr(
            // 初始化CompletableFuture对象，并运行异步任务，有返回值（一个已经完成的CompletableFuture）
            CompletableFuture.supplyAsync(
                () -> {
                    System.out.printf("[%s] supplyAsync is static \n", Thread.currentThread().getName());
                    return 99;
                }
            )
        );
        // runAsync可以是实例方法：
        voidr(cfi(2).thenRunAsync(()-> System.out.println("thenRunAsync")));

        // 2. accept
        // 消费上一个任务的返回值
        cfi(9).thenAccept(i ->
            {
                new Nap(1);
                System.out.printf("[%s] the result from last step is: %d.\n", Thread.currentThread().getName(), i);
            }
        );
        System.out.println("----timeline1----");
        // 消费上一个任务的返回值， 并运行下一个异步任务
        cfi(4).thenAcceptAsync(
            i -> {
                if (i > 0) {
                    new Nap(1);
                }
                System.out.printf("[%s] thenAcceptAsync: %d.\n", Thread.currentThread().getName(), i);
            }
        );
        new Nap(1);

        System.out.println("----timeline2----");

        // 3. apply
        // thenApply(Function<T, U>) T: 消费上一个任务返回值类型, U: 输出类型
        // 同样是消费上一个任务的返回值，但apply需要有输出, apply更像数组的map操作符
        CompletableFuture<String> stringCF = cfi(10).thenApply(i -> {
            System.out.printf("[%s] thenApply. \n", Thread.currentThread().getName());
            return String.valueOf(i);
        });
        showr(cfi(5).thenApplyAsync(i -> i + 42));
        showr(cfi(5).thenComposeAsync(i -> CompletableFuture.completedFuture(i + 42)));

        // 4. compose
        // thenCompose(Fuction<T, CompletionStage<U>>)
        // 跟apply的功能一样，唯一区别是，compose返回的是自己包装好的CompletableFuture,
        // 而apply返回的是值，底层会把这个值包装成completableFuture
        showr(cfi(6).thenComposeAsync(i -> cfi(i + 99)));
        // 下面这两行是等效的
        showr(cfi(5).thenApplyAsync(i -> i + 42));
        showr(cfi(5).thenComposeAsync(i -> CompletableFuture.completedFuture(i + 42)));


        // 5. thenAcceptBoth
        // 在两个任务都完成后，执行任务。
        CompletableFuture<Integer> cf1 = CompletableFuture.supplyAsync(() -> 5);
        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> "do the laundry");
        showr(
            cf1.thenAcceptBoth(cf2, (v1, v2) -> {
                System.out.printf("they are %d and %s.\n", v1, v2);
            })
        );
        // acceptEither
        // acceptEither(CompletionStage<T>, Consumer<T>)
        // 只要两个任务中，任何一个完成了，则执行之后的任务, 注意两个返回值类型需要一致
        CompletableFuture<String> cf3 = CompletableFuture.supplyAsync(() -> "5");
        CompletableFuture<String> cf4 = CompletableFuture.supplyAsync(() -> "do the laundry");
        showr(
            cf3.acceptEither(cf4, (v1) -> {
                System.out.printf("it is %s.\n", v1); //这里结果有多种可能
            })
        );
        // applyToEither
        // applyToEither(CompletionStage<T>, Function<T, U>)
        // 只要两个任务中，任何一个完成了，则执行之后的任务，但需要返回一个CompletableFuture
        CompletableFuture<String> cf5 = CompletableFuture.supplyAsync(() -> "5");
        CompletableFuture<String> cf6 = CompletableFuture.supplyAsync(() -> "do the laundry");
        showr(
            cf3.applyToEither(cf4, (v1) -> {
                System.out.printf("it is %s.\n", v1); //这里结果有多种可能
                return "do later";
            })
        );

        // 6. allOf
        // 执行所有tasks
        CompletableFuture.allOf(cf1, cf2, cf3, cf4, cf5, cf6);

        // 7. thenCombine
        // thenCombine(CompletionStage<U>, BiFunction(T, U, R))
        // 当两个任务完成时，利用两个任务的结果，执行新的任务并输出值
        CompletableFuture<Integer> cf7 = CompletableFuture.supplyAsync(() -> 5);
        CompletableFuture<String> cf8 = CompletableFuture.supplyAsync(() -> "do the laundry");
        cf7.thenCombine(cf8, (v1, v2) -> v1.toString() + v2);

        // obtrudeValue(T)
        // 不管任务是否完成，将结果设置为T。
        CompletableFuture<Integer> c = cfi(7);
        c.obtrudeValue(111);
        showr(c);

        showr(cfi(8).toCompletableFuture());

        // 若c未完成，则在取值时，结果为9
        c = new CompletableFuture<>();
        c.complete(9);
        showr(c);

        // cancel 任务
        c = new CompletableFuture<>();
        c.cancel(true);
        System.out.println("cancelled: " + c.isCancelled());
        System.out.println("completed exceptionally: " + c.isCompletedExceptionally()); // true
        System.out.println("done: " + c.isDone());
        System.out.println(c);

        // 当任务未完成时，取值时拿到默认值777
        c = new CompletableFuture<>();
        System.out.println(c.getNow(777));

        c = new CompletableFuture<>();
        c.thenApplyAsync(i -> i + 42)
            .thenApplyAsync(i -> i * 12);
        System.out.println("dependents: " + c.getNumberOfDependents());
        c.thenApplyAsync(i -> i / 2);
        System.out.println("dependents: " + c.getNumberOfDependents());

        // get() throws InterruptedException, ExecutionException
        // join()
        // 这两个方法都是从CompletableFuture中取值的，调用时会阻塞当前线程
        // join方法只会抛出CancellationException和CompletionException，这两个运行时异常。
        try {
            cf1.get();
        } catch (InterruptedException |ExecutionException e) {
            throw new RuntimeException();
        }
        cf1.join();
        // 不阻塞取值
        // getNow(T): 当任务未完成时，想要取值时，给一个默认值
        cf1.getNow(4);
        // complete(T): boolean
        boolean completed = cf1.complete(1);
        // completeExceptionally()
        // 定义如果任务未完成时取值，则抛异常, 注意是join()的时候才会抛出
        cf1.completeExceptionally(new RuntimeException());

        // exceptionally
        // 如果cf1执行的任务有异常抛出，则cf1传给其消费者的值为9
        cf1.exceptionally(ex -> 9);

        // handle
        // handle(BiFunction<T, Throwable, U>)
        // 相当于thenApply和exceptionally的结合，当ok的时候作何处理，当不ok的时候作何处理
        cf1.handle((ok, notok) -> {
            if (ok == null) {
                return 992;
            }
            return ok;
        });

        // whenComplete
        // whenComplete(BiConsumer<T, Throwable>)
        // 相当于不带返回值的handle
        // 相当于处理了异常的thenApply
        cf1.whenComplete((ok, notok) -> {
            if (ok == null) {
                System.out.println("it's a good result.");
            }
            System.out.println("exception alert");
        });


    }
}
