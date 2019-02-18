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

}
