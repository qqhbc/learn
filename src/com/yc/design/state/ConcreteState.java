package com.yc.design.state;

import lombok.Getter;

abstract class State {
    protected Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public abstract void handler();

    public abstract void handler1();
}

@Getter
class Context {
    public static final ConcreteState concreteState = new ConcreteState();
    public static final ConcreteState1 concreteState1 = new ConcreteState1();

    private State state;

    public void setState(State state) {
        this.state = state;
        this.state.setContext(this);
    }

    public void handler() {
        this.state.handler();
    }

    public void handler1() {
        this.state.handler1();
    }
}

public class ConcreteState extends State {
    @Override
    public void handler() {
        System.out.println("ConcreteState handler");
    }

    @Override
    public void handler1() {
        super.context.setState(Context.concreteState1);
        super.context.getState().handler1();
    }
}

class ConcreteState1 extends State {

    @Override
    public void handler() {
        super.context.setState(Context.concreteState);
        super.context.getState().handler();
    }

    @Override
    public void handler1() {
        System.out.println("ConcreteState1 handler1");
    }
}

class Client {
    public static void main(String[] args) {
        Context context = new Context();
        context.setState(Context.concreteState);

        context.handler();
        context.handler1();
    }
}
