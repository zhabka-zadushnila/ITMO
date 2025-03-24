package story;

import java.util.ArrayList;

public class Storyteller {
    Korotyshka[] korotyshkas;
    ArrayList<Location> locations = new ArrayList<>();

    public Storyteller(Korotyshka ... korotyshkas){
        this.korotyshkas = korotyshkas;
        //getting all the locations from korotyshkas
        for (Korotyshka korotyshka : korotyshkas){
            Location location = korotyshka.getLocation();
            if(! locations.isEmpty()){
                for (Location iterLocation : locations){
                    if (iterLocation == location){
                        continue;
                    }
                    locations.add(iterLocation);
                }
            } else {
                locations.add(location);
            }
        }
    }

    public void addLocation(Location location){
        this.locations.add(location);
    }

    public void start(){
        System.out.println("История началась:");
        SkatertSamobranka skatert = new SkatertSamobranka("Скатерть-самобранка");
        skatert.fold();
        Furniture[] startFurniture = locations.get(0).getFurnitures();
        for (Furniture furniture : startFurniture){
            if (furniture instanceof Interactive){
                korotyshkas[0].InteractWithInteractive((Interactive) furniture);
            }
        }

    }



}
