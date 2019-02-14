public class Disability {
    private String disability;
    private int value;

    public Disability(String disability){
        this.disability = disability;
    }

    public String getName(){ return this.disability; }

    @Override
    public boolean equals(Object object)
    {
        boolean sameSame = false;

        if (object != null && object instanceof Disability)
        {
            sameSame = this.value == ((Disability) object).value;
        }

        return sameSame;
    }

    @Override
    public int hashCode() {
        return (getName().hashCode());
    }



}
