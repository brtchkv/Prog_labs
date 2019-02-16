public class Bird extends God{
    private int id;

    public Bird(String name){
        super(name, CreatureType.Bird);
    }

    public boolean equals(Object other) {
        if(!super.equals(other)) return false;
        if (this == other) return true;
        if (other == null) return false;
        if(this.getClass() != other.getClass()) return false;
        Bird otherObj = (Bird) other;
        return this.id == otherObj.id;
    }

    public void attack(){
        System.out.println("������� ������� � �������� ������� ������ ����� � ����");
        System.out.println("- ����! - �������� ������� " + getName());
        System.out.println("- �� �� ����!");
    }

    public void repeat(){
        System.out.println("���� - ���� � ����! - ��������� " + getName() + ", �� ��� �� ��� ��������");
    }

    public void addSome(){
        System.out.println("* ����� ����� *");
        System.out.println("- ���� ������ � �� ������������� - � ��� ���. �� ��� �� ���������!");
    }

    public void ignore(){
        System.out.println("- ����� �������� ���������, ������ ������ ���������, ������� ����� ���������,\n" +
                "- �� ������ �����, ���������� "+getName()+",- �� ��� ����! ��������� �����!");
    }

}
