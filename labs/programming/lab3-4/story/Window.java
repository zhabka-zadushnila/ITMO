package story;

public class Window  extends Furniture implements Interactive {
    
    Time timeOutside;

    public Window(Time timeOutside){
        super("Окно");
        this.timeOutside = timeOutside;
    }

    public Window( String description, Time timeOutside){
        super("Окно", description);
        this.timeOutside = timeOutside;
    }

    public void Interact(AbstractCreature creature){
        System.out.println(creature.getName() + " смотрит в окно.");
        if(creature.getState() == StateOf.AWAKE){
            if(this.timeOutside == Time.NIGHT){
                if(creature instanceof Korotyshka){
                    ((Korotyshka) creature).SuggestState(StateOf.ASLEEP);
                }
                creature.ChangeState(StateOf.ASLEEP);
            }
        }

    }
    
}
