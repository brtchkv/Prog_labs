package shared;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public abstract class God implements Serializable {
    private String name;
    private int age;
    private String type;
    private OffsetDateTime dateTime = OffsetDateTime.now(); // Current date time with an offset
    private int id = dateTime.getNano();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDateTime(OffsetDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public God (String name, int age, String type){
        this.name = name;
        this.age = age;
        this.type = type;
    }

    public God (String name, String type){
        this.name = name;
        this.type = type;
    }

    public God(String type) {
        this.type = type;
    }

    public int getAge() {
        return this.age;
    }

    public String getName(){
        return this.name;
    }

    public OffsetDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return (getName() +  " -- " + getAge());
    }


    @Override
    public int hashCode() {
        return Objects.hash(name, age, type);
    }

}
