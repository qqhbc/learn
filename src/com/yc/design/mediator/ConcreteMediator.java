package com.yc.design.mediator;

import lombok.Data;

@Data
abstract class Mediator {
    protected ConcreteColleague1 concreteColleague1;
    protected  ConcreteColleague2 concreteColleague2;

    public Mediator() {
        concreteColleague1 = new ConcreteColleague1(this);
        concreteColleague2 = new ConcreteColleague2(this);
    }

    abstract void doSomething1();
    abstract void doSomething2();
}

abstract class Colleague {
    protected final Mediator mediator;

    public Colleague(Mediator mediator) {
        this.mediator = mediator;
    }
}

class ConcreteColleague1 extends Colleague {

    public ConcreteColleague1(Mediator mediator) {
        super(mediator);
    }

    public void selfMethod1() {
        System.out.println("selfMethod1");
    }

    public void dependencyMethod1() {
        super.mediator.doSomething1();
    }
}

class ConcreteColleague2 extends Colleague {

    public ConcreteColleague2(Mediator mediator) {
        super(mediator);
    }

    public void selfMethod2() {
        System.out.println("selfMethod2");
    }

    public void dependencyMethod2() {
        super.mediator.doSomething2();
    }
}

public class ConcreteMediator extends Mediator {
    @Override
    void doSomething1() {
        super.concreteColleague1.selfMethod1();
        super.concreteColleague2.selfMethod2();
    }

    @Override
    void doSomething2() {
        super.concreteColleague2.selfMethod2();
        super.concreteColleague1.selfMethod1();
    }
}

class ConcreteMediatorTest {
    public static void main(String[] args) {
        Mediator mediator = new ConcreteMediator();
        ConcreteColleague1 concreteColleague1 = new ConcreteColleague1(mediator);
        concreteColleague1.dependencyMethod1();
        System.out.println("==================================================");
        ConcreteColleague2 concreteColleague2 = new ConcreteColleague2(mediator);
        concreteColleague2.dependencyMethod2();
    }
}
