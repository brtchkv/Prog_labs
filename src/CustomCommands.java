import java.util.Iterator;
import java.util.LinkedHashSet;

public class CustomCommands {
    private LinkedHashSet<Human> collection;

    public CustomCommands(LinkedHashSet<Human> collection){
        this.collection = new LinkedHashSet<>(collection);
    }

    /**
     * Method shows information about storing collection
     */
    public void info(){
        System.out.println("Collection has LinkedHashSet type and contains Human objects.");
        System.out.println("Generated from " + File.getFileName() + " file.");
        System.out.println("it contains " + collection.size() + " elements now.");
    }

    /**
     * Method outputs all elements of the collection in string representation
     */
    public void show() {
        Iterator<?> iterator = collection.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next().toString() + ", ");
        }
    }

    /**
     * If collection contains an item (argument humanToRemove), delete it.
     * @param humanToRemove : (Human) - Object of class Human
     */
    public void remove(Human humanToRemove) {
        if (collection.remove(humanToRemove)) System.out.println("The element has been deleted.");
        else System.out.println("This person doesn't exist in the collection!");
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
            if (anotherHuman != endObject) {
                iterator.remove();
                count++;
            }else {break;}
        }
        System.out.println("Deleted " + count + " elements.");
    }

    /**
     * Method removes collection's items which greater than argument startObject.
     * @param startObject (Human) - Object of class Human
     */
    public void remove_greater(Human startObject) {
        Boolean go = Boolean.FALSE;
        Iterator<Human> iterator = collection.iterator();

        int count = 0;
        while (iterator.hasNext()) {
            Human anotherHuman = iterator.next();
            if (anotherHuman == startObject) {
                go = Boolean.TRUE;
                continue;
            } else if (go){iterator.remove(); count++;}
        }
        System.out.println("Deleted " + count + " elements.");
    }

    /**
     * Method adds item to a collection.
     * @param human : (Human) - Object of class Human
     */
    public void add(Human human) {
        if (collection.add(human)) System.out.println("An element has been added.");
        else System.out.println("Collection stores already this object.");
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
            collection.add(human);
            System.out.println("The smallest-named element has been added.");
        }else{
            System.out.println("An element's name isn't the smallest!");
        }
    }

}
