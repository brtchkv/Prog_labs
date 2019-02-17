import java.util.Objects;

public abstract class God implements  Goddable, Loveable{
    private String name;
    private int age = -1;
    private CreatureType type;

    public God (String name, int age, CreatureType type){
        this.name = name;
        this.age = age;
        this.type = type;
    }

    public God (String name, CreatureType type){
        this.name = name;
        this.age = 0;
        this.type = type;
    }

    public God(CreatureType type){
        this.type = type;
    }

    public int getAge() {
        class Age{
        int age;
        Age(int age){this.age = age;}

        int getAge(){
            return this.age;
        }
    }

        Age age = new Age(this.age);

        return age.getAge();
    }

    public String getName(){
        return this.name;
    }

    public String getType(){
        return this.type.toString();
    }

    public void touch (int age) throws NegativeAgeException {
        try{
            if (age < 0){
                throw new NegativeAgeException("Возраст не может быть отрицательным! ", age);
            }else {
                this.age = age;
            }

        }catch (NegativeAgeException e) {
            System.out.println("Возникло исключение! " + e);
        }
    }

    @Override
    public String toString() {
        return (getName() + " -- " + getType() + " -- " + getAge());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, type);
    }

    @Override
    public void love(){
        System.out.println("Love is the answer!");
    }
}
