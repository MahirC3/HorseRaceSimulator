import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Statistics {
    private List<RaceResult> raceResults;

    public Statistics() {
        this.raceResults = new ArrayList<>();
    }

    public void addRaceResult(RaceResult result) {
        raceResults.add(result);
    }

    public List<RaceResult> getRaceResults(){
        return raceResults;
    }

    public String getStatisticsReport() {
        if (raceResults.isEmpty()) {
            return "No races have been completed yet.";
        }
    
        StringBuilder report = new StringBuilder("Race Statistics Summary:\n");
        report.append(String.format("%-30s %-20s %-10s %-30s %-15s\n", "Horse", "Finishing Time (s)", "Winner", "Total Distance Traveled (m)", "Total Wins"));
    
        Map<Horse, Integer> totalDistances = new HashMap<>();
        Map<Horse, Integer> winCounts = new HashMap<>();
    
        for (RaceResult result : raceResults) {
            for (Map.Entry<Horse, Double> entry : result.getFinishingTimes().entrySet()) {
                Horse horse = entry.getKey();
                totalDistances.put(horse, totalDistances.getOrDefault(horse, 0) + result.getTotalDist().get(horse)); 
                winCounts.put(horse, winCounts.getOrDefault(horse, 0) + (result.isWinner(horse) ? 1 : 0));
            }
        }
    
        for (RaceResult result : raceResults) {
            for (Map.Entry<Horse, Double> entry : result.getFinishingTimes().entrySet()) {
                Horse horse = entry.getKey();
                Double time = entry.getValue();
                report.append(String.format("%-30s %-20.2f %-10s %-30d %-15d\n",
                    horse.getName(),
                    time,
                    result.isWinner(horse) ? "Yes" : "No",
                    totalDistances.get(horse),
                    winCounts.get(horse)));
            }
        }
    
        return report.toString();
    }
    
    
}
