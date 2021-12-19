package com.yc.juc.chapter15;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 通过Cas实现同步的栈
 */
public class ConcurrentStack<E> {
    private final AtomicReference<Node<E>> atomicReference = new AtomicReference<>();

    public void push(E item) {
        Node<E> newNode = new Node<>(item);
        Node<E> oldNode;
        do {
            oldNode = atomicReference.get();
            newNode.next = oldNode;
        } while (!atomicReference.compareAndSet(oldNode, newNode));
    }

    public E pop() {
        Node<E> newNode;
        Node<E> oldNode;
        do {
            oldNode = atomicReference.get();
            if (Objects.isNull(oldNode)) {
                return null;
            }
            newNode = oldNode.next;
        } while (!atomicReference.compareAndSet(oldNode, newNode));
        return oldNode.item;
    }

    private static class Node<E> {
        private final E item;
        private Node<E> next;

        public Node(E item) {
            this.item = item;
        }
    }

    public static void main(String[] args) {
        ConcurrentStack<String> concurrentStack = new ConcurrentStack<>();
        concurrentStack.push("hty");
        concurrentStack.push("ssl");
        concurrentStack.push("wpx");
        System.out.println(concurrentStack.pop());
        System.out.println(concurrentStack.pop());
        System.out.println(concurrentStack.pop());
        System.out.println(concurrentStack.pop());
    }
}
