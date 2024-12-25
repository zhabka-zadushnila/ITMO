package story;

import java.util.ArrayList;

public class Location implements Descriable{
    String name;
    String description;
    Furniture[] furnitures;
    AbstractCreature[] creatures ={};
    boolean containsBed = false;
    ArrayList<Location> neighbours = new ArrayList<Location>();

    public Location(String name, Furniture[] furnitures) {
        this.name = name;
        this.furnitures = furnitures;
    }
    public Location(String name, Furniture[] furnitures, boolean containsBed) {
        this.name = name;
        this.furnitures = furnitures;
        this.containsBed = containsBed;
    }
    public Location(String name, Furniture[] furnitures, ArrayList<Location> neighbours) {
        this.name = name;
        this.furnitures = furnitures;
        this.neighbours.addAll(neighbours);
        for (Location l : neighbours){
            if(l.getNeighbours() == null){
                l.addNeighbour(l);
            } else {
                for (Location loc : l.getNeighbours()){
                    if(loc != this){
                        l.addNeighbour(this);
                    }
                }
            }
        }
    }
    public Location(String name, Furniture[] furnitures, boolean containsBed, ArrayList<Location> neighbours) {
        this.name = name;
        this.furnitures = furnitures;
        this.containsBed = containsBed;
        this.neighbours.addAll(neighbours);
        for (Location l : neighbours){
            if(l.getNeighbours() == null){
                l.addNeighbour(l);
            } else {
                for (Location loc : l.getNeighbours()){
                    if(loc != this){
                        l.addNeighbour(this);
                    }
                }
            }
        }
    }
    

    public String getName() {
        return name;
    }

    public Furniture[] getFurnitures() {
        return this.furnitures;
    }

    public ArrayList<Location> getNeighbours() {
        return this.neighbours;
    }

    public void addNeighbour(Location l) {
        this.neighbours.add(l);
        System.out.println("something");
    }


    public void addCreature(AbstractCreature creature) {
        this.creatures = appendCreatures(creatures, creature);
    }

    public boolean containsBed() {
        return this.containsBed;
    }

    public AbstractCreature[] getCreatures() {
        return this.creatures;
    }
    
    public String Describe() {
        DescriptionCollector collector = new DescriptionCollector();
        for (AbstractCreature creature : this.creatures) {
            collector.addDescriable(creature);
        }

        for (Furniture furniture : this.furnitures) {
            collector.addDescriable(furniture);
        }

        return collector.collectDescriptions();
        
    }

    public void deleteCreature(AbstractCreature creature) {
        AbstractCreature[] newCreatures = new AbstractCreature[this.creatures.length - 1];
        int index = 0;
        for(AbstractCreature c : this.creatures) {
            if(c!= creature) {
                newCreatures[index++] = c;
            }
        }
        this.creatures = newCreatures;
    }

    

    private AbstractCreature[] appendCreatures(AbstractCreature[] creatures, AbstractCreature creature) {
        AbstractCreature[] newCreatures = new AbstractCreature[creatures.length + 1];
        for(int i = 0; i < creatures.length; i++) {
            newCreatures[i] = creatures[i];
        }
        newCreatures[creatures.length] = creature;
        return newCreatures;

    }

}


