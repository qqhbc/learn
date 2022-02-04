package com.yc.design.flyweight;

import java.util.HashMap;
import java.util.Map;

abstract class Flyweight {
    private String intrinsic;
    protected final String extrinsic;

    public Flyweight(String extrinsic) {
        this.extrinsic = extrinsic;
    }

    public abstract void operate();

    public String getIntrinsic() {
        return intrinsic;
    }

    public void setIntrinsic(String intrinsic) {
        this.intrinsic = intrinsic;
    }
}

class FlyweightFactory {
    private static final Map<String, Flyweight> map = new HashMap<>();

    public static Flyweight getFlyweight(String extrinsic) {
        Flyweight result;
        if (map.containsKey(extrinsic)) {
            result = map.get(extrinsic);
        } else {
            result = new ConcreteFlyweight(extrinsic);
            map.put(extrinsic, result);
        }
        return result;
    }
}
public class ConcreteFlyweight extends Flyweight {

    public ConcreteFlyweight(String extrinsic) {
        super(extrinsic);
    }

    @Override
    public void operate() {
        System.out.println(super.getIntrinsic() + " ConcreteFlyweight operate " + super.extrinsic);
    }
}

class UnShardingConcreteFlyweight extends Flyweight{

    public UnShardingConcreteFlyweight(String extrinsic) {
        super(extrinsic);
    }

    @Override
    public void operate() {
        System.out.println(super.getIntrinsic() + " UnShardingConcreteFlyweight operate " + super.extrinsic);
    }
}

class Client {
    public static void main(String[] args) {
        Flyweight key1 = FlyweightFactory.getFlyweight("key1");
        key1.setIntrinsic("hello world");
        key1.operate();
        System.out.println("========================================");
        FlyweightFactory.getFlyweight("key1").operate();
        FlyweightFactory.getFlyweight("key2").operate();
        System.out.println("========================================");
        UnShardingConcreteFlyweight unShardingConcreteFlyweight = new UnShardingConcreteFlyweight("hi world");
        unShardingConcreteFlyweight.setIntrinsic("xxx");
        unShardingConcreteFlyweight.operate();
    }
}
