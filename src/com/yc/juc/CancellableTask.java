package com.yc.juc;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.*;
import java.util.concurrent.BlockingQueue;

/**
 * 通过newTaskFor将非标准的取消操作封装在一个任务中
 *
 * @see java.util.concurrent.AbstractExecutorService (newTaskFor())
 */
public interface CancellableTask<T> extends Callable<T> {
    /**
     * 自定义一个取消方法，用于取消任务
     */
    void cancel();

    RunnableFuture<T> newTask();
}

class CancellingExecutor extends ThreadPoolExecutor {

    public CancellingExecutor() {
        super(100, 100, 6000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(10));
    }

    /**
     * ThreadPoolExecutor 构造方法没有空参的，因此需要重写父类构造方法
     *
     * @param corePoolSize    核心线程数量
     * @param maximumPoolSize 最大线程数量
     * @param keepAliveTime   存活时间
     * @param unit            单位
     * @param workQueue       阻塞队列
     */
    public CancellingExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        if (callable instanceof CancellableTask) {
            return ((CancellableTask<T>) callable).newTask();
        }
        return super.newTaskFor(callable);
    }


}

abstract class SocketUsingTask<T> implements CancellableTask<T> {
    private Socket socket;

    protected synchronized void setSocket(Socket socket) {
        this.socket = socket;
    }

    public synchronized void cancel() {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException ignore) {
        }
    }

    /**
     * 非标准取消通过调用自身cancel进行取消
     * 阻塞可以取消的按照jdk类库进行取消
     *
     * @return
     */
    @Override
    public RunnableFuture<T> newTask() {
        return new FutureTask<T>(this) {
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                boolean cancel;
                try {
                    SocketUsingTask.this.cancel();
                } finally {
                    cancel = super.cancel(mayInterruptIfRunning);
                }
                return cancel;
            }
        };
    }
}

class SocketTask extends SocketUsingTask<String> {

    public SocketTask() {
    }

    public SocketTask(Socket socket) {
        super.setSocket(socket);
    }

    @Override
    public String call() {
        return "success";
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService service = new CancellingExecutor();
        Future<String> submit = service.submit(new SocketTask());
        System.out.println(submit.get());
        service.shutdown();
    }
}


