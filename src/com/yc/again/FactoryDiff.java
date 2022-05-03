package com.yc.again;

interface ISuperMan {
    void specialTalent();
}

class AdultSuperMan implements ISuperMan {

    @Override
    public void specialTalent() {
        System.out.println("成年超人。。。");
    }
}

class ChildSuperMan implements ISuperMan {

    @Override
    public void specialTalent() {
        System.out.println("儿童超人。。。");
    }
}

class ISuperManFactory {
    public static ISuperMan createSuperMan(String type) {
        if ("adult".equalsIgnoreCase(type)) {
            return new AdultSuperMan();
        }
        if ("child".equalsIgnoreCase(type)) {
            return new ChildSuperMan();
        }
        throw new IllegalArgumentException("创建失败啦");
    }
}

/**
 * 工厂是整体
 */
public class FactoryDiff {
    public static void main(String[] args) {
        ISuperMan adult = ISuperManFactory.createSuperMan("adult");
        adult.specialTalent();
        System.out.println("=========================================");
        ISuperMan child = ISuperManFactory.createSuperMan("child");
        child.specialTalent();
    }
}
