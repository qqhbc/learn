package com.yc.design.abstract_factory;

abstract class AbstractProductA {
    abstract void doSomething();
}

abstract class AbstractProductB {
    abstract void doSomething();
}

abstract class AbstractCreator {
    abstract AbstractProductA createProductA();
    abstract AbstractProductB createProductB();
}

class ProductA1 extends AbstractProductA {

    @Override
    void doSomething() {
        System.out.println("ProductA1");
    }
}

class ProductB1 extends AbstractProductB {

    @Override
    void doSomething() {
        System.out.println("ProductB1");
    }
}

class ProductA2 extends AbstractProductA {

    @Override
    void doSomething() {
        System.out.println("ProductA2");
    }
}

class ProductB2 extends AbstractProductB {

    @Override
    void doSomething() {
        System.out.println("ProductB2");
    }
}

class ProductA3 extends AbstractProductA {

    @Override
    void doSomething() {
        System.out.println("ProductA3");
    }
}

class ProductB3 extends AbstractProductB {

    @Override
    void doSomething() {
        System.out.println("ProductB3");
    }
}

class ConcreteCreator1 extends AbstractCreator {

    @Override
    AbstractProductA createProductA() {
        return new ProductA1();
    }

    @Override
    AbstractProductB createProductB() {
        return new ProductB1();
    }
}

class ConcreteCreator2 extends AbstractCreator {

    @Override
    AbstractProductA createProductA() {
        return new ProductA2();
    }

    @Override
    AbstractProductB createProductB() {
        return new ProductB2();
    }
}

/**
 * 横向扩展容易，只需新增3个类——两个产品等级+一个对应等级工厂
 */

class ConcreteCreator3 extends AbstractCreator {

    @Override
    AbstractProductA createProductA() {
        return new ProductA3();
    }

    @Override
    AbstractProductB createProductB() {
        return new ProductB3();
    }
}

/**
 * <strong>横向扩展容易(产品等级)，纵向扩展难(产品族)</strong>
 * 产品AB对应产品族(可能存在相互依赖或相同约束)</br>
 * 工厂12对应产品等级</br>
 */
public class ConcreteCreatorLevel {
    public static void main(String[] args) {
        AbstractCreator abstractCreator = new ConcreteCreator1();
        AbstractProductA productA = abstractCreator.createProductA();
        AbstractProductB productB = abstractCreator.createProductB();
        productA.doSomething();
        productB.doSomething();
        System.out.println("===============================================");
        abstractCreator = new ConcreteCreator2();
        productA = abstractCreator.createProductA();
        productB = abstractCreator.createProductB();
        productA.doSomething();
        productB.doSomething();
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++扩展等级+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        abstractCreator = new ConcreteCreator3();
        productA = abstractCreator.createProductA();
        productB = abstractCreator.createProductB();
        productA.doSomething();
        productB.doSomething();
    }
}
