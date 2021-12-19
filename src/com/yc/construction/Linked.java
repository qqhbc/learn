package com.yc.construction;

import java.util.Objects;

/**
 * 简单模拟链表的增、删、查
 */
public class Linked<T> implements Collection<T> {
    private final Node<T> node = new Node<>(null);
    private int count;

    @Override
    public void add(T t) {
        Node<T> newNode = new Node<>(t);
        Node<T> oldNode = node;
        while (oldNode.next != null) {
            oldNode = oldNode.next;
        }
        oldNode.next = newNode;
        count++;
    }

    /**
     * index如果小于0，都插入到头部
     *
     * @param t     插入元素
     * @param index 索引
     */
    @Override
    public void add(int index, T t) {
        if (index + 1 >= count) {
            add(t);
            return;
        }
        Node<T> newNode = new Node<>(t);
        Node<T> oldNode = node.next;
        Node<T> prev = node;
        while (oldNode != null) {
            if (index-- <= 0) {
                prev.next = newNode;
                newNode.next = oldNode;
                break;
            }
            prev = oldNode;
            oldNode = oldNode.next;
        }
        count++;
    }

    /**
     * 删除第一个符合即可
     *
     * @param t 要删除的元素
     */
    @Override
    public boolean remove(T t) {
        if (Objects.isNull(t)) {
            return false;
        }
        Node<T> node = this.node.next;
        Node<T> pred = this.node;
        while (node != null) {
            if (Objects.equals(t, node.val)) {
                pred.next = node.next;
                node.next = null;
                return true;
            }
            pred = node;
            node = node.next;
        }
        return false;
    }

    @Override
    public T remove(int index) {
        if (index < 0) return null;
        Node<T> oldNode = node.next;
        Node<T> prev = node;
        while (oldNode != null) {
            if (index-- == 0) {
                prev.next = oldNode.next;
                oldNode.next = null;
                return oldNode.val;
            }
            prev = oldNode;
            oldNode = oldNode.next;
        }
        return null;
    }

    @Override
    public T find(int index) {
        if (index < 0) {
            return null;
        }
        Node<T> oldNode = node.next;
        while (oldNode != null) {
            if (index-- == 0) {
                return oldNode.val;
            }
            oldNode = oldNode.next;
        }
        return null;
    }

    @Override
    public int findFirst(T t) {
        if (Objects.isNull(t)) return -1;
        int index = 0;
        Node<T> oldNode = node.next;
        while (oldNode != null) {
            if (Objects.equals(t, oldNode.val)) {
                return index;
            }
            index++;
            oldNode = oldNode.next;
        }
        return -1;
    }

    @Override
    public String print() {
        Node<T> node = this.node.next;
        StringBuilder sb = new StringBuilder();
        while (node != null) {
            sb.append(node.val).append("、");
            node = node.next;
        }
        return sb.substring(0, sb.length() - 1);
    }

    private static class Node<T> {
        T val;
        Node<T> next;

        Node(T val) {
            this.val = val;
        }
    }

    public Node<T> add(Node<T> node){
        Node<T> newNode = this.node;
        while (newNode.next != null){
            newNode = newNode.next;
        }
        newNode.next = node;
        return newNode;
    }

    public Node<T> reversal(Node<T> node){
        Node<T> prev = null;
        while (node != null){
            Node<T> next = node.next;
            node.next = prev;
            prev = node;
            node = next;
        }
        return prev;
    }



    public static void main(String[] args) {
        Linked<Integer> linked = new Linked<>();
        linked.add(new Node<>(1));
        linked.add(new Node<>(2));
        linked.add(new Node<>(3));
        linked.add(new Node<>(4));
        Node<Integer> node = linked.node;
        linked.reversal(node);
        System.out.println();
    }
}
