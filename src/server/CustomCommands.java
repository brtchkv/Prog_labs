package server;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;

public class CustomCommands implements Serializable {

    private Set<Human> collection;

    public CustomCommands(Set<Human> collection){
        if (collection != null) {
            this.collection = collection;
        }else {
            System.out.println("Due to the recent errors, an empty collection was created.");
            this.collection = Collections.synchronizedSet(new LinkedHashSet());}
    }

    /**
     * Method shows information about storing collection.
     */
    public void info(){
        synchronized(collection) {
            System.out.println("Collection has LinkedHashSet type and contains Human objects.");
            System.out.println("Generated from \"" + FileHandler.getFileName() + "\" file.");
            System.out.println("Currently it contains " + collection.stream().count() + " elements.");
        }
    }

    /**
     * Method outputs all elements of the collection in string representation.
     */
    public void show() {
        synchronized(collection) {
            collection.forEach(System.out::println);
        }
    }

    /**
     * If collection contains an item (argument humanToRemove), delete it.
     * @param humanToRemove : (Human) - Object of class Human
     */
    public void remove(Human humanToRemove) {

        synchronized(collection) {
            if (!collection.contains(humanToRemove)) {
                System.out.println("The element has been deleted.");
            } else {
                System.out.println("This person doesn't exist in the collection!");
            }

            collection.removeIf(x -> !collection.contains(humanToRemove));
        }

    }

    /**
     * Method removes collection's items which lower than argument endObject.
     * @param endObject (Human) - Object of class Human
     */
    public void remove_lower(Human endObject) {
        synchronized(collection) {

            collection.removeIf(item -> item.compareTo(endObject) < 0);

        }
    }

    /**
     * Method removes collection's items which greater than argument startObject.
     * @param startObject (Human) - Object of class Human
     */
    public void remove_greater(Human startObject) {
        synchronized(collection) {

            collection.removeIf(item -> item.compareTo(startObject) > 0);
        }

    }

    /**
     * Method adds item to a collection.
     * @param human : (Human) - Object of class Human
     */
    public void add(Human human) {

        synchronized(collection) {
            if (collection.add(human)) human.welcome();
            else System.out.println("Collection already stores this object.");
        }
    }

    /**
     * Method adds item to a collection if its name value is less than that of the smallest-named element of this collection.
     * @param human : (Human) - Object of class Human
     */
    public void add_if_min(Human human) {

        synchronized(collection) {

            Human minByName = collection
                    .stream()
                    .min(Comparator.comparing(Human :: getName))
                    .get();

            if (minByName.compareTo(human) < 0){
                collection.remove(human);
            }else {System.out.println("An element's name isn't the smallest! This human wasn't added to the collection!");}

        }
    }
    /**
     * Method saves the collection to the source file based on the value of "savePermission" variable.
     * Besides, checks whether the collection is null.
     */
    public Set<Human> save(){


        if (collection != null){
            return collection;
        }else {

            System.out.println("Collection is null; Can't be saved!");
            return null;
            }
    }
//    /**
//     * Method imports collection from the source file on server.
//     */
//    public void importFile(){
//        try {
//
//            Thread t0 = new Thread(new ServerFile());
//            t0.start();
//
//            Thread t1 = new Thread(new ClientFile());
//            t1.start();
//
//
//        }catch (Exception e){
//            System.out.println("Error while transferring the source file!");
//        }
//    }
    /**
     * Method prints information on commands.
     * @param command : (String) - a string with the command.   
     */

    public static void infoCommand(String command){

        String jsonExample = "\r\n{\r\n   \"name\": \"Elizabeth\",\r\n   \"age\": \"16\",\r\n   \"skill\": {\r\n      \"name\": \"\u041F\u0440\u044B\u0433\u0430\u0442\u044C\"\r\n   },\r\n   \"disability\": \"chin\"\r\n}\r";
        switch (command){
            case "add":
                System.out.println("Method adds item to a collection if its name value is less than that of the smallest-named element of this collection.\n" +
                        "@param human : (Human) - Object of class Human written in JSON format.");
                System.out.println("Every human MUST have the following attributes:\n"+"\t1) NAME,\n" +"\t2) AGE");
                System.out.println("Skills and Disabilities are optional.");

                System.out.println("For example:" + jsonExample);
                break;
            case "remove":
                System.out.println("If collection contains an item (argument humanToRemove), delete it. \n@param humanToRemove : (Human) - Object of class Human.");
                System.out.println("Every human MUST have the following attributes:\n"+"\t1) NAME,\n" +"\t2) AGE");
                System.out.println("Skills and Disabilities are optional.");
                System.out.println("For example:" + jsonExample);
                break;

            case "add_if_min":
                System.out.println("Method adds item to a collection if its name value is less than that of the smallest-named element of this collection." +
                        "@param human : (Human) - Object of class Human written in JSON format.");
                System.out.println("Every human MUST have the following attributes:\n"+"\t1) NAME,\n" +"\t2) AGE");
                System.out.println("Skills and Disabilities are optional.");
                System.out.println("For example:" + jsonExample);
                break;
            case "remove_greater":
                System.out.println("Method removes collection's items which greater than argument startObject.");
                System.out.println("@param startObject (Human) - Object of class Human written in JSON format.");
                System.out.println("Every human MUST have the following attributes:\n"+"\t1) NAME,\n" +"\t2) AGE");
                System.out.println("Skills and Disabilities are optional.");
                System.out.println("For example:" + jsonExample);
                break;
            case "remove_lower":
                System.out.println("Method removes collection's items which lower than argument endObject.");
                System.out.println("@param endObject (Human) - Object of class Human written in JSON format.");
                System.out.println("Every human MUST have the following attributes:\n"+"\t1) NAME,\n" +"\t2) AGE");
                System.out.println("Skills and Disabilities are optional.");
                System.out.println("For example:" + jsonExample);
                break;


            default:
                System.out.println("Type \"command_name arg\" for additional info. i.e \"add arg\"");
        }


    }

}
