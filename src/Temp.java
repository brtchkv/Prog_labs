import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Temp{

    public static void main(String[] args) {
        String s = "Alice,12, Jump(Спасибо, что хоть новая шея великолепно гнется в любом направлении!: Тянусь к ногам, изящно изогнув шею плавным зигзагом.)-Catch()-Fly()";
        Matcher m = Pattern.compile("\\(([^)]+)\\)").matcher(s);


    }
}


