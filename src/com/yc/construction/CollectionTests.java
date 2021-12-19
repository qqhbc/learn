package com.yc.construction;

import org.junit.Test;

public class CollectionTests {
    @Test
    public void BSTTest(){
        BST<String, Integer> bst = new BST<>();
        bst.put("S", 1);
        bst.put("X", 2);
        bst.put("E", 3);
        bst.put("A", 4);
        bst.put("R", 5);
        bst.put("C", 6);
        bst.put("H", 7);
        bst.put("M", 8);
        bst.deleteMin();
        System.out.println();
    }
}
