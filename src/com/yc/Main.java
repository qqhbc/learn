package com.yc;

import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 构造函数上面有限执行(实例变量，实例块) 按顺序执行即count赋值 2 1 3
 */
interface A {
    void aa();

    static void test() {
        System.out.println("hello world");
    }
//    default void hello(){
//        System.out.println("xxxx a");
//    }
}

interface B {
    void aa();

    static void test() {
        System.out.println("hello B");
    }
//   default void hello(){
//       System.out.println("xxxx b");
//   }
}

public class Main implements B, A {

    private static int test = 1;

    public void lockSupport() throws Exception {
        Thread a = new Thread(() -> {

            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("执行 " + Thread.currentThread().getName());
            LockSupport.park();
            // 支持提前获得许可，至多只能获取一个
            System.out.println("提前执行啦");
            LockSupport.park();
            System.out.println("唤醒 " + Thread.currentThread().getName());
        }, "a");
        a.start();
        Thread b = new Thread(() -> {
            System.out.println("开始 " + Thread.currentThread().getName());
            LockSupport.unpark(a);
            LockSupport.unpark(a);
        }, "b");
        b.start();

        Semaphore semaphore = new Semaphore(5);
        semaphore.acquire(5);


    }

    public static String reverse(String s) {
        Set<Character> set = new HashSet<>(Arrays.asList('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U'));
        int i = 0;
        int j = s.length() - 1;
        char[] c = new char[s.length()];
        while (i < j) {
            char ci = s.charAt(i);
            char cj = s.charAt(j);
            if (!set.contains(ci)) {
                c[i++] = ci;
            } else if (!set.contains(cj)) {
                c[j--] = cj;
            } else {
                c[i++] = cj;
                c[j--] = ci;
            }
        }
        return new String(c);
    }

    public static Map<String, Integer> getMap(int size) {
        Map<String, Integer> map = new HashMap<>(size * 4 / 3 + 1);
        Random random = new Random(47);
        for (int i = 0; i < size; i++) {
            map.put("000" + i, random.nextInt(size * 5));
        }
        return map;
    }

    public static void mergeSort(int[] nums, int start, int end, int[] temp) {
        if (start >= end) return;
        int mid = (end + start) >> 1;
        mergeSort(nums, start, mid, temp);
        mergeSort(nums, mid + 1, end, temp);
        arraySort(nums, start, mid, end, temp);
    }

    private static void arraySort(int[] nums, int start, int mid, int end, int[] temp) {
        int n = start;
        int m = mid + 1;
        int index = 0;
        while (n <= mid && m <= end) {
            if (nums[n] < nums[m]) {
                temp[index++] = nums[n++];
            } else {
                temp[index++] = nums[m++];
            }
        }
        while (n <= mid) {
            temp[index++] = nums[n++];
        }
        while (m <= end) {
            temp[index++] = nums[m++];
        }
    }

    // 基数排序需要借助一系列数组配合
    public static void radixSort(int[] nums, int[] counts, int k) {
        int n = nums.length;
        int[] temp = new int[n];
        int r = counts.length;
        //rtok 那个位数，例如百分位，十分位, r
        for (int i = 0, rtok = 1; i < k; i++, rtok = r * rtok) {
            for (int j = 0; j < r; j++) {
                counts[j] = 0;
            }
            // 计算元素放置在counts[]位置的个数
            for (int j = 0; j < n; j++) {
                counts[nums[j] / rtok % r]++;
            }
            //if(counts[0] == n) break; // 证明已经排好序
            // 存在位数的数组统计数量的规则是取前统计的总和
            for (int j = 1; j < r; j++) {
                counts[j] += counts[j - 1];
            }
            for (int j = n - 1; j >= 0; j--) {
                counts[nums[j] / rtok % r]--;
                temp[counts[nums[j] / rtok % r]] = nums[j];
            }
            for (int j = 0; j < n; j++) {
                nums[j] = temp[j];
            }
        }
    }

    // 基数排序需要借助一系列数组配合
    public static void radixSortA(int[] nums, int[] counts, int k) {
        int n = nums.length;
        int[] temp = new int[n];
        int r = counts.length;
        //rtok 那个位数，例如百分位，十分位, r
        for (int i = 0, rtok = 1; i < k; i++, rtok = r * rtok) {
            for (int j = 0; j < r; j++) {
                counts[j] = 0;
            }
            // 计算元素放置在counts[]位置的个数
            for (int j = 0; j < n; j++) {
                counts[-nums[j] / rtok % r]++;
            }
            if (counts[0] == n) break; // 证明已经排好序
            // 存在位数的数组统计数量的规则是取前统计的总和
            for (int j = 1; j < r; j++) {
                counts[j] += counts[j - 1];
            }
            for (int j = n - 1; j >= 0; j--) {
                counts[-nums[j] / rtok % r]--;
                temp[counts[-nums[j] / rtok % r]] = nums[j];
            }
            for (int j = 0; j < n; j++) {
                nums[j] = temp[j];
            }
        }
    }

    public static void quickSort(int[] nums, int start, int end) {
        if (start >= end) return;
        int index = partition(nums, start, end);
        quickSort(nums, start, index - 1);
        quickSort(nums, index + 1, end);
    }

    // 3 4 6 8 9
    private static int partition(int[] nums, int start, int end) {
        int i = start;
        int j = end + 1;
        while (true) {
            do {
                i++;
            }while (i < end && nums[i] < nums[start]) ;
            do{
                j--;
            }while (j > start && nums[j] > nums[start]) ;
            if (i >= j) break;
            swap(nums, i, j);
        }
        swap(nums, start, j);
        return j;
    }

    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    @Override
    public void aa() {

    }

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }

    public ListNode FindKthToTail(ListNode head, int k) {
        Map<Integer, ListNode> map = new HashMap<>(16);
        int i = 0;
        while (head.next != null) {
            map.put(i++, new ListNode(head.val));
            head = head.next;
        }
        map.put(i++, new ListNode(head.val));
        return map.get(map.size() - k);
    }

    public void add(ListNode node, int val) {
        ListNode listNode = node;
        while (listNode.next != null) {
            listNode = listNode.next;
        }
        listNode.next = new ListNode(val);
    }

    public synchronized void lock() {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        Thread thread = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.lock();
            try {
                System.out.println("获取锁");
                condition.await();
                System.out.println("被唤醒");
                condition.await();
                System.out.println("再次被唤醒");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "A");
        thread.start();
        new Thread(() -> {
            System.out.println("线程B限执行");
            lock.lock();
            try {
                System.out.println("进行唤醒操作");
                // 提前唤醒没屁用，但是LockSupport是可以的
                condition.signalAll();
                condition.signalAll();
                System.out.println("唤醒结束啦");
            } finally {
                lock.unlock();
            }

        }, "B").start();
    }

    public double[] medianSlidingWindow(int[] nums, int k) {
        if (nums.length == 0) return null;
        //维护两个堆，一个大顶堆和一个小顶堆，确保小顶堆的堆顶大于大顶堆的堆顶
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
        double[] middle = new double[nums.length - k + 1];
        int index = 0;
        for (int i = 0; i < nums.length - k + 1; i++) {
            while (maxHeap.size() + minHeap.size() < k && index < nums.length) {
                if (minHeap.peek() != null && minHeap.peek() > nums[index]) {
                    maxHeap.offer(nums[index]);
                } else {
                    minHeap.offer(nums[index]);
                }
                // 确保minHeap.size()>=maxHeap.size()
                if (maxHeap.size() - minHeap.size() > 0) {
                    minHeap.offer(maxHeap.poll());
                } else if (minHeap.size() - maxHeap.size() > 1) {
                    maxHeap.offer(minHeap.poll());
                }
                index++;
            }
            // min 3  max -1
            if (maxHeap.size() == minHeap.size()) {
                middle[i] = ((long) maxHeap.peek() + (long) minHeap.peek()) * 0.5;
            } else if (maxHeap.size() > minHeap.size()) {
                middle[i] = maxHeap.peek();
            } else {
                middle[i] = minHeap.peek();
            }
            if (nums[i] < minHeap.peek()) {
                maxHeap.remove(nums[i]);
            } else {
                minHeap.remove(nums[i]);
            }
            if (maxHeap.size() - minHeap.size() > 0) {
                minHeap.offer(maxHeap.poll());
            } else if (minHeap.size() - maxHeap.size() > 1) {
                maxHeap.offer(minHeap.poll());
            }
        }
        return middle;

    }

    //示例 1：
//输入：dictionary = ["cat","bat","rat"], sentence = "the cattle was rattled by the battery"
//输出："the cat was rat by the bat"
//示例 2：
//输入：dictionary = ["a","b","c"], sentence = "aadsfasf absbs bbab cadsfafs"
//输出："a a b c"
//示例 3：
//输入：dictionary = ["a", "aa", "aaa", "aaaa"], sentence = "a aa a aaaa aaa aaa aaa aaaaaa bbb baba ababa"
//输出："a a a a a a a a bbb baba a"
//示例 4：
//输入：dictionary = ["catt","cat","bat","rat"], sentence = "the cattle was rattled by the battery"
//输出："the cat was rat by the bat"
//示例 5：
//输入：dictionary = ["ac","ab"], sentence = "it is abnormal that this solution is accepted"
//输出："it is ab that this solution is ac"
    public String getString(String[] strs, String sentence) {
        if (Objects.isNull(sentence) || sentence.length() == 0) {
            return null;
        }
        String[] s = sentence.split(" ");

        for (int i = 0; i < s.length; i++) {
            int minLen = 0;
            String replace = null;
            for (String str : strs) {
                if (s[i].startsWith(str)) {
                    if (minLen == 0) {
                        minLen = str.length();
                        replace = str;
                    } else if (str.length() < minLen) {
                        minLen = str.length();
                        replace = str;
                    }
                }
            }
            if (Objects.nonNull(replace)) {
                s[i] = replace;
            }
        }
        return String.join(" ", s);
    }

    public int maxScore(int[] cardPoints, int k) {
        int maxSum = 0;
        int sum = 0;
        for (int i = 0; i < k; i++) {
            sum += cardPoints[i];
            maxSum = sum;
        }
        int len = cardPoints.length;
        int right = k - 1;
        for (int i = len - 1; i > len - k - 1; i--) {
            // 3 对应 6
            // 2 对应 5  x+k-1=i
            sum = sum + cardPoints[i] - cardPoints[right--];
            maxSum = Math.max(sum, maxSum);
        }
        return maxSum;
    }

    public int equalSubstring(String s, String t, int maxCost) {
        int[] cost = new int[s.length()];
        for (int i = 0; i < s.length(); i++) {
            cost[i] = Math.abs(s.charAt(i) - t.charAt(i));
        }
        int len = 0;
        // 0 2 1 0  1 1 0 2    3
        // 1 1  1 1  3
        // 3 6 15 11 3 9 8 15 22 10 3 7 3 4 0
        int left = 0;
        while (maxCost >= cost[left]) {
            maxCost -= cost[left++];
            len++;
        }
        int maxLen = len;
        for (int i = 1; i < cost.length - len; i++) {
            maxCost = maxCost + cost[i - 1] - cost[len + i - 1];
            while (len + i < cost.length && maxCost >= cost[len + i]) {
                maxCost -= cost[len + i];
                len++;
            }
            maxLen = Math.max(maxLen, len);
        }
        return maxLen;
    }

    public int atMost(int[] A, int k) {
        int N = A.length;
        int right = 0;
        int left = 0;
        // 1 <= A[i] <= A.length 类似于桶排序
        int[] freq = new int[N + 1];
        int res = 0;
        int count = 0;
        while (right < N) {
            freq[A[right]]++;
            if (freq[A[right]] == 0) {
                count++;
            }
            right++;
            while (count > k) {
                if (--freq[A[left++]] == 0) {
                    count--;
                }
            }
            res += right - left;
        }
        return res;
    }

    //"tyiraojpcfuttwblehv"
    //"stbtakjkampohttraky"
    //119

    public int characterReplacement(String s, int k) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int n = s.length();
        // aababbba
        char[] chars = s.toCharArray();
        // 仅由大写英文字母
        int[] freq = new int[26];
        int maxCount = 0;
        int right = 0;
        int left = 0;
        while (right < n) {
            freq[chars[right] - 'A']++;
            maxCount = Math.max(maxCount, freq[chars[right] - 'A']);
            if (maxCount + k < right - left + 1) {
                freq[chars[left++] - 'A']--;
            }
            right++;
        }
        return n - left;
    }

    static class UnionFind {
        private int count;
        private final int[] parent;

        public UnionFind(int count) {
            this.count = count;
            parent = new int[count];
            for (int i = 0; i < count; i++) {
                parent[i] = i;
            }
        }

        // 压缩路径, 找根节点一致即可以认为是一组
        public int find(int element) {
            while (element != parent[element]) {
                parent[element] = parent[parent[element]];
                element = parent[element];
            }
            return element;
        }

        // 0 1 2 3 4 5 6 7 8
        // 0 1 2 3 4 5 6 6 8   -1 7 6
        // 0 1 3 3 4 5 6 6 8   -1 2 3
        // 0 6 3 3 4 5 6 6 8   -1 1 6
        // 5 6 3 3 4 5 6 6 8   -1 0 5
        // 5 6 3 3 4 5 6 6 6   -1 8 1
        // 5 6 3 3 6 5 6 6 6   -1 4 8
        // 5 6 3 5 6 5 6 6 6   -1 3 5
        // 5 6 3 5 6 6 6 6 6   -1 0 4
        // 5 6 3 5 6 6 6 6 6      7 2
        public void union(int firstElement, int secondElement) {
            int first = find(firstElement);
            int second = find(secondElement);
            if (first == second) {
                return;
            }
            parent[first] = second;
            count--;
        }

        public int getCount() {
            return count;
        }
    }

    public int minSwapsCouples(int[] row) {
        int len = row.length;
        int n = len / 2;
        UnionFind unionFind = new UnionFind(n);
        for (int i = 0; i < len; i += 2) {
            unionFind.union(row[i] / 2, row[i + 1] / 2);
        }
        return n - unionFind.getCount();
    }

    public int findMaxConsecutiveOnes(int[] nums) {
        int n = nums.length;
        int left = 0;
        int maxLen = 0;
        for (int i = 0; i < n; i++) {
            if (nums[i] != 1) {
                // 1 1 0 1 1 1 0 1 1
                maxLen = Math.max(maxLen, i - left);
                left = i;
            }
        }
        return Math.max(maxLen, n - 1 - left);
    }

    public int arrayPairSum(int[] nums) {
        // -1 -4 -3 -2
        int[] positives = new int[10000];
        int[] negatives = new int[10000];
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= 0) {
                positives[nums[i]]++;
            } else {
                negatives[-nums[i]]++;
            }
        }
        // 对两个数组分别遍历取数，两两结合找最小求和
        int positive = getSum(positives);
        int negative = getSum(negatives);
        return positive - negative;
    }

    public int getSum(int[] nums) {
        int count = 0;
        int sum = 0;
        int temp = 0;
        for (int i = 0; i < nums.length; i++) {
            while (nums[i] > 0) {
                count++;
                nums[i]--;
                if (count % 2 == 0) {
                    sum += Math.min(temp, i);
                    temp = 0;
                } else {
                    temp = i;
                }
            }
        }
        return sum;
    }

    public int findShortestSubArray(int[] nums) {
        int n = nums.length;
        // 值为数组元素出现的个数及初始下标s和终止下标e（e-s+1为最短长度）
        Map<Integer, int[]> map = new HashMap<>(n);
        for (int i = 0; i < n; i++) {
            if (map.containsKey(nums[i])) {
                int[] temp = map.get(nums[i]);
                temp[0]++;
                temp[2] = i;
            } else {
                map.put(nums[i], new int[]{1, i, i});
            }
        }
        int maxNum = 0;
        int minLen = 0;
        for (Map.Entry<Integer, int[]> entry : map.entrySet()) {
            int[] temp = entry.getValue();
            if (maxNum < temp[0]) {
                maxNum = temp[0];
                minLen = temp[2] - temp[1] + 1;
            } else if (maxNum == temp[0]) {
                minLen = Math.min(minLen, temp[2] - temp[1] + 1);
            }
        }
        return minLen;
    }

    // 0 1 1 2 3 5 8 13 21 34 55
    public int fun(int n) {
        if (n < 2) return n;
        return fun(n - 1) + fun(n - 2);
    }

    public boolean judgeSquareSum(int c) {
        for (long i = 0; i * i <= c; i++) {
            double b = Math.sqrt(c - i * i);
            if ((int) b == b) {
                return true;
            }
        }
        return false;
    }

    public int mySqrt(int x) {
        // 二分法求平方根
        int left = 0;
        int right = x;
        int ans = -1;
        while (left <= right) {
            int mod = left + (right - left) / 2;
            long a = (long) mod * mod;
            if (a <= x) {
                left = mod + 1;
                ans = mod;
            } else {
                right = mod - 1;
            }
        }
        return ans;
    }

    private List<List<Integer>> res = new ArrayList<>();
    private int n;
    public void find(int[] nums, int begin, List<Integer> list){
        res.add(new ArrayList<>(list));
        for (int i = begin; i < n; i++) {
            list.add(nums[i]);
            find(nums, i + 1, list);
            list.remove(list.size() - 1);
        }
    }

    public void test(int[] nums){
        n = nums.length;
        if(n == 0){
            return;
        }
        List<Integer> list = new ArrayList<>();
        find(nums, 0, list);
    }

    public synchronized void testFair(){
        System.out.println(Thread.currentThread().getName() + " hold lock ");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int sum(int a, int b){
        return a+b;
    }

    public static void main(String[] args) throws InterruptedException {
        Integer i = null;
        System.out.println(3 == i);


    }
}

