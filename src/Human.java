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
        System.out.println("������� - " + name + " - ������!");
        System.out.println("---------------------------------");
    }

    public Human() {
        super(CreatureType.Human);
        System.out.println("---------------------------------");
        System.out.println("�������� ������� ������� �������");
        System.out.println("---------------------------------");
    }

    @Override
    public void touchGrass() {
        System.out.println("� ��������(�) �����, ������������� �����!");
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
            System.out.println("������� - " + this.getName() + " - ������� ��������� ����������� " + skill.getName());
            return true;
        } else {
            System.out.println("��� ���������� ����������� ��������� ������...");
            return false;
        }
    }

    public boolean addDisability(Disability d) {
        if (disabilities.add(d)) {
            System.out.println("������� - " + this.getName() + " - ������� ��������� ��������������� " + d.getName()+".");
            return true;
        } else {
            System.out.println("��� ���������� ��������������� ��������� ������...");
            return false;
        }
    }

    @Override
    public void setSkills(String new_skills) {
        System.out.println(new_skills + " ���� ");
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
        System.out.println(getName() + " ���������.");
    }

    public void toDo(){
        System.out.println(getName() + " ����������, ��� ������� ������ ������: ���� ���������� �������� " +
                "���� ������� �� ������� �����, ����� ��� �������!");
    }

    public void eat() throws NoDisabilityException {
        try {
            Disability d = new Disability("chin");
            if (disabilities.contains(d) == false) {
                System.out.println("������� � �������, ���� ����! " + getName() + " ������� � ������ ���� ������ ����(�)");
            } else {
                throw new NoDisabilityException("���� ����������� � ����������������!", d);
            }
        }catch (NoDisabilityException e){
            System.out.println("~~~~~~~~~~~~~~ "+ "�������� ����������! " + e + " ~~~~~~~~~~~~~~");
            System.out.println("���������� " + getName() + " ������� ������ ������� � �����. " + "����� ������ ������� ���");
            System.out.println(getName()+ " ���������� ���-��� �������� � ���������� ��������."+" ���! ������ �� ����!");
            disabilities.add(new Disability("head"));
            disabilities.add(new Disability("hands"));
        }
    }

    public void checkHead() throws NoDisabilityException {
        try{
            Disability d = new Disability("head");
            if (disabilities.contains(d) == false) {
                System.out.println("������ " + getName() + "(�) �� �����.");
            }else {
                throw new NoDisabilityException("���� ����������� � ����������������!", d);
            }
        }catch (NoDisabilityException e){
            System.out.println("~~~~~~~~~~~~~~ "+ "�������� ����������! " + e + " ~~~~~~~~~~~~~~");
            System.out.println("�� ���, ������ ��� ������� �������� �������: ������ ����-�� ������� ��� �����! " +
                    "�� ����� ��� � ���� ������!");
            System.out.println(getName()+ " ������ �� ��� �����, �� ����� ������ �� �����, ����� ���������� ������� ���, " +
                    "������������, ������ �����, ��� ����� ����� ������. \n- ���� �� ��� ����� ��������?" +
                    " - ������ �������(�) " + getName() + " - � ��� ��� �� ����� ����, ���������!");
        }
    }

    public void checkHands() throws NoDisabilityException {
        try{
            Disability d = new Disability("hands");
            if (disabilities.contains(d) == false) {
                System.out.println("���� " + getName() + "(�) �� �����.");
            }else {
                throw new NoDisabilityException("���� ����������� � ����������������!", d);
            }
        }catch (NoDisabilityException e){
            System.out.println("~~~~~~~~~~~~~~ "+ "�������� ����������! " + e + " ~~~~~~~~~~~~~~");
            System.out.println("��, ����� ��� �������, � �� �������! ");
            System.out.println("��� ��, ��-�! ��� "+ getName() +" ����������(�) ���������� ������, �� ����� ��������������. " +
                    "������ ���-�� ���, ������ �����, ������ ������ ������ �� ������. " +
                    "\n�� ��� �, ���� ������� ���� � ������ ���� ����������, ����� ����������� ��������� � ��� ������.");
        }
    }

    public void printDiabilities(){
        for (int i = 0; i < disabilities.size(); i++) {
            System.out.println(disabilities.get(i).getName());
        }
    }

    public void resent(){
        System.out.println("- ����� � ��� ����! - ����������� �����.- �������� ���� � �����! ");
    }

    public void notUnderstand(){
        System.out.println("- � ��� �� ��������? � ������ �� �������,- ������� " + getName());
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