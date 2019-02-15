import java.util.Arrays;

public class Temp{
    String name ;
    int age;
    Skill skill;
    String disability;

    public Human createHuman(){
        Human human = new Human(name, age);
        human.addSkill(skill);
        human.addDisability(new Disability(disability));
        return human;
    }
}


