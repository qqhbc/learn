package com.yc.arithmetic;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

public class SortTest extends Sort{
    @Test
    public void bubbleSortTest(){
        int[] arrays = getArrays(10);
        bubbleSort(arrays);
        print(arrays);
    }

    @Test
    public void selectSortTest(){
        int[] arrays = getArrays(20);
        selectSort(arrays);
        print(arrays);
    }

    @Test
    public void insertSortTest(){
        int[] arrays = getArrays(20);
        insertSort(arrays);
        print(arrays);
    }

    @Test
    public void shellSortTest(){
        int[] arrays = getArrays(20);
        shellSort(arrays);
        print(arrays);
    }

    @Test
    public void quickSortTest(){
        int[] arrays = getArrays(20);
        quickSort(arrays);
        print(arrays);
    }

    private int[] getArrays(int len){
        Random random = new Random();
        int[] nums = new int[len];
        for (int i = 0; i < len; i++) {
            nums[i] = random.nextInt(100);
        }
        print(nums);
        System.out.println();
        return nums;
    }

    private void print(int[] nums){
        Arrays.stream(nums).forEach(out -> System.out.print(out + "、"));
    }
}
