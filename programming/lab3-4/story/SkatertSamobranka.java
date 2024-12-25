package story;

public record SkatertSamobranka(String name) {

    public void fold(){
        System.out.println("Скатерть-самобранка свёрнута, еда с неё пропала");
    }
    public void unfold(){
        System.out.println("Скатерть-самобранка развёрнута");
    }


    
}
