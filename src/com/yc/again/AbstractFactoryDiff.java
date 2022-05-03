package com.yc.again;

interface ICar {
    String getBand();
    String getModel();
}

abstract class AbsBenz implements ICar {
    private static final String BENZ_BAND = "奔驰";

    public String getBand() {
        return BENZ_BAND;
    }

}

abstract class AbsBaom implements ICar {
    private static final String Baom_BAND = "宝马";

    public String getBand() {
        return Baom_BAND;
    }

}

class BenzSuv extends AbsBenz {
    private static final String _MODEL = "Benz_SUV";

    @Override
    public String getModel() {
        return _MODEL;
    }
}

class BenzVAN extends AbsBenz {
    private static final String _MODEL = "Benz_VAN";

    @Override
    public String getModel() {
        return _MODEL;
    }
}

class BaomSUV extends AbsBaom {
    private static final String _MODEL = "Baom_SUV";

    @Override
    public String getModel() {
        return _MODEL;
    }
}

class BaomVAN extends AbsBaom {
    private static final String _MODEL = "Baom_VAN";

    @Override
    public String getModel() {
        return _MODEL;
    }
}

interface CarFactory {
    ICar createSUV();
    
    ICar createVAN();
}

class BenzFactory implements CarFactory {

    @Override
    public ICar createSUV() {
        return new BenzSuv();
    }

    @Override
    public ICar createVAN() {
        return new BenzVAN();
    }
}

class BaomFactory implements CarFactory {

    @Override
    public ICar createSUV() {
        return new BaomSUV();
    }

    @Override
    public ICar createVAN() {
        return new BaomVAN();
    }
}

public class AbstractFactoryDiff {
    public static void main(String[] args) {
        CarFactory baomFactory = new BaomFactory();
        ICar suv = baomFactory.createSUV();
        System.out.println(suv.getBand() + "  " + suv.getModel());
        ICar van = baomFactory.createVAN();
        System.out.println(van.getBand() + "   " + van.getModel());
    }
}
