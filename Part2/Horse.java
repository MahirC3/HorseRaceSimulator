
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * A class that holds details about a Horse object, such as totaldistance covered and the amount of wins etc.
 * 
 * @author Mohammed Mahir Uddin Choudhury
 * @version 220286617
 */

public class Horse
{

    private static final Random RANDOM = new Random();

    private String name;
    private char symbol;
    private int distanceTravelled;
    private boolean fallen;
    private double confidence;
    private double totalTime;
    double raceTime;
    private long startTime;

    private List<Double> raceTimes = new ArrayList<>(); 
    private int racesWon = 0;
    int totalDistanceTravelled = 0;

    
    //Constructor of class Horse
    /**
     * Constructor for objects of class Horse
     */

     
    public Horse(char horseSymbol, String horseName, double horseConfidence)
    {
       this.symbol = horseSymbol;
       this.name = horseName;
       this.confidence = horseConfidence;
       this.fallen = false; 
       this.distanceTravelled = 0; 
    }

    public static Horse createRandomHorse(int laneNumber) {
        String[] horseNames = {"KAREEM", "STALLION", "BLANCA", "BLACK", "MUSTANG", "BEAUTY", "SHIRE", "PONY", "AZTECA",
         "BEETLE", "TROTTER", "ANDALUSIAN", "WHITE", "ARABIAN", "KNIGHT", "NIGHT", "DARK", "LIGHT","SUNRISE", "VOLKS",};
        char symbol = (char) ('A' + laneNumber - 1); 
        String name = horseNames[RANDOM.nextInt(horseNames.length)] + " " + horseNames[RANDOM.nextInt(horseNames.length)];
        double confidence = 0.5 + (RANDOM.nextDouble() * 0.5); 
        return new Horse(symbol, name, confidence);
    }

    public void startRace() {
        this.startTime = System.currentTimeMillis();
    }

    public void addTime(double raceTime){
        this.raceTimes.add(raceTime);
    }

    public List<Double> getRaceTimes(){
        return raceTimes;
    }

    public int getRacesWon(){
        return racesWon;
    }

    public void incrementWins() {
        this.racesWon++; 
        System.out.println(racesWon);
    }

    public void fall()
    {
        this.fallen = true;
        long fallTime = System.currentTimeMillis();
        this.raceTime = (fallTime - startTime) / 1000.0; 
    }

    public void finishRace() {
        if (!hasFallen()) {
            long endTime = System.currentTimeMillis();
            this.raceTime = (endTime - startTime) / 1000.0; 
            totalTime += raceTime; 
        }
    }
    
    public int getTotalDist(){
        return totalDistanceTravelled;
    }
    
    public double getRaceTime() {
        return this.raceTime;
    }

    public double getConfidence()
    {
        return this.confidence;
    }
    
    public int getDistanceTravelled()
    {
        return this.distanceTravelled;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public double getTotalTime(){
        return totalTime;
    }

    public char getSymbol()
    {
        return this.symbol;
    }
    
    public void goBackToStart()
    {
        totalDistanceTravelled += distanceTravelled;
        System.out.println(totalDistanceTravelled);
        this.distanceTravelled = 0;
        this.fallen = false;
    }
    
    public boolean hasFallen()
    {
        return this.fallen;
    }

    public void moveForward()
    {
        if(!this.fallen){
            this.distanceTravelled += 1;
        }
    }

    public void setConfidence(double newConfidence)
    {
        this.confidence = newConfidence;
    }
    
    public void setSymbol(char newSymbol)
    {
        this.symbol = newSymbol;
    }
    
}
