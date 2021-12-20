package com.yc.design.factory_method;

public class SimpleConcreteCreator {
    public static Product getProduct(Class<? extends Product> clazz) {
        Product product = null;
        try {
            product = (Product) Class.forName(clazz.getName()).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return product;
    }
}

class SimpleConcreteCreatorTest {
    public static void main(String[] args) {
        Product product = SimpleConcreteCreator.getProduct(ConcreteProduct.class);
        product.doSomething();
        System.out.println("===========================");
        Product product1 = SimpleConcreteCreator.getProduct(ConcreteProduct1.class);
        product1.doSomething();
    }
}

