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
        System.out.println("Яростно налетаю и стараюсь ударить крылом прямо в лицо");
        System.out.println("- Змея! - отчаянно кричала " + getName());
        System.out.println("- Ах ты змея!");
    }

    public void repeat(){
        System.out.println("Змея - змея и есть! - повторила " + getName() + ", но уже не так уверенно");
    }

    public void addSome(){
        System.out.println("* почти плачя *");
        System.out.println("- Чего только я не перепробовала - и все зря. На них не потрафишь!");
    }

    public void ignore(){
        System.out.println("- Корни деревьев пробовала, речные откосы пробовала, колючие кусты пробовала,\n" +
                "- не слушая Алисы, продолжала "+getName()+",- им все мало! Проклятые твари!");
    }

}
