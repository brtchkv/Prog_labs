package shared;

import java.io.*;
import java.util.*;

public class FileHandler {

    private static Boolean savePermission;
    private static String FILEPATH;

    public static String getFILEPATH() {
        return FILEPATH;
    }

    /**
     * Searches for PATH and converts file to LinkedHashSet
     * @return String with PATH to the file
     */

    public static String getFileName() {
        String collectionPath = System.getenv("HUMAN_PATH").trim();

        String extension = "";

        int i = collectionPath.lastIndexOf('.');
        if (i > 0) {
            extension = collectionPath.substring(i+1);
        }

        try {
            File file = new File(collectionPath);
            if (file.isDirectory()) {
                System.out.println("Variable leads to a directory: " + file.getName());
                return null;
            }
        }catch (Exception e){}

        if (collectionPath == null) {
            System.out.println("The environment variable HUMAN_PATH is not set!");
            return null;
        }else if (collectionPath.isEmpty()){
            System.out.println("The environment variable HUMAN_PATH can not be void!");
            return null;
        }else if (!extension.equals("csv")) {
            System.out.println("Be careful: the extension of the file is not CSV!");
            FILEPATH = collectionPath;
            return collectionPath;
        }else{
            FILEPATH = collectionPath;
            return collectionPath;
        }

    }

    /**
     * Converts CSV file to LinkedHashSet
     * @return LinkedHashSet with HUMAN objects
     */

    public static Vector<Human> convertToVector(String path, String username){

        Vector<Human> humans = new Vector<>();

        try {
            File file = new File(path);
            if (file.isDirectory()) {
                System.out.println("Path leads to a directory: " + file.getName());
                return humans;
            }

            long len = (int) file.length();
            if (len > 3583) {
                System.out.println("File exceeds 64KB and thus can't be processed!\nThus, an empty collection has been transferred.");
                return humans;
            }

            if (FileHandler.checkFileRead(path)) {}
            else {
                return humans;
            }

        }catch (Exception e){}

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;

            while ((line = br.readLine()) != null) {
                List<String> upper = CSVUtils.parseLine(line);
                if (upper.get(0).equals(" ")) break;
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

                try {
                    temp.setOwner(username);
                }catch (Exception e){}
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
                    List<String> lower_dis = CSVUtils.parseLine(upper.get(4).trim(), '-');
                    for (int i = lower_dis.size() - 1; i >= 0; i--) {
                        //temp.addDisability(new Disability(lower_dis.get(i)));
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
    public static byte[] save(Vector<Human> humans){

            try (FileOutputStream n = new FileOutputStream(FILEPATH, false);
                 PrintWriter printWriter = new PrintWriter(n)) {

                Iterator<Human> iterator = humans.iterator();
                while (iterator.hasNext()) {
                    StringBuffer s = new StringBuffer();
                    Human temp = iterator.next();

                    s.append("\"" + temp.getName() + "\"," + "\"" + temp.getAge() + "\"" + ",\"" + temp.getOwner() + "\"");

                    Iterator<Skill> iterator_s = temp.getSkills().iterator();
                    if (iterator_s.hasNext()) {
                        s.append(",\"");
                    }
                    while (iterator_s.hasNext()) {
                        Skill skill = iterator_s.next();
                        if (iterator_s.hasNext()) {
                            s.append(skill.toString() + "-");
                        } else {
                            s.append(skill.toString() + "\"");
                        }
                    }

//                    Iterator<Disability> iterator_d = temp.getDisabilities().iterator();
//                    if (iterator_d.hasNext()) {
//                        s.append(",\"");
//                    }
//                    while (iterator_d.hasNext()) {
//                        Disability disability = iterator_d.next();
//                        if (iterator_d.hasNext()) {
//                            s.append(disability.toString() + "-");
//                        } else {
//                            s.append(disability.toString() + "\"");
//                        }
//                    }

                    printWriter.println(s);
                }

                return "<<<<<<<<<<<<<<< The source file has been updated >>>>>>>>>>>>>>>".getBytes();

            } catch (Exception e) {
                return "Something bad has happened; Can't save!".getBytes();
            }
    }

    public static byte[] save(Vector<Human> humans, String path) {
        FileHandler.setFILEPATH(path);
        return FileHandler.save(humans);
    }

    public static void setFILEPATH(String FILEPATH) {
        FileHandler.FILEPATH = FILEPATH;
    }

    public static boolean checkFileRead(String path){
        try {
            File file = new File(path);

            boolean exists = file.exists();
            if (exists && file.isFile()) {
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
        }catch (Exception e){ return false;}
    }


    public static boolean checkFileWrite(String path){
        try {
            File file = new File(path);
            boolean exists = file.exists();
            if (exists && file.isFile()) {
                if (!file.canWrite()) {
                    System.out.println("Permission denied: Can't write to the file!");
                    return false;
                }else {
                    return true;
                }
            } else {
                if (file.isDirectory()) {
                    System.out.println("Path leads to a directory: " + file.getName());
                } else {
                    System.out.println("File does not exist!");
                }
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
