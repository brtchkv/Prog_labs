import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Temp{

    public static void main(String[] args) {
        String s = "Alice,12, Jump(�������, ��� ���� ����� ��� ����������� ������ � ����� �����������!: ������ � �����, ������ ������� ��� ������� ��������.)-Catch()-Fly()";
        Matcher m = Pattern.compile("\\(([^)]+)\\)").matcher(s);


    }
}


