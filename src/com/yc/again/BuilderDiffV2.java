package com.yc.again;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
class Design {
    private String engine;
    private String wheel;
}

@Data
@AllArgsConstructor
class Car {
    private String engine;
    private String wheel;
}

abstract class CarBuilder {
    @Getter
    protected Design design;

    public void receiveDesign(Design design) {
        this.design = design;
    }

    public Car builderCar() {
        return new Car(this.builderEngine(), this.builderWheel());
    }

    protected abstract String builderEngine();

    protected abstract String builderWheel();
}

class BaomBuilder extends CarBuilder {

    @Override
    protected String builderEngine() {
        return super.getDesign().getEngine();
    }

    @Override
    protected String builderWheel() {
        return super.getDesign().getWheel();
    }
}

class BenzBuilder extends CarBuilder {

    @Override
    protected String builderEngine() {
        return super.getDesign().getEngine();
    }

    @Override
    protected String builderWheel() {
        return super.getDesign().getWheel();
    }
}

class CarDirector {
    private final CarBuilder benzBuilder = new BenzBuilder();
    private final CarBuilder baomBuilder = new BaomBuilder();

    public Car createBenzCar() {
        return createCar(benzBuilder, "Benz engine", "Benz wheel");
    }

    public Car createBaomCar() {
        return createCar(baomBuilder, "Baom engine", "Baom wheel");
    }

    public Car createMixCar() {
        return createCar(baomBuilder, "Baom engine", "Benz wheel");
    }

    private Car createCar(CarBuilder carBuilder, String engine, String wheel) {
        Design design = new Design();
        design.setEngine(engine);
        design.setWheel(wheel);
        carBuilder.receiveDesign(design);
        return carBuilder.builderCar();
    }
}

public class BuilderDiffV2 {
    public static void main(String[] args) {
        CarDirector director = new CarDirector();
        Car car = director.createMixCar();
        System.out.println("mix: " + car);

        car = director.createBaomCar();
        System.out.println("baom: " + car);

        car = director.createBenzCar();
        System.out.println("benz: " + car);
    }
}
