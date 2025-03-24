package story;

public class Furniture implements Descriable {
    String name = "Unknown";
    String description = "Unknown";
    static int Num = 0;


    public Furniture(String name) {
        this.name = name;
        this.description = "описания нет";
    }
    public Furniture(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String Describe(){
        return this.name + ", " + this.description;
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
