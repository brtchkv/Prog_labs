import java.util.Objects;

public class Skill {
    private String name;
    private String info;
    private String action = "undefined";

    public Skill(String name, String info){
        this.name = name;
        this.info = info;
    }

    public String getName(){ return name; }

    public String doSkill(){
        if (action.equals("undefined")) return "undefined";
        else return  action;
    }


    public String getInfo() {
        if (info == null)
            return "undefined";
        else
            return info;
    }

    public String toString(){
        return (this.getName()+ ":"+ getInfo()+ ":" + doSkill());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skill skill = (Skill) o;
        return Objects.equals(name, skill.name) &&
                Objects.equals(info, skill.info) &&
                Objects.equals(action, skill.action);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, info, action);
    }
}
