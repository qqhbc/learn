package com.yc.arithmetic;

class Node {
    Node head;
    int val;
    Node next;
    Node(int val) {
        this.val = val;
    }

    public void addNode(int val) {
        if (head == null) {
            head = new Node(val);
            return;
        }
        Node node = head;
        while (node.next != null) {
            node = node.next;
        }
        node.next = new Node(val);
    }

    public Node reverseNode(Node node) {
        Node oldNode = node;
        Node newNode = null;
        while (oldNode != null) {
            Node next = oldNode.next;
            oldNode.next = newNode;
            newNode = oldNode;
            oldNode = next;
        }
        return newNode;
    }
}
public class NodeLinked {
    public static void main(String[] args) {
        Node node = new Node(-1);
        for (int i = 0; i < 5; i++) {
            node.addNode(i);
        }
        Node head = node.head;
        Node node1 = node.reverseNode(head);

        System.out.println();
    }
}
