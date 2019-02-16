import java.util.ArrayList;

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