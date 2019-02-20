import java.util.ArrayList;
import java.util.Objects;

public class Human extends God {
    private String type = "Human";

    private ArrayList<Skill> skills = new ArrayList<Skill>();
    private ArrayList<Disability> disabilities = new ArrayList<Disability>();

    public Human(String name, int age) {
        super(name, age, "Human");
    }

    public Human(String name) {
        super(name, "Human");
    }

    public Human(){
        super("Human");
    }

    public void welcome(){
        if (this.getName() != null) {
            System.out.println("---------------------------------");
            System.out.println("������� - " + this.getName() + " - ������!");
            System.out.println("---------------------------------");
        }else{
            System.out.println("---------------------------------");
            System.out.println("�������� ������� ������� �������");
            System.out.println("---------------------------------");}
    }



    public ArrayList<Skill> getSkills(){
        return this.skills;
    }

    public boolean addSkill(Skill skill) {
        if (skills.add(skill)) {
            //System.out.println("������� - " + this.getName() + " - ������� ��������� ����������� " + skill.getName());
            return true;
        } else {
            System.out.println("��� ���������� ����������� ��������� ������...");
            return false;
        }
    }

    public boolean addDisability(Disability d) {
        if (disabilities.add(d)) {
            //System.out.println("������� - " + this.getName() + " - ������� ��������� ��������������� " + d.getName()+".");
            return true;
        } else {
            System.out.println("��� ���������� ��������������� ��������� ������...");
            return false;
        }
    }

    public ArrayList<Disability> getDisabilities(){
        return this.disabilities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Human human = (Human) o;
        return Objects.equals(type, human.type) &&
                skills.equals(human.skills) &&
                disabilities.equals(human.disabilities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), type, skills, disabilities);
    }
}