package com.kixs.other.sync;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * CompletableFutureTest
 *
 * @author wangbing
 * @version v1.0.0
 * @since 2021/5/26 10:53
 */
public class CompletableFutureTest {

    private final static int CORE = Runtime.getRuntime().availableProcessors();

    private final static ThreadPoolExecutor POOL = new ThreadPoolExecutor(
            CORE,
            CORE * 2,
            60,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(50),
            new ThreadPoolExecutor.CallerRunsPolicy());

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // CompletableFuture原生方法分组
        // testCompletableFutureFunction();
        // CompletionStage方法分组-串行执行-thenRun
        // testCompletionStageAndThenRun();
        // CompletionStage方法分组-串行执行-thenAccept
        // testCompletionStageAndThenAccept();
        // CompletionStage方法分组-串行执行-thenCompose
        // testCompletionStageAndThenCompose();
        // 举例测试
        exampleTest();

    }

    /**
     * 举例测试
     */
    private static void exampleTest() {
        System.out.println("-------------------------------------\nCompletableFuture举例测试");
        // 找出茶壶、茶杯；洗茶壶；接水；烧水；洗茶杯；找茶叶；把茶叶倒入茶壶；用烧好的水冲泡茶壶中的茶叶；冲泡好倒入茶杯中；奉茶给主人和客人饮用；茶喝完了谈正事。
        System.out.println("主人带了3个朋友回家，吩咐3个侍女奉茶好与朋友共商大事");
        ThreadPoolExecutor handmaidPool = new ThreadPoolExecutor(
                3,
                3,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(Integer.MAX_VALUE),
                new ThreadFactoryBuilder().setNameFormat("侍女-%d").build(),
                new ThreadPoolExecutor.CallerRunsPolicy());

        Supplier<String> findTeapotTask = () -> {
            String handmaid = Thread.currentThread().getName();
            System.out.println(handmaid + "说：我去找茶壶");
            System.out.println(handmaid + "花1分钟去找茶壶");
            sleep(1000);
            System.out.println(handmaid + "说：我已经找到茶壶了。说着拿出了主人的精美茶壶");
            return "精美茶壶";
        };

        Supplier<String> findTeacupTask = () -> {
            String handmaid = Thread.currentThread().getName();
            System.out.println(handmaid + "说：我去找茶杯");
            System.out.println(handmaid + "花1分钟去找茶杯");
            sleep(1000);
            System.out.println(handmaid + "说：我已经找到茶杯了。说着拿出了主人的4个高级茶杯");
            return "4个高级茶杯";
        };

        Function<String, String> washTeapotTask = teapot -> {
            String handmaid = Thread.currentThread().getName();
            System.out.println(handmaid + "说：我去洗茶壶。说着接过了" + teapot);
            System.out.println(handmaid + "花2分钟去洗茶壶");
            sleep(2000);
            System.out.println(handmaid + "说：我已经洗好茶壶了。说着拿出了洗干净了" + teapot);
            return "洗净的" + teapot;
        };

        Function<String, String> washTeacupTask = teacup -> {
            String handmaid = Thread.currentThread().getName();
            System.out.println(handmaid + "说：我去洗茶杯。说着接过了" + teacup);
            System.out.println(handmaid + "花4分钟去洗茶杯");
            sleep(4000);
            System.out.println(handmaid + "说：我已经洗好茶杯了。说着拿出了洗干净了" + teacup);
            return "洗净的" + teacup;
        };

        Supplier<String> findWaterTask = () -> {
            String handmaid = Thread.currentThread().getName();
            System.out.println(handmaid + "说：我去接水。说着去接水了");
            System.out.println(handmaid + "花3分钟去接水");
            sleep(3000);
            System.out.println(handmaid + "说：我已经接好水了。说着拿出了接满水的水桶");
            return "装满水的水桶";
        };

        Function<String, String> boilWaterTask = bucket -> {
            String handmaid = Thread.currentThread().getName();
            System.out.println(handmaid + "说：我去烧水吧。说着去接过了" + bucket);
            System.out.println(handmaid + "花10分钟去烧水");
            sleep(10000);
            System.out.println(handmaid + "说：我已经烧好水了。说着展示已经烧好了水");
            return "开水";
        };

        Supplier<String> findTeaTask = () -> {
            String handmaid = Thread.currentThread().getName();
            System.out.println(handmaid + "说：我去找茶叶");
            System.out.println(handmaid + "花1分钟去找茶叶");
            sleep(1000);
            System.out.println(handmaid + "说：我已经找到茶叶了。说着拿出了主人的大红袍茶叶");
            return "大红袍茶叶";
        };

        BiFunction<String, String, String> teaLeavesTeapotTask = (tea, teapot) -> {
            String handmaid = Thread.currentThread().getName();
            System.out.println(handmaid + "说：我把茶叶倒入茶壶，做好冲泡准备");
            System.out.println(handmaid + "花半分钟把" + tea + "放入" + teapot + "中");
            sleep(500);
            System.out.println(handmaid + "说：我已经把茶叶倒入茶壶做好了冲泡准备。说着拿出了装有" + tea + "的精美茶壶");
            return "装有" + tea + "的精美茶壶";
        };

        BiFunction<String, String, String> brewingTask = (boiledWater, teacup) -> {
            String handmaid = Thread.currentThread().getName();
            System.out.println(handmaid + "说：我来冲泡茶叶。说着接过了" + boiledWater + "和" + teacup);
            System.out.println(handmaid + "花1分钟把冲泡好茶叶");
            sleep(1000);
            System.out.println(handmaid + "说：我已经把茶叶冲泡好了。说着拿出了冲泡好的" + teacup);
            return "装满茶水的精美茶壶";
        };

        BiFunction<String, String, String> pourTeaTask = (teapot, teacup) -> {
            String handmaid = Thread.currentThread().getName();
            System.out.println(handmaid + "说：我来到茶水吧。说着接过了" + teacup + "和" + teapot);
            System.out.println(handmaid + "花2分钟把冲泡好茶叶");
            sleep(2000);
            System.out.println(handmaid + "说：我已经倒好茶水了，可以奉茶了。说着拿出了装满茶水的高级茶杯");
            return "装满茶水的高级茶杯";
        };

        Consumer<String> serveTeaByCupsTask = teacup -> {
            String handmaid = Thread.currentThread().getName();
            System.out.println(handmaid + "说：我来到奉茶吧。说着接过了" + teacup);
            System.out.println(handmaid + "花" + 4 + "分钟把" + teacup + "端给主人和3个客人");
            sleep(4000);
            System.out.println(handmaid + "说：请主人与客人慢用。说着慢慢退下");
        };

        CompletableFuture<String> findTeacupTaskFeature = CompletableFuture.supplyAsync(findTeacupTask, handmaidPool);
        CompletableFuture<String> washTeacupTaskFuture = findTeacupTaskFeature.thenApplyAsync(washTeacupTask, handmaidPool);

        CompletableFuture<String> findTeapotTaskFeature = CompletableFuture.supplyAsync(findTeapotTask, handmaidPool);
        CompletableFuture<String> washTeapotTaskFuture = findTeapotTaskFeature.thenApplyAsync(washTeapotTask, handmaidPool);

        CompletableFuture<String> findWaterTaskFeature = CompletableFuture.supplyAsync(findWaterTask, handmaidPool);
        CompletableFuture<String> boilWaterTaskFeature = findWaterTaskFeature.thenApplyAsync(boilWaterTask, handmaidPool);

        CompletableFuture<String> findTeaTaskFuture = CompletableFuture.supplyAsync(findTeaTask, handmaidPool);
        CompletableFuture<String> teaLeavesTeacupTaskFuture = findTeaTaskFuture.thenCombineAsync(washTeapotTaskFuture, teaLeavesTeapotTask, handmaidPool);

        CompletableFuture<String> brewingTaskFuture = boilWaterTaskFeature.thenCombineAsync(teaLeavesTeacupTaskFuture, brewingTask, handmaidPool);
        CompletableFuture<String> pourTeaTaskFuture = brewingTaskFuture.thenCombineAsync(washTeacupTaskFuture, pourTeaTask, handmaidPool);
        CompletableFuture<Void> serveTeaFeature = pourTeaTaskFuture.thenAcceptAsync(serveTeaByCupsTask, handmaidPool);

        while (!serveTeaFeature.isDone()) {
            System.out.println("茶水还未准备好，主人与客人闲聊中...");
            sleep(2000);
        }

        System.out.println("茶水已准备好，主人与客人饮茶...");
        sleep(3000);

        System.out.println("饮茶完毕，正准备共商大事，锦衣卫冲了进来，把乱臣贼子一网打尽。卒。");
    }

    /**
     * CompletionStage方法分组-串行执行-thenRun
     */
    private static void testCompletionStageAndThenRun() {
        System.out.println("-------------------------------------\nCompletionStage方法分组-串行执行测试-thenRun");
        CompletableFuture<Void> vf1 = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "--runAsync1");
        });
        vf1.thenRun(() -> {
            System.out.println(Thread.currentThread().getName() + "--thenRun1");
        });

        CompletableFuture<Void> vf2 = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "--runAsync2");
        });
        vf2.thenRunAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "--thenRunAsync1");
        });

        CompletableFuture<Void> vf3 = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "--runAsync3");
        });
        vf3.thenRunAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "--thenRunAsync2");
        }, POOL);

    }

    /**
     * CompletionStage方法分组-串行执行-thenAccept
     */
    private static void testCompletionStageAndThenAccept() {
        System.out.println("-------------------------------------\nCompletionStage方法分组-串行执行测试-thenAccept");
        CompletableFuture<Void> vf4 = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "--runAsync4");
        });
        vf4.thenAccept(t -> {
            System.out.println(Thread.currentThread().getName() + "--thenAccept1" + "\t" + t);
        });

        CompletableFuture<Void> vf5 = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "--runAsync5");
        });
        vf5.thenAcceptAsync(t -> {
            System.out.println(Thread.currentThread().getName() + "--thenAcceptAsync1" + "\t" + t);
        });

        CompletableFuture<String> vf6 = CompletableFuture.supplyAsync(() -> {
            sleep(500);
            return Thread.currentThread().getName() + "--supplyAsync1";
        });
        vf6.thenAcceptAsync(t -> {
            System.out.println(Thread.currentThread().getName() + "--thenAcceptAsync2" + "\t" + t);
        }, POOL);
    }

    /**
     * CompletionStage方法分组-串行执行-thenCompose
     */
    private static void testCompletionStageAndThenCompose() throws ExecutionException, InterruptedException {
        System.out.println("-------------------------------------\nCompletionStage方法分组-串行执行测试-thenCompose");
        CompletableFuture<String> vf1 = CompletableFuture.supplyAsync(() -> {
            return Thread.currentThread().getName() + "--supplyAsync1";
        });
        CompletableFuture<Integer> tc1 = vf1.thenCompose(t -> {
            return CompletableFuture.supplyAsync(() -> t.length());
        });
        System.out.println(tc1.get());

        CompletableFuture<String> vf2 = CompletableFuture.supplyAsync(() -> {
            return Thread.currentThread().getName() + "--supplyAsync2";
        });
        CompletableFuture<Integer> tc2 = vf2.thenComposeAsync(t -> {
            return CompletableFuture.supplyAsync(() -> t.length());
        });
        System.out.println(tc2.get());

        CompletableFuture<String> vf3 = CompletableFuture.supplyAsync(() -> {
            return Thread.currentThread().getName() + "--supplyAsync3";
        }, POOL);
        CompletableFuture<Integer> tc3 = vf3.thenComposeAsync(t -> {
            return CompletableFuture.supplyAsync(() -> t.length());
        }, POOL);
        System.out.println(tc3.get());
    }

    /**
     * CompletableFuture原生方法分组
     */
    private static void testCompletableFutureFunction() throws ExecutionException, InterruptedException {
        System.out.println("-------------------------------------\nCompletableFuture.runAsync-测试");
        CompletableFuture<Void> vf1 = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "--runAsync1");
        });
        CompletableFuture<Void> vf2 = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "--runAsync2");
        }, POOL);
        vf1.join();
        vf2.join();
        System.out.println(vf1.isDone());
        System.out.println(vf2.isDone());

        System.out.println("CompletableFuture.supplyAsync-测试");
        CompletableFuture<String> vf3 = CompletableFuture.supplyAsync(() -> {
            sleep(500);
            return Thread.currentThread().getName() + "--supplyAsync1";
        });
        CompletableFuture<String> vf4 = CompletableFuture.supplyAsync(() -> {
            sleep(1000);
            return Thread.currentThread().getName() + "--supplyAsync2";
        }, POOL);
        System.out.println(vf3.get());
        System.out.println(vf4.get());
    }


}
