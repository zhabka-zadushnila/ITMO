package story;

public enum StateOf {
    ASLEEP ("бодрствование"),
    AWAKE ("сон");

    private String name;

    StateOf(String name) {
       this.name = name;
    }

    public String getName() {
        return this.name;
    }



}
