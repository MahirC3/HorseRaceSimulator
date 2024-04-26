import java.util.HashMap;
import java.util.Map;

public class RaceResult {
    private final Map<Horse, Double> finishingTimes; 
    private final Map<Horse, Boolean> winners; 
    private final Map<Horse, Integer> totalDistanceOfHorse;
    private final Map<Horse, Integer> totalWins;
    private final Map<Horse, Double> totalTime;
    


    public RaceResult() {
        this.finishingTimes = new HashMap<>();
        this.winners = new HashMap<>();
        this.totalDistanceOfHorse = new HashMap<>();
        this.totalWins = new HashMap<>();
        this.totalTime = new HashMap<>();
    }

    public void recordResult(Horse horse, double time, boolean isWinner, int totalDistanceTravelled, int winCount) {
        finishingTimes.put(horse, time);
        winners.put(horse, isWinner);
        totalDistanceOfHorse.put(horse, totalDistanceTravelled);
        totalWins.put(horse, winCount);
        totalTime.put(horse, horse.getTotalTime());
    }

    public Map<Horse, Double> getTotalTime() {
        return totalTime;
    }

    public Map<Horse, Double> getFinishingTimes() {
        return finishingTimes;
    }

    public Map<Horse, Integer> getTotalDist() {
        return totalDistanceOfHorse;
    }

    public Map<Horse, Integer> getWinCount(){
        return totalWins;
    }

    public boolean isWinner(Horse horse) {
        return winners.getOrDefault(horse, false);
    }
}
