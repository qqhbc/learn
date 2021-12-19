package com.yc.design;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;

/**
 * 原来只能播放mp3，通过适配是其支持播放mp4,主动关闭的能力
 */
interface MediaPlayer {
    void play(String autoType);
}

interface AdvanceMediaPlayer {
    void playMp4();

    void shutdown();
}

class AutoPlayer implements MediaPlayer {

    AdapterPlayer adapterPlayer;

    @Override
    public void play(String autoType) {
        if ("mp4".equalsIgnoreCase(autoType)) {
            adapterPlayer = new AdapterPlayer();
            adapterPlayer.play(autoType);
        } else if ("mp3".equalsIgnoreCase(autoType)) {
            System.out.println("播放mp3。。。");
        }
    }
}

class AdapterPlayer implements MediaPlayer {

    AdvanceMediaPlayer advanceMediaPlayer;

    @Override
    public void play(String autoType) {
        if ("mp4".equalsIgnoreCase(autoType)) {
            advanceMediaPlayer = new Mp4Player();
            advanceMediaPlayer.playMp4();
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            advanceMediaPlayer.shutdown();
        }
    }
}

class Mp4Player implements AdvanceMediaPlayer {

    @Override
    public void playMp4() {
        System.out.println("播放mp4.....");
    }

    @Override
    public void shutdown() {
        System.out.println("主动停止啦");
    }
}

public class AdapterDemo {
    public static String fun(int num) {
        char[] chars = "ABCDEFGHIGKLMNOPQRSTUVWXYZ".toCharArray();
        int length = chars.length;
        int d = num / length;
        int mod = num % length;
        return d == 0 ? String.valueOf(chars[mod]) : fun(d) + chars[mod];
    }

    public static void main(String[] args) throws InterruptedException {
//        MediaPlayer mediaPlayer = new AutoPlayer();
//        mediaPlayer.play("mp3");
//        mediaPlayer.play("mp4");

        double sum = Stream.of(new BigDecimal("2.2"), new BigDecimal("4.3"), new BigDecimal("5.4"), null).filter(Objects::nonNull).mapToDouble(BigDecimal::doubleValue).sum();
        BigDecimal reduce = Stream.of(new BigDecimal("2.2"), new BigDecimal("4.3"), new BigDecimal("5.4"), null).reduce(BigDecimal.ZERO, (a, b) -> {
            if (Objects.isNull(a)) {
                a = BigDecimal.ZERO;
            }
            if (Objects.isNull(b)) {
                b = BigDecimal.ZERO;
            }
            return a.add(b);
        });

        for (int i = 0; i < 1000000; i++) {
            if (Objects.equals("LOVE", fun(i))) {
                System.out.println(i);
                break;
            }
        }
        System.out.println(sum);
        System.out.println(reduce);

        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        Semaphore semaphore = new Semaphore(5);
        semaphore.acquire();

    }
}
