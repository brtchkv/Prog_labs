import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class File {

    private static String getFileName() {
        String collectionPath = System.getenv("HUMAN_PATH");
        if (collectionPath == null) {
            System.out.println("Переменная окружения HUMAN_PATH не установлена.");
            return null;
        }else if (collectionPath.isEmpty()){
            System.out.println("Переменная окружения HUMAN_PATH не должна быть пустой.");
            return null;
        }else {
            return collectionPath;
        }

    }


    public static LinkedHashSet convertToLinkedHashSet(){
        LinkedHashSet<Human> humans = new LinkedHashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(File.getFileName()))) {
            String line;

            while ((line = br.readLine()) != null) {
                List<String> upper = CSVUtils.parseLine(line);
                String[] skills = upper.get(2).split("-");
                Human temp = new Human(upper.get(0), Integer.valueOf(upper.get(1)));
                temp.setSkills();
                System.out.println("[id= " + upper.get(0) + ", code= " + upper.get(1) + " , name= " + upper.get(2)+ "]");

                for (String i: skills){
                    List<String> lower = CSVUtils.parseLine(i.trim(), ':');
                    System.out.println("[ " + lower.get(0) + ", " + lower.get(1) + " , " + lower.get(2) + "]");
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден.");
            e.printStackTrace();
        } catch (IOException e){
            System.out.println("Ошибка чтения/записи.");
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return humans;
    }
}
