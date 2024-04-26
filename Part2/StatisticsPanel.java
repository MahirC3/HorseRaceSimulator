import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.List;

import javax.swing.table.DefaultTableModel;

public class StatisticsPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;

    public StatisticsPanel() {
        setLayout(new BorderLayout());
        String[] columnNames = {
            "Horse", "Time (s)", "Winner", 
            "Total Distance Traveled (m)", "Total Wins", "Average Speed (m/s)"
        };        
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);  
        table.setBackground(Color.black);
        table.setForeground(Color.white);
        add(new JScrollPane(table), BorderLayout.CENTER); 
    } 

    public void updateStatistics(List<RaceResult> raceResults) {
        tableModel.setRowCount(0); 
        for (RaceResult result : raceResults) {
            for (Map.Entry<Horse, Double> entry : result.getFinishingTimes().entrySet()) {
                Horse horse = entry.getKey();
                double timeInSeconds = entry.getValue();
                int distance = result.getTotalDist().get(horse);
                double tTotal = result.getTotalTime().get(horse);
                double averageSpeed = 0;
                averageSpeed = distance / tTotal;  

                
                Object[] row = new Object[]{
                    horse.getName(),
                    String.format("%.2f", timeInSeconds),
                    result.isWinner(horse) ? "Yes" : "No",
                    distance,
                    result.getWinCount().get(horse),
                    String.format("%.2f", averageSpeed)  
                };
                tableModel.addRow(row);
            }
        }
    }
    
}
