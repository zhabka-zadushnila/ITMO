package story;
//Поменять на абстрактный класс живого существа с базовыми общими возможностями и добавить некий признак имеющийся у всех, но так же различающийся
//(типа смены локации и т.п.) + добавить метод, который будет по разному реализовываться у каждого.
abstract class AbstractCreature implements Interactive, Descriable{
    String name;
    String creatureType = "unknown";
    String description = "unknown";
    Location location;
    StateOf InState = StateOf.AWAKE;

    public AbstractCreature(String name, String description, Location location) {
        this.name = name;
        this.description = description;
        this.location = location;
    }
    public AbstractCreature(String name, String description, String creatureType, Location location) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.creatureType = creatureType;
    }
    public String getName(){
        return this.name;
    }

    public Location getLocation(){
        return this.location;
    }

    public String getCreatureType(){
        return this.creatureType;
    }

    public StateOf getState(){
        return this.InState;
    }
    public void GoTo(Location location){
        System.out.println(this.creatureType + " " + this.name + " перемещается в " + location.getName());
        this.location.deleteCreature(this);  // удаляем из старой локации
        this.location = location;
        location.addCreature(this);
    }
    public void LookTo(AbstractThing thing){
        System.out.println(this.creatureType + " " + this.name + " смотрит на объект " + thing.getName()+ ". В голове проносится описание: " + thing.describe());
    }
    public void Eat(AbstractFood food){
        System.out.println(this.name + "только что съел " + food.getName());
        food.afterAction(this);
    }
    public void ChangeState(StateOf state){
        this.InState = state;
        if (state == StateOf.ASLEEP){
            System.out.println(this.name + " заснул.");
        }
        else if (state == StateOf.AWAKE){
            System.out.println(this.name + " проснулся.");
        }
        else{
            System.out.println(this.name + " изменил состояние на " + state.getName());
        }
    }

    public void Sleep(){
        this.InState = StateOf.ASLEEP;
        System.out.println(this.name + " засыпает.");
    }



    // to be overridden probably
    public void Interact(AbstractCreature creature){}
    public String Describe() {
        return "default_description";
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public String toString(){
        return "(" + this.creatureType + ") " + this.name + ": [" + this.description + "]"; 
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof AbstractCreature)) return false;
        AbstractCreature abstr = (AbstractCreature) obj;
        return this.name.equals(abstr.name);
    }


}
