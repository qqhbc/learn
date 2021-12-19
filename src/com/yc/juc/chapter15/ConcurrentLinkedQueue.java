package com.yc.juc.chapter15;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 通过cas实现链表队列</br>
 * 头节点，尾节点<strong>存在两个操作——将添加节点放入最后节点的next,将尾节点指向添加节点</strong>
 *
 * @param <E>
 */
public class ConcurrentLinkedQueue<E> {
    private final Node<E> dummy = new Node<>(null, null);
    private final AtomicReference<Node<E>> head = new AtomicReference<>(dummy);
    private final AtomicReference<Node<E>> tail = new AtomicReference<>(dummy);

    public boolean put(E item) {
        Node<E> newNode = new Node<>(item, null);
        while (true) {
            Node<E> curNode = tail.get();
            Node<E> nextNode = curNode.next.get();
            if (curNode == tail.get()) {
                if (Objects.nonNull(nextNode)) {
                    // 不稳定
                    tail.compareAndSet(curNode, nextNode);
                } else {
                    // 稳定
                    if (curNode.next.compareAndSet(null, newNode)) {
                        // 将尾节点指向新加入Node
                        tail.compareAndSet(curNode, newNode);
                        return true;
                    }
                }
            }
        }
    }

    private static class Node<E> {
        private final E item;
        private final AtomicReference<Node<E>> next;
        // private volatile Node<E> next;

        public Node(E item, AtomicReference<Node<E>> atomicReference) {
            this.item = item;
            this.next = atomicReference;
        }
    }

    private static final AtomicIntegerFieldUpdater<Node> nextUpdater = AtomicIntegerFieldUpdater.newUpdater(Node.class, "next");
}

