package story;
//Еду можно сделать абстрактным классом (есть общие и различающие черты)
abstract class AbstractFood {

    String name;
    String description= "None";
    String afterActionDescription = "None";

    public AbstractFood(String name){
        this.name = name;
    }
    public AbstractFood(String name, String description){
        this.name = name;
        this.description = description;
    }
    public AbstractFood(String name, String description, String afterActionDescription){
        this(name, description);
        this.afterActionDescription = afterActionDescription;
    }

    public String getName(){
        return this.name;
    }

    public void afterAction(AbstractCreature k) {
        if (afterActionDescription != null && !afterActionDescription.isEmpty()) {
            System.out.println(k.getName() + " " + this.afterActionDescription);
        }
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public String toString(){
        return this.name + ": [" + this.description + "]"; 
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Furniture)) return false;
        Furniture abstr = (Furniture) obj;
        return this.name.equals(abstr.name);
    }
}
