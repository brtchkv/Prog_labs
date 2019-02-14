public class Vegetation extends God implements Vegable {
    private int id;

    public Vegetation(String name, int age){
        super(name, age, CreatureType.Vegetation);
    }

    public void getWet(){
        System.out.println("Thanks God, I won't drought");
    }

    public void rustle(){
        System.out.printf("Sh Sh Sh Sh Sh");
    }

    public boolean equals(Object other) {
        if(!super.equals(other)) return false;
        if (this == other) return true;
        if (other == null) return false;
        if(this.getClass() != other.getClass()) return false;
        Vegetation otherObj = (Vegetation) other;
        return this.id == otherObj.id;
    }


}
