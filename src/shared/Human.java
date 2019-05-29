package shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Human extends God implements Serializable, Comparable<Human> {

    private String type = "Human";
    private String owner = "all";
    private int size = 1;
    private int x = 0;
    private int y = 0;

    private ArrayList<Skill> skills = new ArrayList<>();
    private ArrayList<Disability> disabilities = new ArrayList<>();

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
            System.out.println("Человек - " + this.getName() + " - создан!");
            System.out.println("---------------------------------");
        }else{
            System.out.println("---------------------------------");
            System.out.println("Безликий человек успешно создано");
            System.out.println("---------------------------------");}
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSize() {
        return size;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public ArrayList<Skill> getSkills(){
        return this.skills;
    }

    public boolean addSkill(Skill skill) {
        if (skills.add(skill)) {
            return true;
        } else {
            System.out.println("При добавлении способности произошла ошибка...");
            return false;
        }
    }

    public boolean addDisability(Disability d) {
        if (disabilities.add(d)) {
            return true;
        } else {
            System.out.println("При добавлении несовершенности произошла ошибка...");
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
        return this.getName().equals(human.getName()) && this.getAge() == human.getAge()
                && this.size == human.getSize() && this.x == human.getX() && this.y == human.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), owner, x, y, size);
    }

    @Override
    public int compareTo(Human human) {
        return this.getName().compareTo(human.getName());
    }

}