public class NegativeAgeException extends Exception {

    int age;

    public NegativeAgeException (String msg, int age){
        super(msg);
        this.age = age;
    }

    public int GetAge() {
        return age;
    }

}
