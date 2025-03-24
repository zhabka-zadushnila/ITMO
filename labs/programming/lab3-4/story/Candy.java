package story;

public class Candy extends AbstractFood{
    private String covering;
    Candy( String description, String covering) {
        super("Конфета", description);
        this.covering = covering;
    } 

    @Override
    public void afterAction(AbstractCreature k) {
        System.out.println(k.getName() + " выбрасывает фантик с " + this.covering + " на пол." );
    }
}
