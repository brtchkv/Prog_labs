package server;

public class Temp{
    String name ;
    int age;
    Skill skill;
    String disability;

    public Human createHuman(){

        Human human = age != 0 ? new Human(name, age): new Human(name);

        if (skill != null) {
            human.addSkill(skill);
        }
        if (disability != null) {
            human.addDisability(new Disability(disability));
        }
        return human;
    }
}


