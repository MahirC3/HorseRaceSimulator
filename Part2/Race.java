import java.util.concurrent.TimeUnit;
import java.lang.Math;

//IMPORTED TO USE LIST
import java.util.List;
import java.util.ArrayList;
import javax.swing.SwingUtilities;


/**
 * A three-horse race, each horse running in its own lane
 * for a given distance
 * 
 * @author McFarewell
 * @version 1.0
 */
public class Race
{
    RaceListener listener;
    public int raceLength;

    Statistics statistics;

    private List<Horse> horsesInRace = new ArrayList<>();

    //ARRAY LIST TO HOLD WINNERS
    private List<Horse> winners = new ArrayList<>();

    private RaceTrackPanel raceTrackPanel; 

    public void setRaceTrackPanel(RaceTrackPanel panel) {
        this.raceTrackPanel = panel;
    }

    public void setRaceListener(RaceListener listener) {
        this.listener = listener;
    }

    /**
     * Constructor for objects of class Race
     * Initially there are no horses in the lanes
     * 
     * @param distance the length of the racetrack (in metres/yards...)
     */
    public Race(int distance)
    {
        raceLength = distance;

    }
    
    /**
     * Adds a horse to the race in a given lane
     * 
     * @param theHorse the horse to be added to the race
     * @param laneNumber the lane that the horse will be added to
     */
    public void addHorse(Horse theHorse, int laneNumber)
    {
        horsesInRace.add(theHorse);
    }

    public void resetRace() {
        for (int i = 0; i < horsesInRace.size(); i++) 
        {
            if (horsesInRace.get(i) != null) horsesInRace.get(i).goBackToStart();
        }

        winners.clear();
    }
    
    public List<Horse> getHorses() {
        return horsesInRace;
    }

    public void removeExcessHorses(int numberOfTracks) {
        while (horsesInRace.size() > numberOfTracks) {
            horsesInRace.remove(horsesInRace.size() - 1);
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
        boolean finished = false;
        
        

        for (int i = 0; i < horsesInRace.size(); i++) 
        {
            if (horsesInRace.get(i) != null) horsesInRace.get(i).goBackToStart();
        }


        boolean existsWinner = false;

        for (Horse horse : horsesInRace) {
            horse.startRace();
        }

        while (!finished)
        {

            for (int i = 0; i < horsesInRace.size(); i++) 
            {
                if (raceWonBy(horsesInRace.get(i))){
                    existsWinner = true;
                };
            }

            if(allHorsesHaveFallen()){
                endRace();
                finished = true;
                printResults();
                return;            
            }


            //IF STATEMENT CHECKS TO SEE IF ANY OF THE HORSEWON BOOLEANS ARE SET TO TRUE
            if ( existsWinner )
            {
                finished = true;
                endRace();
                //MUST RETURN TO ENSURE HORSES DON'T MOVE FORWARD ONCE MORE
                //CALL PRINT RESULTS METHOD
                printResults();
                return;
            }

            for (int i = 0; i < horsesInRace.size(); i++) 
            {
                moveHorse(horsesInRace.get(i));
            }
                        
            printRace();
            
           
            try{ 
                TimeUnit.MILLISECONDS.sleep(100);
            }catch(Exception e){}

            SwingUtilities.invokeLater(() -> {
                if (raceTrackPanel != null) {
                    raceTrackPanel.setHorses(horsesInRace);
                    raceTrackPanel.repaint();  

                }
            });

            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void setStatistics(Statistics inpStatistics){
        statistics = inpStatistics;
    }
    
    public void endRace() {
        RaceResult result = new RaceResult();
        for (Horse horse : horsesInRace) {
            double raceTime = horse.getRaceTime(); 
            System.out.println(raceTime);
            boolean isWinner = horse.getDistanceTravelled() >= raceLength;
            result.recordResult(horse, raceTime, isWinner, horse.getTotalDist(), horse.getRacesWon());
        }
        statistics.addRaceResult(result);
    }

    

    public boolean allHorsesHaveFallen() {
        for (Horse horse : horsesInRace) {
            if (!horse.hasFallen()) {
                return false; 
            }
        }
        return true; 
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

        
        if  (!theHorse.hasFallen())
        {
            if (Math.random() < theHorse.getConfidence())
            {
               theHorse.moveForward();
            }
            
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
        if (theHorse != null && theHorse.getDistanceTravelled() == raceLength)
        {
            //ENSURES THAT HORSE ISN'T ADDED TWICE TO SAME LIST
            theHorse.finishRace();
            theHorse.incrementWins();
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
        System.out.print('\u000C'); 
        
        multiplePrint('=',raceLength+3); 
        System.out.println();
        
        for (int i = 0; i < horsesInRace.size(); i++) 
        {
            printLane(horsesInRace.get(i));
        }
        
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

        int spacesBefore = theHorse.getDistanceTravelled();
        int spacesAfter = raceLength - theHorse.getDistanceTravelled();
        
        System.out.print('|');
        
        multiplePrint(' ',spacesBefore);
        
        if(theHorse.hasFallen())
        {
            //PRINTED X TEMPORRALIY AS SYMBOL DOESNT SHOW ON CONSOLE
            //ACCIDENTLY USED PRINTLN INSTEAD OF PRINT - AS A RESULT THE NAME AND CONFIDENCE RATING WENT TO NEXT LINE
            System.out.print('X');
        }
        else
        {
            System.out.print(theHorse.getSymbol());
        }
        
        multiplePrint(' ',spacesAfter);
        
        //ADDED NAME AND CONFIDENCE PRINT
        System.out.print('|' + " " + theHorse.getName() + " Current Confidence: (Current Confidence: " + theHorse.getConfidence() + ")");
    }
    
    //NEW METHOD TO PRINT RESULTS
    private void printResults() {
        if (winners.isEmpty()) {
            System.out.println("No winners! All horses have fallen!");
            if (listener != null) {
                listener.onRaceFinished("No winners! All horses have fallen!");
            }
        } else {
            StringBuilder winnersAnnouncement = new StringBuilder(" ");
            for (int i = 0; i < winners.size(); i++) {
                Horse winner = winners.get(i);
                winnersAnnouncement.append(winner.getName());
                if (i < winners.size() - 1) {
                    winnersAnnouncement.append(", ");
                }
            }
            System.out.println(winnersAnnouncement);
            if (listener != null) {
                listener.onRaceFinished(winnersAnnouncement.toString());
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
