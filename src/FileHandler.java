import java.io.*;
import java.util.*;

//export HUMAN_PATH="/Users/ivan/OneDrive - ITMO UNIVERSITY/Прога/5/Lab/src/human.csv"

public class FileHandler {

    private static Boolean savePermission;

    /**
     * Searches for PATH and converts file to LinkedHashSet
     * @return String with PATH to the file
     */

    public static String getFileName() {
        String collectionPath = System.getenv("HUMAN_PATH");

        String extension = "";

        int i = collectionPath.lastIndexOf('.');
        if (i > 0) {
            extension = collectionPath.substring(i+1);
        }

        if (collectionPath == null) {
            System.out.println("The environment variable HUMAN_PATH is not set!");
            return null;
        }else if (collectionPath.isEmpty()){
            System.out.println("The environment variable HUMAN_PATH can not be void!");
            return null;
        }else if (!extension.equals("csv")) {
            System.out.println("Be careful: the extension of the file is not CSV!");
            return collectionPath;
        }else{
            return collectionPath;
        }

    }

    /**
     * Converts CSV file to LinkedHashSet
     * @return LinkedHashSet with HUMAN objects
     */

    public static LinkedHashSet convertToLinkedHashSet(){

        LinkedHashSet<Human> humans = new LinkedHashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FileHandler.getFileName()))) {
            String line;

            while ((line = br.readLine()) != null) {
                List<String> upper = CSVUtils.parseLine(line);
                if (upper.get(0) == " ") break;
                try
                {
                    Integer.parseInt(upper.get(1));
                }
                catch(NumberFormatException nfe)
                {
                    System.out.println("Error whilst importing a human!\nPerhaps, \""+ upper.get(0) +"\" Human has an unrecognizable age.");
                    throw new Exception();
                }
                Human temp = upper.size() > 1 ? new Human(upper.get(0), Integer.valueOf(upper.get(1))) : new Human(upper.get(0));
                temp.welcome();
                try{
                    if(upper.size() > 2) {
                        String[] skills = upper.get(2).split("-");
                        for (String i : skills) {
                            List<String> lower_skill = CSVUtils.parseLine(i.trim(), ':');
                            if(lower_skill.size() > 1) {
                                temp.addSkill(new Skill(lower_skill.get(0), lower_skill.get(1)));
                            }
                            else {
                                System.out.println("\nNot enough of attributes for adding \""+ upper.get(0) + "\"'s skill!");
                                throw new Exception();
                            }

                        }
                    }
                }catch (Exception e){ throw new Exception(); }

                try{
                    List<String> lower_dis = CSVUtils.parseLine(upper.get(3).trim(), '-');
                    for (int i = lower_dis.size() - 1; i >= 0; i--) {
                        temp.addDisability(new Disability(lower_dis.get(i)));
                    }
                }catch (Exception e){}

                humans.add(temp);
            }
            savePermission = true;

        } catch (FileNotFoundException e) {
            System.out.println("File is not found!");
            humans = null;
            savePermission = false;
        } catch (IOException e){
            System.out.println("Can't import file");
            humans = null;
            savePermission = false;
        } catch (Exception e){
            System.out.println("Some error, in an attempt to import the file!");
            humans = null;
            savePermission = false;
        }
        return humans;
    }

    /**
     * Saves a collection to a file in a CSV format
     * @param humans: (LinkedHashSet) - a collection to save of Human objects
     */
    public static void save(LinkedHashSet<Human> humans){
        try(    FileOutputStream n = new FileOutputStream(FileHandler.getFileName(), false);
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

    public static boolean checkFileRead(){
        try {
            File file = new File(FileHandler.getFileName());
            boolean exists = file.exists();
            if (exists) {
                if (!file.canRead()) {
                    System.out.println("Permission denied: Can't read the file!");
                    return false;
                }else {
                    return true;
                }
            } else {
                System.out.println("File does not exist!");
                return false;
            }
        }catch (Exception e){
            System.out.println("The environment variable HUMAN_PATH is not set!");
            return false;}
    }

    public static boolean checkFileWrite(){
        try {
            File file = new File(FileHandler.getFileName());
            boolean exists = file.exists();
            if (exists) {
                if (!file.canWrite()) {
                    System.out.println("Permission denied: Can't write to the file!");
                    System.out.println("hehe");
                    return false;
                }else {
                    return true;
                }
            } else {
                System.out.println("File does not exist!");
                return false;
            }
        }catch (Exception e){return false;}
    }

    public static void setSavePermission(Boolean mode){
        savePermission = mode;
    }
    public static Boolean getSavePermission(){
        return savePermission;
    }

}
