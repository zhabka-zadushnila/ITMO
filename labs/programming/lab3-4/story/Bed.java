package story;
public class Bed extends Furniture implements Interactive{
    public Bed(String name){
        super(name);
    }
    public Bed(String name, String description){
        super(name, description);
    }

    public void Interact(AbstractCreature creature){
        if(creature instanceof Korotyshka){
            ((Korotyshka) creature).sayThought();
        }
        else {
            System.out.println(creature.getName() + " пытается взаимодействовать с кроватью, но не может, ведь он - " + creature.getCreatureType() + ".");
        }
    }
}
