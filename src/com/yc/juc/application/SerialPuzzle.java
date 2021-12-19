package com.yc.juc.application;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SerialPuzzle<P, M> {
    private final Puzzle<P, M> puzzle;
    private final Set<P> seen = new HashSet<>();

    public SerialPuzzle(Puzzle<P, M> puzzle) {
        this.puzzle = puzzle;
    }

    public List<M> solver() {
        P init = puzzle.init();
        return search(new Node<>(init, null, null));
    }

    /**
     * 深度优先搜索
     *
     * @param node 链表
     * @return 所有移动位置
     */
    private List<M> search(Node<P, M> node) {
        if (!seen.contains(node.pos)) {
            seen.add(node.pos);
            if (puzzle.isGoal(node.pos)) {
                return node.asMoveList();
            }
            for (M move : puzzle.legalMoves(node.pos)) {
                P pos = puzzle.move(node.pos, move);
                Node<P, M> child = new Node<>(pos, move, node);
                List<M> search = search(child);
                if (search != null) {
                    return search;
                }
            }
        }
        return null;
    }
}
