package shared;

import java.io.Serializable;

public class Temp implements Serializable {
    String name ;
    int age;
    Skill skill;
    String disability;
    int size;
    int x;
    int y;
    int id;

    public Human createHuman(){
        Human human = age != 0 ? new Human(name, age): new Human(name);

        human.setX(x);
        human.setY(y);
        human.setSize(size);

        if (id != 0){
            human.setId(id);
        }

        if (skill != null) {
            human.addSkill(skill);
        }
        if (disability != null) {
            human.addDisability(new Disability(disability));
        }
        return human;
    }
}
