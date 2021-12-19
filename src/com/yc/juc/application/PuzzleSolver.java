package com.yc.juc.application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

public class PuzzleSolver<P, M> extends ConcurrentPuzzle<P, M> {
    private final AtomicInteger count = new AtomicInteger(0);

    public PuzzleSolver(Puzzle<P, M> puzzle, ExecutorService service) {
        super(puzzle, service);
    }

    @Override
    protected Runnable newTask(P pos, M move, Node<P, M> node) {
        return new CountingSolverTask(pos, move, node);
    }

    class CountingSolverTask extends SolverTask {
        CountingSolverTask(P pos, M move, Node<P, M> node) {
            super(pos, move, node);
            count.incrementAndGet();
        }

        @Override
        public void run() {
            try {
                super.run();
            } finally {
                if (count.decrementAndGet() == 0) {
                    solution.setValue(null);
                }
            }
        }
    }
}
