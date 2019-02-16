public class Temp{
    String name ;
    int age;
    Skill skill;
    String disability;

    public Human createHuman(){
        Human human = new Human(name, age);
        if (skill != null) {
            human.addSkill(skill);
        }
        if (disability != null) {
            human.addDisability(new Disability(disability));
        }
        return human;
    }
}


