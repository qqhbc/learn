package com.yc.juc.application;

import java.util.concurrent.*;

public abstract class BackgroundTask<V> implements RunnableFuture<V> {
    private final ExecutorService guiExecutor = Executors.newSingleThreadExecutor();
    private final FutureTask<V> computation = new Computation();

    class Computation extends FutureTask<V> {

        public Computation() {
            super(BackgroundTask.this::computer);
        }

        // callable调用成功后，会自动调用done()
        @Override
        protected void done() {
            boolean isCancel = false;
            V value = null;
            Throwable throwable = null;
            try {
                value = get();
            } catch (InterruptedException ignore) {
            } catch (CancellationException e) {
                isCancel = true;
            } catch (ExecutionException e) {
                throwable = e.getCause();
            } finally {
                onComputer(value, throwable, isCancel);
            }
        }
    }

    protected abstract V computer() throws Exception;

    protected void onComputer(V value, Throwable throwable, boolean isCancel) {

    }

    protected void setProcess(final int current, final int max) {
        guiExecutor.execute(() -> {
            onProcess(current, max);
        });
    }

    protected void onProcess(int current, int max) {

    }

    @Override
    public void run() {
        computation.run();
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return computation.cancel(mayInterruptIfRunning);
    }

    @Override
    public boolean isCancelled() {
        return computation.isCancelled();
    }

    @Override
    public boolean isDone() {
        return computation.isDone();
    }

    @Override
    public V get() throws InterruptedException, ExecutionException {
        return computation.get();
    }

    @Override
    public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return computation.get(timeout, unit);
    }
}
