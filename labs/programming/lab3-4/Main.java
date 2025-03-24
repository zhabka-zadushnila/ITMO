import java.util.ArrayList;

import story.*;


public class Main {

    public static void main(String[] args) {

        
        Furniture window = new Window("Обычное окно, кажется через него можно увидеть, что сейчас ночь.",  Time.NIGHT);
        Furniture painting = new Furniture("Картина", "Висящая на стене картина в чёрной широкой раме. Кажется она висит напротив кровати.");
        Furniture unknown = new Furniture("Неизвестный предмет", "предмет чьи очертания весьма размыты и едва ли поддаются описанию");
        Furniture bed1 = new Bed("Кровать1");
        Furniture bed2 = new Bed("Кровать2");

        Furniture bed3 = new Bed("Кровать3");
        Furniture shelf = new Furniture("Тумбочка", "Необыкновенная тумбочка с ровной стенкой вместо дверцы, сплошь усеянной маленькими белыми кнопками. Возле каждой кнопки имелась надпись с названием какой-нибудь сказки. Тут были и \"Красная Шапочка\", и \"Мальчик с пальчик\", и \"Золотой петушок\", и \"Котофей Котофеевич\". Сверху на тумбочке стояло зеркало.");

        Furniture[] mainLocFurnitures = {window};


        Location mainLocation = new Location("Главная комната", mainLocFurnitures);

        Furniture[] bedroom1Furnitures = {bed1};

        Location bedroom1 = new Location("Спальня 1", bedroom1Furnitures, true);

        Furniture[] bedroom2Furnitures = {bed2, bed3, painting, unknown, shelf};

        Location bedroom2 = new Location("Спальня 2", bedroom2Furnitures, true);

        mainLocation.addNeighbour(bedroom1);
        mainLocation.addNeighbour(bedroom2);

        Korotyshka knopochka = new Korotyshka("Кнопочка", "Типичная коротышка, woman", mainLocation);
        

        

        Korotyshka pestrenky = new Korotyshka("Пестренький", "Ничем не отличается от других коротышек, кроме, кажется, пёстрого цвета", mainLocation);
        

        Korotyshka neznaika = new Korotyshka("Незнайка", "О нём мало что известно, даже он ничего о себе не знает", mainLocation);
        neznaika.addThought("Казалось, мы выехали из цветочного города не сегодня и не вчера, а давным давно");
        
        Korotyshka[] korotyshky = {knopochka, neznaika, pestrenky};

        Storyteller storyteller = new Storyteller(korotyshky);
        
        storyteller.addLocation(bedroom1);
        storyteller.addLocation(bedroom2);



        storyteller.start();
        
    }
}