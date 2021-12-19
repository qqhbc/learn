package com.yc.juc.application;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * 位置类
 */
class P {
}

/**
 * 移动类
 */
class M {
}

/**
 * 链表节点
 *
 * @param <P>
 * @param <M>
 */
class Node<P, M> {
    P pos;
    M move;
    Node<P, M> prev;

    Node(P pos, M move, Node<P, M> prev) {
        this.pos = pos;
        this.move = move;
        this.prev = prev;
    }

    List<M> asMoveList() {
        List<M> solution = new LinkedList<>();
        for (Node<P, M> node = this; node.move != null; node = node.prev) {
            solution.add(0, node.move);
        }
        return solution;
    }
}

/**
 * 推箱子抽象类
 *
 * @param <P>
 * @param <M>
 */
public interface Puzzle<P, M> {
    /**
     * 初始位置
     */
    P init();

    /**
     * 是否是有效移动的规则集合
     */
    boolean isGoal(P pos);

    /**
     * 所有移动的有效集合
     */
    Set<M> legalMoves(P pos);

    /**
     * 每次移动的结果位置
     */
    P move(P pos, M move);
}
