public class testRace {

    public static void main(String[] args) {
        Horse horse1 = new Horse('a', "Stallion", 0.6);
        Horse horse2 = new Horse('b', "WhiteArabian", 0.8);
        Horse horse3 = new Horse('c', "YellowMustard", 0.3);

        Race race = new Race(10); 
        race.addHorse(horse1, 1);
        race.addHorse(horse2, 2);
        race.addHorse(horse3, 3);


        race.startRace();
    }
    
}
