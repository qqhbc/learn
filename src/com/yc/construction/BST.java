package com.yc.construction;

import java.util.Objects;

/**
 * 二叉查找树
 */
public class BST<K extends Comparable<K>, V> {
    private Node root;

    private class Node{
        private K k;
        private V v;
        Node left;
        Node right;
        // 节点及子节点所有个数
        int N;

        public Node(K k, V v, int N){
            this.k = k;
            this.v = v;
            this.N = N;
        }
    }

    public int size(){
        return size(root);
    }

    private int size(Node node){
        if(Objects.isNull(node)){
            return 0;
        }
        return node.N;
    }

    public V get(K k){
        return get(root, k);
    }

    private V get(Node node, K k){
        if(node == null) return null;
        int compare = k.compareTo(node.k);
        if(compare>0){
            return get(node.right, k);
        }else if(compare < 0){
            return get(node.left, k);
        }else {
            return node.v;
        }
    }

    public void put(K k, V v){
        root = put(root, k, v);
    }

    private Node put(Node node, K k, V v){
        if(node == null) return new Node(k, v, 1);
        int compare = k.compareTo(node.k);
        if(compare > 0){
            node.right = put(node.right, k, v);
        }else if(compare < 0){
            node.left = put(node.left, k, v);
        }else {
            node.v = v;
        }
        node.N = size(node.right) + size(node.left) + 1;
        return node;
    }


    public K min(){
        return min(root).k;
    }

    private Node min(Node node){
        if(node.left == null) return node;
        return min(node.left);
    }

    public K max(){
        return max(root).k;
    }

    private Node max(Node node){
        if(node.right == null) return node;
        return max(node.right);
    }

    public K floor(K k){
        Node node = floor(root, k);
        if(node == null) return null;
        return node.k;
    }

    private Node floor(Node node, K k){
        if(node == null) return null;
        int compare = k.compareTo(node.k);
        if(compare == 0) return node;
        // 左子树一定包含向下取整的那个结点
        if(compare < 0) return floor(node.left, k);
        // 如果查找的k在右结点，则可能在左子树也可能在右子树，如果返回为null，则证明没有找到所以取父节点，因为父节点一定小于左节点
        Node floor = floor(node.right, k);
        if(floor == null) return node;
        // 不为空证明结点已经找到
        else return floor;
    }

    public K ceiling(K k){
        Node node = ceiling(root, k);
        if(node == null) return null;
        return node.k;
    }

    private Node ceiling(Node node, K k){
        if(node == null) return null;
        int compare = k.compareTo(node.k);
        if(compare == 0) return node;
        if(compare > 0) return ceiling(node.right, k);
        Node ceiling = ceiling(node.left, k);
        if(ceiling == null) return node;
        else return ceiling;
    }

    /**
     * 查找排名为k的键
     * @param ranking 正序排名
     * @return key
     */
    public K select(int ranking){
        return select(root, ranking).k;
    }

    private Node select(Node node, int ranking){
        if(node == null) return null;
        int t = size(node.left);
        if(t == ranking) return node;
        else if(t > ranking) return select(node.left, ranking);
        // t小于ranking，向右遍历
        else return select(node.right, ranking - t - 1);
    }

    public int rank(K k){
        return rank(root, k);
    }

    private int rank(Node node, K k){
        if(node == null) return 0;
        int compare = k.compareTo(node.k);
        if(compare < 0) return rank(node.left, k);
        else if(compare > 0) return size(node.left) + 1 + rank(node.right, k);
        else return size(node.left);

    }

   public void deleteMin(){
        if (root != null) {
            root = deleteMin(root);
        }
   }

   private Node deleteMin(Node node){
        if(node.left == null) return node.right;
        node.left = deleteMin(node.left);
        node.N = size(node.left) + size(node.right) + 1;
        return node;
   }

   public void deleteMax(){
        if(root != null) {
            deleteMax(root);
        }
   }

   private Node deleteMax(Node node){
       if(node.right == null) return node.left;
       node.right = deleteMax(node);
       node.N = size(node.left) + size(node.right) + 1;
       return node;
   }

}
