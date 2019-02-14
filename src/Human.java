import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Human extends God implements Humanable {
    private int id;

    private ArrayList<Skill> skills = new ArrayList<Skill>();
    private ArrayList<Disability> disabilities = new ArrayList<Disability>();

    public Human(String name, int age) {
        super(name, age, CreatureType.Human);
        System.out.println("---------------------------------");
        System.out.println("Человек - " + name + " - создан!");
        System.out.println("---------------------------------");
    }

    public Human() {
        super(CreatureType.Human);
        System.out.println("---------------------------------");
        System.out.println("Безликий человек успешно создано");
        System.out.println("---------------------------------");
    }

    @Override
    public void touchGrass() {
        System.out.println("Я потрогал(а) траву, действительно мягко!");
    }

    @Override
    public void getSkillInfo(int i) {
         System.out.println(skills.get(i).getInfo());
    }

    public void doSkill(int i) {
        System.out.println(skills.get(i).doSkill());
    }

    public boolean addSkill(Skill skill) {
        if (skills.add(skill)) {
            System.out.println("Объекту - " + this.getName() + " - успешно присвоена способность " + skill.getName());
            return true;
        } else {
            System.out.println("При добавлении способности произошла ошибка...");
            return false;
        }
    }

    public boolean addDisability(Disability d) {
        if (disabilities.add(d)) {
            System.out.println("Объекту - " + this.getName() + " - успешно присвоена несовершенность " + d.getName()+".");
            return true;
        } else {
            System.out.println("При добавлении несовершенности произошла ошибка...");
            return false;
        }
    }

    @Override
    public void setSkills(String new_skills) {
        System.out.println(new_skills + " тута ");
        String[] skills_raw = new_skills.split("-");
        System.out.println(Arrays.toString(skills_raw));
        for (String i: skills_raw){
            System.out.println(i);
            String name = i.split("\\(")[0].trim();
            Matcher m = Pattern.compile("\\(([^)]+)\\)").matcher(i);

            ArrayList<String[]> skill_stuff = new ArrayList<>();
            while(m.find()) {
                System.out.println(m.group(1));
                Collections.addAll(skill_stuff, m.group(1).split(":"));
            }
            System.out.println(skill_stuff.size());
            if (skill_stuff.size() == 0) {
                System.out.println(skill_stuff.get(0)[0].trim());
                Skill temp = new Skill(name, skill_stuff.get(0)[0].trim()) {
                    @Override
                    public String doSkill() {
                        return (skill_stuff.get(0)[1].trim());
                    }
                };
                this.addSkill(temp);

            } else if(skill_stuff.size() == 1){
                    Skill temp = new Skill(name, skill_stuff.get(0)[0].trim()) {};
                    this.addSkill(temp);
            }

        }
    }


    public void stopAction() {
        System.out.println(getName() + " отпрянула.");
    }

    public void toDo(){
        System.out.println(getName() + " сообразила, что времени терять нельзя: надо немедленно откусить " +
                "хоть чуточку от другого куска, иначе она пропала!");
    }

    public void eat() throws NoDisabilityException {
        try {
            Disability d = new Disability("chin");
            if (disabilities.contains(d) == false) {
                System.out.println("Челюсть в порядке, могу есть! " + getName() + " успешно и вполне себе вкусно поел(а)");
            } else {
                throw new NoDisabilityException("Есть ограничение в функционировании!", d);
            }
        }catch (NoDisabilityException e){
            System.out.println("~~~~~~~~~~~~~~ "+ "Возникло исключение! " + e + " ~~~~~~~~~~~~~~");
            System.out.println("Подбородок " + getName() + " слишком сильно прижало к ногам. " + "Никак нельзя открыть рот");
            System.out.println(getName()+ " ухитрилась кое-как откусить и проглотить крошечку."+" Ура! Голова на воле!");
            disabilities.add(new Disability("head"));
            disabilities.add(new Disability("hands"));
        }
    }

    public void checkHead() throws NoDisabilityException {
        try{
            Disability d = new Disability("head");
            if (disabilities.contains(d) == false) {
                System.out.println("Голова " + getName() + "(ы) на месте.");
            }else {
                throw new NoDisabilityException("Есть ограничение в функционировании!", d);
            }
        }catch (NoDisabilityException e){
            System.out.println("~~~~~~~~~~~~~~ "+ "Возникло исключение! " + e + " ~~~~~~~~~~~~~~");
            System.out.println("Ну вот, теперь мой восторг сменился испугом: теперь куда-то пропали мои плечи! " +
                    "Ну прямо как в воду канули!");
            System.out.println(getName()+ " глядит во все глаза, но внизу ничего не видно, кроме бесконечно длинной шеи, " +
                    "вздымавшейся, словно мачта, над целым морем зелени. \n- Куда же они могли деваться?" +
                    " - громко спросил(а) " + getName() + " - А это что за новое море, интересно!");
        }
    }

    public void checkHands() throws NoDisabilityException {
        try{
            Disability d = new Disability("hands");
            if (disabilities.contains(d) == false) {
                System.out.println("Руки " + getName() + "(ы) на месте.");
            }else {
                throw new NoDisabilityException("Есть ограничение в функционировании!", d);
            }
        }catch (NoDisabilityException e){
            System.out.println("~~~~~~~~~~~~~~ "+ "Возникло исключение! " + e + " ~~~~~~~~~~~~~~");
            System.out.println("Ой, ручки мои дорогие, и вы пропали! ");
            System.out.println("Где вы, ау-у! Тут "+ getName() +" попробовал(а) пошевелить руками, но почти безрезультатно. " +
                    "Только где-то там, далеко внизу, легкий трепет прошел по зелени. " +
                    "\nНу что ж, если поднять руки к голове было невозможно, можно попробовать наклонить к ним голову.");
        }
    }

    public void printDiabilities(){
        for (int i = 0; i < disabilities.size(); i++) {
            System.out.println(disabilities.get(i).getName());
        }
    }

    public void resent(){
        System.out.println("- Какая я вам змея! - возмутилась Алиса.- Оставьте меня в покое! ");
    }

    public void notUnderstand(){
        System.out.println("- О чем вы говорите? Я ничего не понимаю,- сказала " + getName());
    }

    public boolean equals(Object other) {
        if (!super.equals(other)) return false;
        if (this == other) return true;
        if (other == null) return false;
        if (this.getClass() != other.getClass()) return false;
        Human otherObj = (Human) other;
        return this.id == otherObj.id;
    }

}