package story;

public enum Time {
    NIGHT ("ночь"),
    DAY ("день");

    private String name;

    Time(String name) {
       this.name = name;
    }

    public String getName() {
        return this.name;
    }



}
