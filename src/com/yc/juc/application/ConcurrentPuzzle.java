package com.yc.juc.application;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

class ValueLatch<T> {
    private T value;
    private final CountDownLatch countDownLatch = new CountDownLatch(1);

    public boolean isSet() {
        return (countDownLatch.getCount() == 0);
    }

    public synchronized void setValue(T newValue) {
        if (!isSet()) {
            value = newValue;
            countDownLatch.countDown();
        }
    }

    public T getValue() throws InterruptedException {
        countDownLatch.await(15, TimeUnit.SECONDS);
        synchronized (this) {
            return value;
        }
    }
}

public class ConcurrentPuzzle<P, M> {
    private final Puzzle<P, M> puzzle;
    private final Map<P, Boolean> seen = new ConcurrentHashMap<>();
    private final ExecutorService service;
    final ValueLatch<Node<P, M>> solution = new ValueLatch<>();

    public ConcurrentPuzzle(Puzzle<P, M> puzzle, ExecutorService service) {
        this.puzzle = puzzle;
        this.service = service;
    }

    public List<M> solver() throws InterruptedException {
        try {
            P init = puzzle.init();
            service.execute(newTask(init, null, null));
            Node<P, M> node = solution.getValue();
            return node.asMoveList();
        } finally {
            service.shutdown();
        }
    }

    protected Runnable newTask(P pos, M move, Node<P, M> node) {
        return new SolverTask(pos, move, node);
    }

    class SolverTask extends Node<P, M> implements Runnable {
        public SolverTask(P pos, M move, Node<P, M> node) {
            super(pos, move, node);
        }

        @Override
        public void run() {
            // 已经找到了解答或已经遍历了这个位置
            if (solution.isSet() || seen.putIfAbsent(pos, true) != null) {
                return;
            }
            if (puzzle.isGoal(pos)) {
                solution.setValue(this);
            } else {
                for (M move : puzzle.legalMoves(pos)) {
                    service.execute(newTask(puzzle.move(pos, move), move, this));
                }
            }
        }
    }
}
