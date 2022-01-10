package com.yc.design.command;

class InvokerExtend {
    private CommandExtend commandExtend;

    public void setCommandExtend(CommandExtend commandExtend) {
        this.commandExtend = commandExtend;
    }

    public void action() {
        commandExtend.execute();
    }
}

abstract class CommandExtend {
    protected final Receiver receiver;

    protected CommandExtend(Receiver receiver) {
        this.receiver = receiver;
    }

    abstract void execute();
}

public class ConcreteCommandExtend extends CommandExtend {

    public ConcreteCommandExtend(Receiver receiver) {
        super(receiver);
    }

    @Override
    void execute() {
        System.out.println("ConcreteCommandExtend 执行。。。");
        super.receiver.doSomething();
    }
}

class ConcreteCommandExtend1 extends CommandExtend {

    protected ConcreteCommandExtend1(Receiver receiver) {
        super(receiver);
    }

    @Override
    void execute() {
        System.out.println("ConcreteCommandExtend1 执行。。。");
        super.receiver.doSomething();
    }
}

class ClientExtend {
    public static void main(String[] args) {
        InvokerExtend invokerExtend = new InvokerExtend();

        Receiver receiver = new ConcreteReceiver1();
        CommandExtend commandExtend = new ConcreteCommandExtend(receiver);

        invokerExtend.setCommandExtend(commandExtend);
        invokerExtend.action();
        System.out.println("====================================");

        Receiver receiver1 = new ConcreteReceiver2();
        CommandExtend commandExtend1 = new ConcreteCommandExtend1(receiver1);

        invokerExtend.setCommandExtend(commandExtend1);
        invokerExtend.action();

    }
}
