package com.yc.design.builder;

/**
 * 建造者模式</br>
 * <strong>侧重点在于零件的装配、顺序；而工厂方法模式侧重点在于创建</strong>
 */
class Product {
    private String name;
    public void doSomething() {
        System.out.println(name + " doSomething");
    }

    public void setName(String name) {
        this.name = name;
    }
}
abstract class Builder {
    abstract void buildPart(String name);
    abstract Product builderProduct();
}

public class ConcreteBuilder extends Builder {
    private final Product product = new Product();

    @Override
    void buildPart(String name) {
        this.product.setName(name);
    }

    @Override
    Product builderProduct() {
        return this.product;
    }
}

class Director {
    private final Builder builder = new ConcreteBuilder();

    public Product getProduct() {
        builder.buildPart("concrete");

        return builder.builderProduct();
    }
}

class ConcreteBuilderTest {
    public static void main(String[] args) {
        Director director = new Director();
        director.getProduct().doSomething();
    }
}
