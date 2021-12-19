package com.yc.pet;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LiteralPetCreator extends PetCreator{
    private static final List<Class<? extends Pet>> allTypes =
            Collections.unmodifiableList(Arrays.asList(Pet.class, Dog.class, Cat.class, Rodent.class
                    , Cymric.class, EgyptianMau.class, Hamster.class, Manx.class, Mouse.class, Mutt.class, Pug.class, Rat.class));
    private static final List<Class<? extends Pet>> types = allTypes.subList(allTypes.indexOf(Cymric.class), allTypes.size());
    @Override
    protected List<Class<? extends Pet>> types() {
        return types;
    }

    public static List<Class<? extends Pet>> getAllTypes() {
        return allTypes;
    }
}
