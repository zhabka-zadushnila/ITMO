package story;
import java.util.ArrayList;

public class DescriptionCollector {
    private ArrayList<Descriable> describeableList = new ArrayList<>();
    
    DescriptionCollector(){}
    DescriptionCollector(ArrayList<Descriable> describeableList){
        this.describeableList = describeableList;
    }
    
    public void addDescriable(Descriable describeable){
        this.describeableList.add(describeable);
    }

    public ArrayList<Descriable> getDescribeableList(){
        return this.describeableList;
    }

    public void addDescriableList(ArrayList<Descriable> describeableList){
        this.describeableList.addAll(describeableList);
    }

    public String collectDescriptions(){
        System.out.println(this.getDescribeableList().size());
        String returnString = "";
        for (var describeable : this.describeableList){
            System.out.println(describeable.Describe() );
            returnString += describeable.Describe() + " \n";
        }
    return returnString;
    }
    static public String collectDescriptions(ArrayList<Descriable> describeableList){
        String returnString = "";
        for (var describeable : describeableList){
            returnString += describeable.Describe() + " \n";
        }
    return returnString;
    }
    


}
