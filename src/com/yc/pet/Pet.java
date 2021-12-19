package com.yc.pet;

class Dog extends  Pet{
    public Dog(){super();}
    public Dog(String name){super(name);}
}
class Cat extends  Pet{
    public Cat(){super();}
    public Cat(String name){super(name);}
}
class Rodent extends  Pet{
    public Rodent(){super();}
    public Rodent(String name){super(name);}
}
class Rat extends  Rodent{
    public Rat(){super();}
    public Rat(String name){super(name);}
}
class Mouse extends  Rodent{
    public Mouse(){super();}
    public Mouse(String name){super(name);}
}
class Hamster extends  Rodent{
    public Hamster(){super();}
    public Hamster(String name){super(name);}
}
class Mutt extends  Dog{
    public Mutt(){super();}
    public Mutt(String name){super(name);}
}
class Pug extends  Dog{
    public Pug(){super();}
    public Pug(String name){super(name);}
}
class EgyptianMau extends  Cat{
    public EgyptianMau(){super();}
    public EgyptianMau(String name){super(name);}
}
class Manx extends  Cat{
    public Manx(){super();}
    public Manx(String name){super(name);}
}
class Cymric extends Manx{
    public Cymric(){super();}
    public Cymric(String name){super(name);}
}
public class Pet extends Individual{
    public Pet(){
        super();
    }
    public Pet(String name){
        super(name);
    }
}
