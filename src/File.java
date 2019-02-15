import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class File {
    /**
     * Searches for PATH and converts file to LinkedHashSet
     * @return LinkedHashSet with HUMAN objects
     * @throws IOException When file is inaccessible or not found
     */

    public static String getFileName() {
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

                Human temp = new Human(upper.get(0), Integer.valueOf(upper.get(1)));

                try{
                    String[] skills = upper.get(2).split("-");
                    for (String i: skills){
                        List<String> lower_skill = CSVUtils.parseLine(i.trim(), ':');
                        temp.addSkill(new Skill(lower_skill.get(0),lower_skill.get(1)){
                            @Override
                            public String doSkill(){
                                return (lower_skill.get(2));
                            }
                        });

                    }
                }catch (Exception e){}

                try{
                    List<String> lower_dis = CSVUtils.parseLine(upper.get(3).trim(), '-');
                    for (int i = lower_dis.size() - 1; i >= 0; i--) {
                        temp.addDisability(new Disability(lower_dis.get(i)));
                    }
                }catch (Exception e){}

                humans.add(temp);
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
