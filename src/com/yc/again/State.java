package com.yc.again;

class Context {
    public static final State concreteState = new ConcreteState();
    public static final State concreteState1 = new ConcreteState1();

    private State state;

    public State getState() {
        return state;
    }

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

class ConcreteState extends State {

    @Override
    protected void handler() {
        System.out.println("ConcreteState handler");
    }

    @Override
    protected void handler1() {
        super.context.setState(Context.concreteState1);
        super.context.handler1();
    }
}

class ConcreteState1 extends State {

    @Override
    protected void handler() {
        super.context.setState(Context.concreteState);
        super.context.handler();
    }

    @Override
    protected void handler1() {
        System.out.println("ConcreteState1 handler1");
    }
}

public abstract class State {
    protected Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    protected abstract void handler();

    protected abstract void handler1();
}

class Client {
    public static void main(String[] args) {
        Context context = new Context();
        context.setState(Context.concreteState1);

        context.handler();
        context.handler1();
    }
}
