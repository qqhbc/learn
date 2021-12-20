package com.yc.design.factory_method;

interface Product {
    void doSomething();
}

class ConcreteProduct implements Product {

    @Override
    public void doSomething() {
        System.out.println("ConcreteProduct");
    }
}

class ConcreteProduct1 implements Product {

    @Override
    public void doSomething() {
        System.out.println("ConcreteProduct1");
    }
}

interface Creator {
   Product getProduct(Class<? extends Product> clazz);
}

public class ConcreteCreator implements Creator {

    @Override
    public Product getProduct(Class<? extends Product> clazz) {
        Product o = null;
        try {
            o = (Product) Class.forName(clazz.getName()).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return o;
    }

}

class ConcreteCreatorTest {
    public static void main(String[] args) {
        Creator productConcreteCreator = new ConcreteCreator();
        Product product = productConcreteCreator.getProduct(ConcreteProduct.class);
        product.doSomething();
        System.out.println("=============================");
        Product product1 = productConcreteCreator.getProduct(ConcreteProduct1.class);
        product1.doSomething();
    }
}
