package com.yc.arithmetic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LRUCache {
    private final int capacity;
    private final Map<Integer, Node<Integer, Integer>> map;
    private final DoubleLinkedList<Integer, Integer> doubleLinkedList;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>(16);
        doubleLinkedList = new DoubleLinkedList<>();
    }

    public int get(int key) {
        if(!map.containsKey(key)){
            return -1;
        }
        Node<Integer, Integer> node = map.get(key);
        doubleLinkedList.removeNode(node);
        doubleLinkedList.addHead(node);
        return node.value;
    }

    public void put(int key, int value) {
        if(map.containsKey(key)){
            Node<Integer, Integer> node = map.get(key);
            node.value = value;
            doubleLinkedList.removeNode(node);
            doubleLinkedList.addHead(node);
        }else{
            Node<Integer, Integer> newNode = new Node<>(key, value);
            map.put(key, newNode);
            doubleLinkedList.addHead(newNode);
            // 超过最大容量，需要删除最后节点
            if(map.size() > capacity){
                Node<Integer, Integer> node = doubleLinkedList.getLast();
                doubleLinkedList.removeNode(node);
                map.remove(node.key);
            }

        }
    }

    static class Node<K,V>{
        K key;
        V value;
        Node<K,V> prev;
        Node<K,V> next;
        public Node(){}

        public Node(K key, V value){
            this.key = key;
            this.value = value;
        }
    }

    static class DoubleLinkedList<K,V>{
        Node<K,V> head;
        Node<K,V> tail;

        public DoubleLinkedList(){
            head = new Node<>();
            tail = new Node<>();
            head.next = tail;
            tail.prev = head;
        }

        public void addHead(Node<K,V> node){
            node.next = head.next;
            node.prev = head;
            head.next.prev = node;
            head.next = node;
        }

        public void removeNode(Node<K, V> node){
            node.next.prev = node.prev;
            node.prev.next = node.next;
            node.prev = null;
            node.next = null;
        }

        public Node<K,V> getLast(){
            return tail.prev;
        }
    }

    public List<Integer> findDisappearedNumbers(int[] nums) {
        int n = nums.length;
        List<Integer> list = new ArrayList<>();
        for(int i=0;i<n;i++){
            nums[nums[i]-1] = nums[i]+n;
        }
        for(int i=0;i<n;i++){
            if(nums[i] < n){
                list.add(i);
            }
        }
        return list;
    }

    public static void main(String[] args) {
        LRUCache main = new LRUCache(5);
        List<Integer> disappearedNumbers = main.findDisappearedNumbers(new int[]{4, 3, 2, 7, 8, 2, 3, 1});
        System.out.println();
    }
}

