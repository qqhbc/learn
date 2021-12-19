package com.yc.arithmetic;

// 关于排序的一些简单实现
public class Sort {
    /**
     * 冒泡排序
     * 双层循环外层每一次遍历都需要和内层所有元素进行比较并交换，将最大值移到右边
     *
     * @param nums 要排序的数组
     */
    public void bubbleSort(int[] nums) {
        boolean flag = false;
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = 1; j < nums.length - i; j++) {
                if (nums[j - 1] > nums[j]) {
                    flag = true;
                    swap(nums, j - 1, j);
                }
            }
            if (!flag) {
                break;
            }
        }
    }

    /**
     * 选择排序
     * 双层循环外层每一层遍历需要和内层所有元素进行比较找出最小的元素放入第i列（i是外层遍历的次数）
     *
     * @param nums 要排序的数组
     */
    public void selectSort(int[] nums) {
        for (int i = 0; i < nums.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[minIndex] > nums[j]) {
                    minIndex = j;
                }
            }
            swap(nums, i, minIndex);
        }
    }

    /**
     * 插入排序
     * 双层循环外层每一层遍历需要内层所有元素（外层长度+1）, 倒序插入每次插入时该数组已经排序，类似于扑克牌的插排
     *
     * @param nums 要排序的数组
     */
    public void insertSort(int[] nums) {
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j > 0; j--) {
                if (nums[j - 1] > nums[j]) {
                    swap(nums, j - 1, j);
                // 因为之前组数元素已经排序
                } else {
                    break;
                }
            }
        }
    }

    /**
     * 希尔排序
     * 优化插入排序，如果每次要插入的元素恰好都是最小元素，那么每次都需要进行全量交换，通过将一个数组拆分成子序列，分别进行排序可以有效减少交换次数
     * @param nums
     */
    public void shellSort(int[] nums){
        int h = 1;
        int n = nums.length;
        while (h<n/3){
            h = h*3+1;
        }
        while (h>=1) {
            for (int i = h; i < n; i++) {
                for (int j=i; j>=h;j -= h){
                    if(nums[j-h] > nums[j]){
                        swap(nums, j-h, j);
                    }else {
                        break;
                    }
                }
            }
            h = h/3;
        }
    }

    public void quickSort(int[] nums){
        quickSort(nums, 0, nums.length-1);
    }

    private void quickSort(int[] nums, int start, int end){
        if(start<end){
            int index = partition(nums, start, end);
            quickSort(nums, start, index-1);
            quickSort(nums, index+1, end);
        }
    }
    // 8 4 9 3 7 2 5
    // 8 4 5i 3 7 2 9j  8 4 9i 3 7 2 5j
    // 8 4 5 3 7 2j 9i
    private int partition(int[] nums, int start, int end){
        int k = nums[start];
        int l = start;
        int h = end+1;
        while (true){
            // 升序7
            do {
                l++;
            }while (nums[l]<k && l<end);
            do {
                h--;
            }while (nums[h]>k && h > start);
            if(l>=h){
                break;
            }
            swap(nums, l, h);
        }
        swap(nums, start, h);
        return h;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public static void main(String[] args) {
        Sort sort = new Sort();
        int[] ints = {8, 4, 9, 3, 7, 2, 5};
        sort.quickSort(ints);
        System.out.println();
    }
}
