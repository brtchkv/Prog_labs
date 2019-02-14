public enum CreatureType implements Loveable {
    Human, Vegetation, NatureForce, Bird;

    @Override
    public void love(){
        System.out.println("Love is the answer!");
    }
}
