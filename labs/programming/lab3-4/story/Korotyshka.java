package story;
import java.util.ArrayList;
import java.util.Random;

public class Korotyshka extends AbstractCreature {

    ArrayList<String> thoughts = new ArrayList<String>();
    
    public Korotyshka(String name, String description, Location location){
        super(name, description, "Коротышка", location);
        location.addCreature(this);

    }
    public Korotyshka(String name, String description, Location location, StateOf inState){
        super(name, description, "Коротышка", location);
        this.InState = inState;
        location.addCreature(this);
    }

    public String getName(){
        return this.name;
    }
    
    public void addThought(String thought){
        thoughts.add(thought);
    }

    public void InvestigateLocation(Location location){
        System.out.println("Коротышка " + this.name + " осматривает " + location.getName() + " и натыкается на объект: ");
        /*for(Predmet predmet : location.getPredmets()){
            System.out.println("- " + predmet.getName());
        }*/
        Furniture[] objects = location.getFurnitures();
        if(objects.length == 0){
            System.out.println("Находится пустая комната.");
        }else{
            for(int i = 0; i < new Random().nextInt(objects.length); i++){
                int tmp_num = new Random().nextInt(objects.length);
                Furniture tmp_object = objects[tmp_num];
                System.out.println(tmp_object.Describe());
            }
        }
    }

    public void SuggestState(StateOf stateOf) throws NoSleepException {
        AbstractCreature[] CreaturesAtLocation = location.getCreatures(); 
        ArrayList<Location> bedrooms = new ArrayList<Location>();
        for (var loc: this.location.getNeighbours()){
            if (loc.containsBed()){
                bedrooms.add(loc);
            }
        }
        for(AbstractCreature creature : CreaturesAtLocation){
            if(!this.equals(creature)){
                System.out.println("Коротышка " + this.name + " убеждает " + creature.getName() + " поменять состояние на " + stateOf.name());
                if (creature.getLocation().containsBed()){
                    creature.ChangeState(stateOf);
                } else {
                    try{
                        if (bedrooms.isEmpty()){
                            throw new NoSleepException("Спать негде :(((    ");
                        } else {
                            creature.GoTo(bedrooms.get(new Random().nextInt(bedrooms.size())));
                            if (creature instanceof Korotyshka){
                                System.out.println("Перед сном у коротышки в голове проносятся мысли");
                                ((Korotyshka) creature).sayThought();
                                System.out.println("Перед сном Коротышка решает осмотреться, он видит:");
                                ((Korotyshka) creature).InvestigateLocation(creature.getLocation());
                            }
                            creature.ChangeState(stateOf);
                        }
                    }catch (NoSleepException e) {
                        System.out.println("Кажется коротышкам негде спать!");
                    }
                }
            }
        }
        if (this.getLocation().containsBed()){
            this.ChangeState(stateOf);
        } else {
            if (bedrooms.isEmpty()){
                throw new NoSleepException("Данному коротышке некуда идти");
            } else {
                this.GoTo(bedrooms.get(new Random().nextInt(bedrooms.size())));
                if (this instanceof Korotyshka){
                    System.out.println("Перед сном у коротышки в голове проносятся мысли");
                    ((Korotyshka) this).sayThought();
                    System.out.println("Перед сном Коротышка решает осмотреться, он видит:");
                    ((Korotyshka) this).InvestigateLocation(this.getLocation());
                    
                    
                    
                }
                this.ChangeState(stateOf);
            }
        }

    }
    
    public void sayThought(){
        if (thoughts.isEmpty()){
            System.out.println("Коротышка " + this.name + " ни о чём не думает.");
        } else {
            System.out.println("Коротышка " + this.name + " думает: " + thoughts.get(new Random().nextInt(thoughts.size())));
        }
    }


    @Override
    public void Interact(AbstractCreature creature){
        System.out.println(this.name + " обнимает " + creature.getName());
    }

    public void InteractWithInteractive(Interactive obj){
        obj.Interact(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Korotyshka)) return false;
        Korotyshka korotysh = (Korotyshka) obj;
        return this.name.equals(korotysh.name);
    }

}
