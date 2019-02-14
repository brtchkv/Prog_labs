public class Skill {
    private String name;
    private String info;

    public Skill(String name, String info){
        this.name = name;
        this.info = info;
    }

    public String getName(){ return name; }

    public String doSkill(){
        return "undefined";
    }


    public String getInfo() {
        if (info == null)
            return "undefined";
        else
            return info;
    }

    public String toString(){
        return (this.getName()+ " "+ getInfo()+ " " + doSkill());
    }

}
