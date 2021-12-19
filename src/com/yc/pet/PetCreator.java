package com.yc.pet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public abstract class PetCreator {
    private final Random random = new Random();

    protected abstract List<Class<? extends Pet>> types();

    public Pet randomPet(){
        int i = random.nextInt(types().size());
        Class<? extends Pet> aClass = types().get(i);
        Pet pet = null;
        try {
             pet = aClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return pet;
    }

    public Pet[] createPet(int size){
        Pet[] pets = new Pet[size];
        for (int i = 0; i < size; i++) {
            pets[i] = randomPet();
        }
        return pets;
    }

    public List<Pet> arrayList(int size){
        List<Pet> list = new ArrayList<>();
        Collections.addAll(list, createPet(size));
        return list;
    }
}
