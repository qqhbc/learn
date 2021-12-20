package com.yc.design.factory_method;

class ConcreteProductCreator implements MultiConcreteCreator {

    @Override
    public Product getProduct() {
        return new ConcreteProduct();
    }
}

class ConcreteProductCreator1 implements MultiConcreteCreator {

    @Override
    public Product getProduct() {
        return new ConcreteProduct1();
    }
}

public interface MultiConcreteCreator {
    Product getProduct();
}

class MultiConcreteCreatorTest {
    public static void main(String[] args) {
        Product product = new ConcreteProductCreator().getProduct();
        product.doSomething();
        System.out.println("================================");
        Product product1 = new ConcreteProductCreator1().getProduct();
        product1.doSomething();
    }
}
