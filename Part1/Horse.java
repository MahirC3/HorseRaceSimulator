/**
 *
 * Write a description of class Horse here.
 * 
 * @author 
 * @version 
 */

 public class Horse
 {
     //Fields of class Horse
     private String name;
     private char symbol;
     private int distanceTravelled;
     private boolean fallen;
     private double confidence;
 
     //Constructor of class Horse
     /**
      * Constructor for objects of class Horse
      */
     public Horse(char horseSymbol, String horseName, double horseConfidence)
     {
        this.symbol = horseSymbol;
        this.name = horseName;
        this.confidence = horseConfidence;
        this.fallen = false; //by default horse is ready to go at the start of race, not fallen
        this.distanceTravelled = 0; //dsitance is 0 at start
     }
 
     //Other methods of class Horse
     public void fall()
     {
         this.fallen = true;
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
     
     public char getSymbol()
     {
         return this.symbol;
     }
     
     public void goBackToStart()
     {
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