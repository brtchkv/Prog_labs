public class NatureForce extends God {
    private int id;

    public NatureForce(){
        super("wind", CreatureType.NatureForce);
    }

    public static void stopHuman(){
        System.out.println("* ������ ��������� ����� �� ��� *");
    }
    public static void vibrate(){
        System.out.println("� ����� � ��������� �� ����� ������ ������ �� ������.");
    }

    public boolean equals(Object other) {
        if(!super.equals(other)) return false;
        if (this == other) return true;
        if (other == null) return false;
        if(this.getClass() != other.getClass()) return false;
        NatureForce otherObj = (NatureForce) other;
        return this.id == otherObj.id;
    }

}
