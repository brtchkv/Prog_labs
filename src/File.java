import java.io.*;
import java.util.*;


public class File {
    /**
     * Searches for PATH and converts file to LinkedHashSet
     * @return String with PATH to the file
     */

    public static String getFileName() {
        String collectionPath = System.getenv("HUMAN_PATH");
        if (collectionPath == null) {
            System.out.println("The environment variable HUMAN_PATH is not set!");
            return null;
        }else if (collectionPath.isEmpty()){
            System.out.println("The environment variable HUMAN_PATH can not be void!");
            return null;
        }else {
            return collectionPath;
        }

    }

    /**
     * Converts CSV file to LinkedHashSet
     * @return LinkedHashSet with HUMAN objects
     */

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

        } catch (NullPointerException | FileNotFoundException e) {
            System.out.println("File is not found!");

        } catch (IOException e){
            System.out.println("Error: either reading or writing!");

        } catch (Exception e){
            System.out.println("Some undefined error, while importing a file!");
            e.printStackTrace();
        }
        return humans;
    }

    /**
     * Saves a collection to a file in a CSV format
     * @param humans: (LinkedHashSet) - a collection to save of Human objects
     */
    public static void save(LinkedHashSet<Human> humans){

        try(    FileOutputStream n = new FileOutputStream(File.getFileName(), false);
                PrintWriter printWriter = new PrintWriter (n)){

            Iterator<Human> iterator = humans.iterator();
            while (iterator.hasNext()) {
                StringBuffer s = new StringBuffer();
                Human temp = iterator.next();
                s.append("\"" + temp.getName() + "\"," + "\""+ temp.getAge() + "\"");
                Iterator<Skill> iterator_s = temp.getSkills().iterator();
                if (iterator_s.hasNext()){s.append(",\"");}
                while (iterator_s.hasNext()) {
                    Skill skill = iterator_s.next();
                    if (iterator_s.hasNext()) {
                        s.append(skill.toString()+"-");
                    }else{s.append(skill.toString() + "\"");}
                }

                Iterator<Disability> iterator_d = temp.getDisabilities().iterator();
                if (iterator_d.hasNext()){s.append(",\"");}
                while (iterator_d.hasNext()) {
                    Disability disability = iterator_d.next();
                    if (iterator_d.hasNext()) {
                        s.append(disability.toString() + "-");
                    }else{s.append(disability.toString() + "\"");}
                }
                printWriter.println(s);
            }

            System.out.println("<<<<<<<<<<<<<<< The source file has been updated >>>>>>>>>>>>>>>");

        }catch (Exception e){
            System.out.println("Something bad has happened; Can't save!");
        }
    }

}
