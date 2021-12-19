package com.yc.pet;

import java.util.List;

public class Pets {
    public static final PetCreator petCreator = new LiteralPetCreator();

    public static Pet randomPet(){
        return petCreator.randomPet();
    }

    public static Pet[] createArray(int size){
        return petCreator.createPet(size);
    }

    public static List<Pet> arrayList(int size){
        return petCreator.arrayList(size);
    }
}
