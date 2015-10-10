package if4031.client.executor;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Deprecated
class RestartableSingleExecutor {
    private final Runnable runnable;
    private final int refreshMillis;

    private ScheduledExecutorService executorService;

    RestartableSingleExecutor(Runnable _runnable, int refreshTime) {
        runnable = _runnable;
        refreshMillis = refreshTime;
    }

    ServiceState getStatus() {
        if (executorService != null) {
            return ServiceState.STARTED;
        } else {
            return ServiceState.STOPPED;
        }
    }

    public void restart() {
        stop();
        executorService = new ScheduledThreadPoolExecutor(1);
        executorService.scheduleWithFixedDelay(runnable, 0, refreshMillis, TimeUnit.MILLISECONDS);
    }

    public void stop() {
        if (executorService != null) {
            if (!executorService.isShutdown()) {
                executorService.shutdownNow();
            }

            executorService = null;
        }
    }

    enum ServiceState {
        STARTED,
        STOPPED
    }
}
