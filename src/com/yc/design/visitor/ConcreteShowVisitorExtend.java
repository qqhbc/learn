package com.yc.design.visitor;

import lombok.Data;

import java.util.Random;

interface VisitorExtend {
    void visit(ConcreteElementExtend concreteElementExtend);

    void visit(ConcreteElementExtend1 concreteElementExtend1);
}

interface ShowVisitorExtend extends VisitorExtend {
    void report();
}

interface TotalVisitorExtend extends VisitorExtend {
    void total();
}

@Data
abstract class ElementExtend {
    private int score;

    public abstract String doSomething();

    public abstract void accept(VisitorExtend visitorExtend);
}

class ConcreteElementExtend extends ElementExtend {

    @Override
    public String doSomething() {
        return "ConcreteElementExtend \t";
    }

    @Override
    public void accept(VisitorExtend visitorExtend) {
        visitorExtend.visit(this);
    }

    @Override
    public String toString() {
        return "ConcreteElementExtend(score=" + super.getScore() + ")";
    }
}

class ConcreteElementExtend1 extends ElementExtend {

    @Override
    public String doSomething() {
        return "ConcreteElementExtend1 \t";
    }

    @Override
    public void accept(VisitorExtend visitorExtend) {
        visitorExtend.visit(this);
    }

    @Override
    public String toString() {
        return "ConcreteElementExtend1(score=" + super.getScore() + ")";
    }
}

public class ConcreteShowVisitorExtend implements ShowVisitorExtend {
    private String msg = "";

    @Override
    public void visit(ConcreteElementExtend concreteElementExtend) {
        this.msg += concreteElementExtend.doSomething();
    }

    @Override
    public void visit(ConcreteElementExtend1 concreteElementExtend1) {
        this.msg += concreteElementExtend1.doSomething();
    }

    @Override
    public void report() {
        System.out.println(this.msg);
    }
}

class ConcreteTotalVisitorExtend implements TotalVisitorExtend {
    private int totalNum;
    private static final int baseNum = 3;
    private static final int baseNum1 = 2;

    @Override
    public void visit(ConcreteElementExtend concreteElementExtend) {
        totalNum += concreteElementExtend.getScore() * baseNum;
    }

    @Override
    public void visit(ConcreteElementExtend1 concreteElementExtend1) {
        totalNum += concreteElementExtend1.getScore() * baseNum1;
    }

    @Override
    public void total() {
        System.out.println(this.totalNum);
    }
}

class ObjectStructureExtend {

    private static final Random random = new Random();

    public static ElementExtend createElement() {
        if (random.nextBoolean()) {
            ConcreteElementExtend concreteElementExtend = new ConcreteElementExtend();
            concreteElementExtend.setScore(random.nextInt(5) + 5);
            System.out.println(concreteElementExtend);
            return concreteElementExtend;
        }
        ConcreteElementExtend1 concreteElementExtend1 = new ConcreteElementExtend1();
        concreteElementExtend1.setScore(random.nextInt(5) + 15);
        System.out.println(concreteElementExtend1);
        return concreteElementExtend1;
    }
}

class ClientExtend {
    public static void main(String[] args) {
        ShowVisitorExtend showVisitor = new ConcreteShowVisitorExtend();
        TotalVisitorExtend totalVisitor = new ConcreteTotalVisitorExtend();

        for (int i = 0; i < 5; i++) {
            ElementExtend element = ObjectStructureExtend.createElement();
            element.accept(showVisitor);
            element.accept(totalVisitor);
        }

        showVisitor.report();
        totalVisitor.total();
    }
}
