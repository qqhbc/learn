package com.yc.design.factory_method;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

enum ProductEnum {
    CONCRETE_PRODUCT("concreteProduct", new ConcreteProduct()),
    CONCRETE_PRODUCT_1("concreteProduct1", new ConcreteProduct1()),
    ;
    private final String type;
    private final Product instance;
    ProductEnum(String type, Product instance) {
        this.type = type;
        this.instance = instance;
    }

    public String getType() {
        return type;
    }

    public Product getInstance() {
        return instance;
    }
}
public class CacheConcreteCreator {
    private static final Map<String, Product> cacheMap = new ConcurrentHashMap<>();

    /**
     * 根据type获取对应产品
     * @param type 一般为枚举
     * @return Product
     */
    public static Product getProduct(String type) {
        Product product;
        if (cacheMap.containsKey(type)) {
            product = cacheMap.get(type);
        } else {
            if (Objects.equals(ProductEnum.CONCRETE_PRODUCT.getType(), type)) {
                product = ProductEnum.CONCRETE_PRODUCT.getInstance();
            } else {
                product = ProductEnum.CONCRETE_PRODUCT_1.getInstance();
            }
        }
        cacheMap.put(type, product);

        return product;
    }
}

class CacheConcreteCreatorTest {
    public static void main(String[] args) {
        Product product = CacheConcreteCreator.getProduct(ProductEnum.CONCRETE_PRODUCT.getType());
        product.doSomething();
        System.out.println(product);
        System.out.println("================================");

        Product product1 = CacheConcreteCreator.getProduct(ProductEnum.CONCRETE_PRODUCT_1.getType());
        product1.doSomething();
        System.out.println(product1);
        System.out.println("==================================");

        System.out.println(CacheConcreteCreator.getProduct(ProductEnum.CONCRETE_PRODUCT.getType()));
        System.out.println(CacheConcreteCreator.getProduct(ProductEnum.CONCRETE_PRODUCT_1.getType()));
    }
}
