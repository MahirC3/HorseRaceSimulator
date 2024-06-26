import java.util.concurrent.TimeUnit;
import java.lang.Math;


//IMPORTED TO USE LIST
import java.util.List;
import java.util.ArrayList;

/**
 * A three-horse race, each horse running in its own lane
 * for a given distance
 * 
 * @author McFarewell
 * @version 1.0
 */
public class Race
{
    private int raceLength;
    private Horse lane1Horse;
    private Horse lane2Horse;
    private Horse lane3Horse;

    //ARRAY LIST TO HOLD WINNERS
    private List<Horse> winners = new ArrayList<>(); // List to keep track of winners


    /**
     * Constructor for objects of class Race
     * Initially there are no horses in the lanes
     * 
     * @param distance the length of the racetrack (in metres/yards...)
     */
    public Race(int distance)
    {
        // initialise instance variables
        raceLength = distance;
        lane1Horse = null;
        lane2Horse = null;
        lane3Horse = null;
    }
    
    /**
     * Adds a horse to the race in a given lane
     * 
     * @param theHorse the horse to be added to the race
     * @param laneNumber the lane that the horse will be added to
     */
    public void addHorse(Horse theHorse, int laneNumber)
    {
        if (laneNumber == 1)
        {
            lane1Horse = theHorse;
        }
        else if (laneNumber == 2)
        {
            lane2Horse = theHorse;
        }
        else if (laneNumber == 3)
        {
            lane3Horse = theHorse;
        }
        else
        {
            System.out.println("Cannot add horse to lane " + laneNumber + " because there is no such lane");
        }
    }
    
    /**
     * Start the race
     * The horse are brought to the start and
     * then repeatedly moved forward until the 
     * race is finished
     */
    public void startRace()
    {
        //declare a local variable to tell us when the race is finished
        boolean finished = false;
        
        //reset all the lanes (all horses not fallen and back to 0). 
        lane1Horse.goBackToStart();
        lane2Horse.goBackToStart();
        lane3Horse.goBackToStart();

        //BEFORE WE ENTER THE WHILE LOOP WE DECLARE THAT NONE OF THE HORSES HAVE WON YET
        boolean lane1Won = false;
        boolean lane2Won = false;
        boolean lane3Won = false;
        
                      
        while (!finished)
        {

            //ADDED THIS CHECK TO END RACE ONCE ALL HORSES HAVE FALLEN.
            if(lane1Horse.hasFallen() && lane2Horse.hasFallen() && lane3Horse.hasFallen()){
                finished = true;
                printResults();
                return;
            }

            //move each horse
            moveHorse(lane1Horse);
            moveHorse(lane2Horse);
            moveHorse(lane3Horse);
                        
            //print the race positions
            printRace();
            
            //UPDATE EACH BOOLEAN VARIABLE TO HOLD WHETHER THE HORSE HAS WON OR NOT
            lane1Won = raceWonBy(lane1Horse);
            lane2Won = raceWonBy(lane2Horse);
            lane3Won = raceWonBy(lane3Horse);

            //if any of the three horses has won the race is finished
            //IF STATEMENT CHECKS TO SEE IF ANY OF THE HORSEWON BOOLEANS ARE SET TO TRUE
            if ( lane1Won || lane2Won || lane3Won )
            {
                finished = true;
                //MUST RETURN TO ENSURE HORSES DON'T MOVE FORWARD ONCE MORE
                //CALL PRINT RESULTS METHOD
                printResults();
                return;
            }
           
            //wait for 100 milliseconds
            try{ 
                TimeUnit.MILLISECONDS.sleep(100);
            }catch(Exception e){}
        }
    }
    
    /**
     * Randomly make a horse move forward or fall depending
     * on its confidence rating
     * A fallen horse cannot move
     * 
     * @param theHorse the horse to be moved
     */
    private void moveHorse(Horse theHorse)
    {
        //if the horse has fallen it cannot move, 
        //so only run if it has not fallen
        
        if  (!theHorse.hasFallen())
        {
            //the probability that the horse will move forward depends on the confidence;
            if (Math.random() < theHorse.getConfidence())
            {
               theHorse.moveForward();
            }
            
            //the probability that the horse will fall is very small (max is 0.1)
            //but will also will depends exponentially on confidence 
            //so if you double the confidence, the probability that it will fall is *2
            if (Math.random() < (0.1*theHorse.getConfidence()*theHorse.getConfidence()))
            {
                theHorse.fall();
            }
        }
    }
        
    /** 
     * Determines if a horse has won the race
     *
     * @param theHorse The horse we are testing
     * @return true if the horse has won, false otherwise.
     */
    private boolean raceWonBy(Horse theHorse)
    {
        if (theHorse.getDistanceTravelled() >= raceLength)
        {
            //ENSURES THAT HORSE ISN'T ADDED TWICE TO SAME LIST
            if(!winners.contains(theHorse)){
                //ADD HORSE TO LIST
                winners.add(theHorse);
            }
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /***
     * Print the race on the terminal
     */
    private void printRace()
    {
        System.out.print('\u000C');  //clear the terminal window
        
        multiplePrint('=',raceLength+3); //top edge of track
        System.out.println();
        
        printLane(lane1Horse);
        System.out.println();
        
        printLane(lane2Horse);
        System.out.println();
        
        printLane(lane3Horse);
        System.out.println();
        
        multiplePrint('=',raceLength+3); //bottom edge of track
        System.out.println();    
    }
    
    /**
     * print a horse's lane during the race
     * for example
     * |           X                      |
     * to show how far the horse has run
     */
    private void printLane(Horse theHorse)
    {
        //calculate how many spaces are needed before
        //and after the horse
        int spacesBefore = theHorse.getDistanceTravelled();
        int spacesAfter = raceLength - theHorse.getDistanceTravelled();
        
        //print a | for the beginning of the lane
        System.out.print('|');
        
        //print the spaces before the horse
        multiplePrint(' ',spacesBefore);
        
        //if the horse has fallen then print dead
        //else print the horse's symbol
        if(theHorse.hasFallen())
        {
         //   System.out.print('\u2322');
            //PRINTED X TEMPORRALIY AS SYMBOL DOESNT SHOW ON CONSOLE
            //ACCIDENTLY USED PRINTLN INSTEAD OF PRINT - AS A RESULT THE NAME AND CONFIDENCE RATING WENT TO NEXT LINE
            System.out.print('X');
        }
        else
        {
            System.out.print(theHorse.getSymbol());
        }
        
        //print the spaces after the horse
        multiplePrint(' ',spacesAfter);
        
        //print the | for the end of the track
        //ADDED NAME AND CONFIDENCE PRINT
        System.out.print('|' + " " + theHorse.getName() + " Current Confidence: (Current Confidence: " + theHorse.getConfidence() + ")");
    }
    
    //NEW METHOD TO PRINT RESULTS
    private void printResults() {
        if (winners.isEmpty()) {
            System.out.println("No winners! All horses have fallen!");
        } else {
            System.out.println("The winner(s) of the race:");
            for (Horse winner : winners) {
                System.out.println(winner.getName() + " (Confidence: " + winner.getConfidence() + ")");
            }
        }
    }

    /***
     * print a character a given number of times.
     * e.g. printmany('x',5) will print: xxxxx
     * 
     * @param aChar the character to Print
     */
    private void multiplePrint(char aChar, int times)
    {
        int i = 0;
        while (i < times)
        {
            System.out.print(aChar);
            i = i + 1;
        }
    }
}