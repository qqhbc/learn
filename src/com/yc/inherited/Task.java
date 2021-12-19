package com.yc.inherited;

class SuperTask {
    protected SuperTask newTask() {
        System.out.println("1 super");
        return new SuperTask();
    }

    public void print() {
        System.out.println("super");
    }

    public void run() {
        System.out.println("2 super");
        newTask().print();
    }
}

public class Task extends SuperTask {
    @Override
    protected SuperTask newTask() {
        System.out.println("1 sub");
        return new Task();
    }

    public void print() {
        System.out.println("sub");
    }

    public void run() {
        super.run();
    }

    public static void main(String[] args) {
        Task task = new Task();
        task.run();
    }
}
