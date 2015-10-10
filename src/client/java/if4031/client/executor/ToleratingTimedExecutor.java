package if4031.client.executor;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ToleratingTimedExecutor extends DelayableRepeatingExecutor {
    private final Runnable checkingRunnable;
    private final int toleranceMillis;

    private ScheduledExecutorService executorService;
    private ScheduledFuture scheduledFuture;
    private long lastRun;

    public ToleratingTimedExecutor(Runnable _runnable, int _refreshMillis, int _toleranceMillis) {
        super(_runnable, _refreshMillis);

        checkingRunnable = new Runnable() {
            @Override
            public void run() {
                long currentTime = System.currentTimeMillis();
                // if last run is more than refreshMillis ago
                if (currentTime - lastRun > getRefreshMillis()) {
                    lastRun = currentTime;
                    getRunnable().run();
                }
            }
        };

        toleranceMillis = _toleranceMillis;
    }

    public void initialize() {
        executorService = new ScheduledThreadPoolExecutor(1);
    }

    public void shutdown() {
        executorService.shutdown();
    }

    @Override
    public void start() {
        scheduledFuture = executorService.scheduleWithFixedDelay(checkingRunnable, 0, toleranceMillis, TimeUnit.MILLISECONDS);
    }

    @Override
    public void mark() {
        lastRun = System.currentTimeMillis();
    }

    @Override
    public void stop() {
        scheduledFuture.cancel(false);
    }

    @Override
    public ExecutorState getStatus() {
        if (scheduledFuture != null && !scheduledFuture.isCancelled()) {
            return ExecutorState.STARTED;
        } else {
            return ExecutorState.STOPPED;
        }
    }
}
