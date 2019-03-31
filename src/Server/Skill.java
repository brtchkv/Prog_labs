package Server;

import java.io.Serializable;
import java.util.Objects;

public class Skill implements Serializable {
    private String name;
    private String action;

    public Skill(String name, String info){
        this.name = name;
        this.action = info;
    }

    public String getName(){ return name; }

    public String doSkill(){
        if (action == null || action.equals("undefined")) return "undefined";
        else return  action;
    }


    public String toString(){
        return (this.getName()+ ":"+ doSkill());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skill skill = (Skill) o;
        return Objects.equals(name, skill.name) &&
                Objects.equals(action, skill.action);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, action);
    }
}
