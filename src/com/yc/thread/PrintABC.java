package com.yc.thread;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

class MajusculeABC {
    //由于线程实在构造方法出入元素，之后不插入因此不存在多线程问题
    private final ReentrantLock lock = new ReentrantLock();
    private final Map<Character, Semaphore> map = new HashMap<>();

    public void put(Character character){
        if(Objects.equals(character, 'A')){
            map.put(character, new Semaphore(1));
        }else {
            map.put(character, new Semaphore(0));
        }
    }

    public Semaphore get(Character character){
        return map.get(character);
    }
}
class Thread_ABC implements Runnable {

    private final MajusculeABC majusculeABC;
    private final Character character;
    private static final Map<Character, Thread_ABC> map = new ConcurrentHashMap<>();

    public Thread_ABC(MajusculeABC majusculeABC, Character character){
        this.majusculeABC = majusculeABC;
        this.character = character;
        map.put(character, this);
        majusculeABC.put(character);
    }

    public void selectCharacter(Character character) throws InterruptedException {
        if(character == 'A'){
            majusculeABC.get(character).acquire();
            System.out.println(character);
            majusculeABC.get('B').release();
        }else if(character == 'B'){
            majusculeABC.get(character).acquire();
            System.out.println(character);
            majusculeABC.get('C').release();
        }else {
            majusculeABC.get(character).acquire();
            System.out.println(character);
            majusculeABC.get('A').release();
        }
    }

    @Override
    public void run() {

    }
}
// 交替打印ABC
public class PrintABC {
    public static void main(String[] args) {
        MajusculeABC maj = new MajusculeABC();
        Thread t_a = new Thread(new Thread_ABC(maj , 'A'));
        Thread t_b = new Thread(new Thread_ABC(maj , 'B'));
        Thread t_c = new Thread(new Thread_ABC(maj , 'C'));
        t_a.start();
        t_b.start();
        t_c.start();

    }
}
