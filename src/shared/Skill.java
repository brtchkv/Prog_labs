package shared;

import java.io.Serializable;
import java.util.Objects;

public class Skill implements Serializable {
    private String name;
    private String info;

    public Skill(String name, String info){
        this.name = name;
        this.info = info;
    }

    public String getName(){ return name; }

    public String doSkill(){
        if (info == null || info.equals("undefined")) return "undefined";
        else return  info;
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
                Objects.equals(info, skill.info);
    }

    public String getAction() {
        return info;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, info);
    }
}
