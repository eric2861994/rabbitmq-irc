package if4031.client.executor;

/**
 * Repeatedly execute something every refreshMillis interval.
 * When marked, the next execution is performed refreshMillis later.
 */
public abstract class DelayableRepeatingExecutor {
    private final Runnable runnable;
    private final int refreshMillis;

    DelayableRepeatingExecutor(Runnable _runnable, int _refreshMillis) {
        runnable = _runnable;
        refreshMillis = _refreshMillis;
    }

    protected Runnable getRunnable() {
        return runnable;
    }

    protected int getRefreshMillis() {
        return refreshMillis;
    }

    // BEGIN exposed methods

    /**
     * Must only be run once, counter is reset on stop().
     */
    public abstract void start();

    /**
     * Pre Cond. : Must call start() first
     */
    public abstract void mark();

    /**
     * Must only run once, counter is reset on start().
     * Pre Cond. : Must call start() first
     */
    public abstract void stop();

    public abstract ExecutorState getStatus();

    public enum ExecutorState {
        STARTED,
        STOPPED
    }
}
