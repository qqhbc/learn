package com.yc.again;

import lombok.Data;

/**
 * 建造者注重细节
 * 角色: 产品 建造者 导演类
 */
@Data
class SuperMan {
    private String body;
    private String specialTalent;
    private String specialSymbol;
}

abstract class Builder {
    protected final SuperMan superMan = new SuperMan();

    protected abstract SuperMan getSuperMan();

    public void setBody(String body) {
        this.superMan.setBody(body);
    }

    public void setSpecialTalent(String specialTalent) {
        this.superMan.setSpecialTalent(specialTalent);
    }

    public void setSpecialSymbol(String specialSymbol) {
        this.superMan.setSpecialSymbol(specialSymbol);
    }
}

class AdultSuperManBuilder extends Builder {

    @Override
    protected SuperMan getSuperMan() {
        super.setBody("强健的身体");
        super.setSpecialTalent("成年超人");
        super.setSpecialSymbol("成年标识");
        return super.superMan;
    }
}

class ChildSuperManBuilder extends Builder {

    @Override
    protected SuperMan getSuperMan() {
        super.setBody("灵活的身体");
        super.setSpecialTalent("儿童超人");
        super.setSpecialSymbol("儿童标识");
        return super.superMan;
    }
}

class Director {
    private static final Builder adultBuilder = new AdultSuperManBuilder();
    private static final Builder childBuilder = new ChildSuperManBuilder();

    public static SuperMan getAdultSuperMan() {
        return adultBuilder.getSuperMan();
    }

    public static SuperMan getChildSuperMan() {
        return childBuilder.getSuperMan();
    }
}
public class BuilderDiff {
    public static void main(String[] args) {
        SuperMan adultSuperMan = Director.getAdultSuperMan();
        System.out.println(adultSuperMan);
        System.out.println("======================================");
        System.out.println(Director.getChildSuperMan());
    }
}
