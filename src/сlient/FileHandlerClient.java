package ñlient;


import java.io.*;

//export HUMAN_PATH="/Users/ivan/OneDrive - ITMO UNIVERSITY/Ïðîãà/5/Lab/src/human.csv"

public class FileHandlerClient {

    private static Boolean savePermission;

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
            return collectionPath;
        }else{
            return collectionPath;
        }

    }


    public static boolean checkFileRead(){
        try {
            File file = new File(FileHandlerClient.getFileName());

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
        }catch (Exception e){ return false;}
    }

    public static boolean checkFileWrite(){
        try {
            File file = new File(FileHandlerClient.getFileName());
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
}
