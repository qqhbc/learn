package com.yc.design.command;

abstract class Command {
    abstract void execute();
}

class Invoker {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void action() {
        this.command.execute();
    }
}

abstract class Receiver {
    abstract void doSomething();
}

class ConcreteReceiver1 extends Receiver {

    @Override
    void doSomething() {
        System.out.println("ConcreteReceiver1 doSomething");
    }
}

class ConcreteReceiver2 extends Receiver {

    @Override
    void doSomething() {
        System.out.println("ConcreteReceiver2 doSomething");
    }
}

class ConcreteCommand1 extends Command {
    private final Receiver receiver;

    public ConcreteCommand1(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    void execute() {
        System.out.println("ConcreteCommand1 执行。。。");
        receiver.doSomething();
    }
}

public class ConcreteCommand extends Command {
    private final Receiver receiver;

    public ConcreteCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    void execute() {
        System.out.println("ConcreteCommand 执行。。。");
        receiver.doSomething();
    }
}

class Client {
    public static void main(String[] args) {
        Invoker invoker = new Invoker();

        Receiver receiver = new ConcreteReceiver1();
        Command command = new ConcreteCommand(receiver);

        invoker.setCommand(command);
        invoker.action();
        System.out.println("====================================");

        Receiver receiver1 = new ConcreteReceiver2();
        Command command1 = new ConcreteCommand1(receiver1);

        invoker.setCommand(command1);
        invoker.action();

    }
}
