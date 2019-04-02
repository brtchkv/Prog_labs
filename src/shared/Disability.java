package shared;

import java.io.Serializable;
import java.util.Objects;

public class Disability implements Serializable {
    private String disability;

    public Disability(String disability){
        this.disability = disability;
    }

    public String getName(){ return this.disability; }

    public String toString(){
        return this.getName();
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Disability that = (Disability) o;
        return Objects.equals(disability, that.disability);
    }

    @Override
    public int hashCode() {
        return Objects.hash(disability);
    }
}
