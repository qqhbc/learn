package com.yc.pet;

import java.util.HashMap;

public class PetCountInstanceOf {
    static class PetCounter extends HashMap<String, Integer>{
        public void count(String type){
            Integer orDefault = getOrDefault(type, 0);
            put(type, orDefault + 1);
        }
    }

    public static void petCounters(Pets pets){
        PetCounter petCounter = new PetCounter();
        for (Pet pet : pets.createArray(20)) {
            System.out.print(pet.getClass().getSimpleName() + " ");
            if(pet instanceof Pet){
                petCounter.count("Pet");
            }
            if(pet instanceof Dog){
                petCounter.count("Dog");
            }
            if(pet instanceof Cat){
                petCounter.count("Cat");
            }
            if(pet instanceof Rodent){
                petCounter.count("Rodent");
            }
            if(pet instanceof Cymric){
                petCounter.count("Cymric");
            }
            if(pet instanceof EgyptianMau){
                petCounter.count("EgyptianMau");
            }
            if(pet instanceof Hamster){
                petCounter.count("Hamster");
            }
            if(pet instanceof Manx){
                petCounter.count("Manx");
            }
            if(pet instanceof Mouse){
                petCounter.count("Mouse");
            }
            if(pet instanceof Mutt){
                petCounter.count("Mutt");
            }
            if(pet instanceof Pug){
                petCounter.count("Pug");
            }
            if(pet instanceof Rat){
                petCounter.count("Rat");
            }

        }
        System.out.println();
        System.out.println(petCounter);
    }

    public static void main(String[] args) {
        petCounters(new Pets());
    }
}
