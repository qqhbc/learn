package com.yc.juc;

import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

class Person {
    private String name;
    private Integer age;

    public Person() {
    }

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

public class BlockingQueue {
    private static final ArrayBlockingQueue<Person> queue = new ArrayBlockingQueue<>(100);

    public static ArrayBlockingQueue<Person> getQueue() {
        return queue;
    }

    static class Provide implements Runnable {

        @Override
        public void run() {
            try {
                int i = 10;
                while (true) {
                    if (queue.size() == 30) {
                        System.out.println("生产的数据太多啦");
                        Thread.currentThread().interrupt();
                    }
                    queue.put(new Person(UUID.randomUUID().toString(), i++));
                    TimeUnit.SECONDS.sleep(1);
                }
            } catch (InterruptedException e) {
                // 抛出异常会调用 Thread.interrupted(),清空中断信息
                System.out.println("生产者取消线程啦！！" + Thread.currentThread().isInterrupted() + "    " + queue.size());
            }
        }
    }

    static class Customer implements Runnable {
        @Override
        public void run() {
            Person take;
            try {
                while (true) {
                    if (queue.size() >= 20) {
                        break;
                    }
                    System.out.println("工作队列中消息个数 " + queue.size());
                    take = queue.take();
                    TimeUnit.MILLISECONDS.sleep(600);
                    System.out.println("消费者进行消费： " + take);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Provide provide = new Provide();
        Customer customer = new Customer();
        Thread thread = new Thread(provide);
        Thread thread2 = new Thread(provide);
        Thread thread1 = new Thread(customer);
        thread.start();
        thread1.start();
        thread2.start();
    }
}
