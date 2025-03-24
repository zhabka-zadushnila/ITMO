package story;

public class Pizza extends AbstractFood {
    private int slices = 8;
    Pizza( String description, int slices) {
        super("Пицца", description);
        this.slices = slices;
    } 

    @Override
    public void afterAction(AbstractCreature k) {
        System.out.println(k.getName() + " съедает кусок пиццы, осталось " + --slices);
    }
}
