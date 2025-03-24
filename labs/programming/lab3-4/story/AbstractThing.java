package story;
//Точно на удаление
abstract class AbstractThing {
    String name = "Unknown";
    String description = "Unknown";
    static int Num = 0;
    public AbstractThing(String name) {
        this.name = name;
        Num++;
    }
    
    public String describe(){
        return this.description;
    }

    public String getName(){
        return this.name;
    }

    public AbstractThing(String name, String description) {
        this.name = name;
        this.description = description;

    }
}
