package com.github.zengchao1212.sms;

import com.github.zengchao1212.sms.service.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zengchao
 * @date 2020-09-02
 */
public class TaskExecutor {
    private final static TaskExecutor INSTANCE = new TaskExecutor();
    private final Map<String, TaskStatus> task = new ConcurrentHashMap<>();
    private final List<SmsBoom> smsBooms = new CopyOnWriteArrayList<>();
    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());

    private TaskExecutor() {
        Properties config = new Properties();
        try (InputStream inputStream = getClass().getResourceAsStream("/task.properties")) {
            config.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Class<? extends SmsBoom>> classes = new LinkedList<>();
        classes.add(AbcRegister.class);
        classes.add(BaiduLogin.class);
        classes.add(ChinaMobileLogin.class);
        classes.add(DoushiyiguiRegister.class);
        classes.add(IcbcForgetPwd.class);

        classes.forEach(cls -> {
            String forbidden = config.getProperty(cls.getSimpleName());
            if (!"false".equals(forbidden)) {
                try {
                    smsBooms.add(cls.newInstance());
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static TaskExecutor getInstance() {
        return INSTANCE;
    }

    public void addTask(String mobile, int count, long period, TimeUnit unit) {
        ScheduledFuture scheduledFuture = executor.scheduleWithFixedDelay(() -> {
            smsBooms.forEach(smsBoom -> {
                executor.execute(() -> {
                    boolean success = smsBoom.send(mobile);
                    if (success) {
                        TaskStatus taskStatus = task.get(mobile);
                        int successCount = taskStatus.successCount.incrementAndGet();
                        if (taskStatus.count <= successCount) {
                            taskStatus.scheduledFuture.cancel(false);
                            task.remove(mobile);
                            System.out.println("任务执行完成" + mobile);
                        }
                    }
                });
            });
        }, 5, period, unit);
        TaskStatus taskStatus = task.putIfAbsent(mobile, new TaskStatus(count, scheduledFuture));
        if (taskStatus != null) {
            taskStatus.scheduledFuture.cancel(false);
        }
    }

    public void cancelTask(String mobile) {
        TaskStatus taskStatus = task.get(mobile);
        if (taskStatus != null) {
            taskStatus.scheduledFuture.cancel(false);
        }
    }

    private static class TaskStatus {
        private final int count;
        private final ScheduledFuture scheduledFuture;
        private AtomicInteger successCount;

        public TaskStatus(int count, ScheduledFuture scheduledFuture) {
            this.count = count;
            this.scheduledFuture = scheduledFuture;
            this.successCount = new AtomicInteger(0);
        }
    }
}
