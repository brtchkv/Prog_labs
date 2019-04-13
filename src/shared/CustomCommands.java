package shared;

import java.util.Iterator;
import java.util.Vector;

public class CustomCommands {
    private Vector<Human> collection;

    public CustomCommands(Vector<Human> collection){
        if (collection != null) {
            this.collection = new Vector<>(collection);
        }else {
            System.out.println("Due to the recent errors, an empty collection was created.");
            this.collection = new Vector<>();}
    }

    /**
     * Method shows information about storing collection.
     */
    public void info(){

        System.out.println("Collection has LinkedHashSet type and contains Human objects.");
        System.out.println("Generated from \"" + FileHandler.getFileName() + "\" file.");
        System.out.println("Currently it contains " + collection.size() + " elements.");

    }

    /**
     * Method outputs all elements of the collection in string representation.
     */
    public void show() {

        Iterator<?> iterator = collection.iterator();
        while (iterator.hasNext()) {
                System.out.println(iterator.next().toString());
        }

    }

    /**
     * If collection contains an item (argument humanToRemove), delete it.
     * @param humanToRemove : (Human) - Object of class Human
     */
    public void remove(Human humanToRemove) {
        if (collection.remove(humanToRemove)) {
            System.out.println("The element has been deleted.");
        }else{ System.out.println("This person doesn't exist in the collection!");}

    }

    /**
     * Method removes collection's items which lower than argument endObject.
     * @param endObject (Human) - Object of class Human
     */
    public void remove_lower(Human endObject) {
        Iterator<Human> iterator = collection.iterator();

        int count = 0;
        while (iterator.hasNext()) {
            Human anotherHuman = iterator.next();
            if (anotherHuman.compareTo(endObject) > 0) {
                iterator.remove();
                count++;
            }
        }
        if(count != 0) {
            System.out.println("Deleted " + count + " elements.");
        }else{
            System.out.println("Deleted 0 elements.");
        }
    }

    /**
     * Method removes collection's items which greater than argument startObject.
     * @param startObject (Human) - Object of class Human
     */
    public void remove_greater(Human startObject) {

        Iterator<Human> iterator = collection.iterator();

        int count = 0;
        while (iterator.hasNext()) {
            Human anotherHuman = iterator.next();
            if (anotherHuman.compareTo(startObject) < 0) {
                iterator.remove();
                count++;
            }
        }
        if(count != 0) {
            System.out.println("Deleted " + count + " elements.");
        }else{
            System.out.println("Deleted 0 elements.");
        }

    }

    /**
     * Method adds item to a collection.
     * @param human : (Human) - Object of class Human
     */
    public void add(Human human) {
        if (collection.add(human)) human.welcome();
        else System.out.println("Collection already stores this object.");

    }

    /**
     * Method adds item to a collection if its name value is less than that of the smallest-named element of this collection.
     * @param human : (Human) - Object of class Human
     */
    public void add_if_min(Human human) {

        Iterator<Human> iterator = collection.iterator();

        int n = iterator.next().getName().length();

        int i = 0;
        while (iterator.hasNext()) {
            i = iterator.next().getName().length();
            if (i < n) {
                n = i;
            }
        }

        if (human.getName().length() < i) {
            collection.add(human); human.welcome();
        }else{
            System.out.println("An element's name isn't the smallest! This human wasn't added to the collection!");
        }
    }
    /**
     * Method saves the collection to the source file based on the value of "savePermission" variable.
     * Besides, checks whether the collection is null.
     */
    public void save(){

        if (FileHandler.checkFileWrite(FileHandler.getFILEPATH())) {
            if (collection != null){
                FileHandler.save(collection);
            }else {
                System.out.println("The Collection is null; Can't be saved!");
            }
        }
    }
    /**
     * Method prints information on commands.
     * @param command : (String) - a string with the command.   
     */

    public void infoCommand(String command){

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
