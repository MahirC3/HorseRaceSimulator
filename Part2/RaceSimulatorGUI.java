import javax.swing.*;
import java.awt.*;

public class RaceSimulatorGUI extends JFrame implements RaceListener {
    private RaceTrackPanel raceTrackPanel;
    public int raceTrackDistance = 10;
    private JLabel winnerLabel; 
    private JButton startButton;
    private JButton restartButton;
    private Race race;
    private TrackCustomisationPanel trackCustomisationPanel;
    private JButton customiseTrackButton;
    private JPanel buttonPanel; 
    private Statistics statistics;
    private StatisticsPanel statisticsPanel;
    private JButton showStatsButton;    

    


    public RaceSimulatorGUI() {
        super("Horse Race Simulator");
        initaliseGui();    
    }


    

    private void initaliseGui() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.BLACK); 

        raceTrackPanel = new RaceTrackPanel(800, 150, 3, raceTrackDistance);
        add(raceTrackPanel, BorderLayout.CENTER);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        winnerLabel = new JLabel("Winner(s): None", SwingConstants.CENTER);
        winnerLabel.setFont(new Font("Arial", Font.BOLD, 40));
        add(winnerLabel, BorderLayout.SOUTH); 

        buttonPanel = new JPanel();
        startButton = new JButton("Start Race");
        restartButton = new JButton("Restart Race");
        restartButton.setEnabled(false);  

        buttonPanel.add(startButton);
        buttonPanel.add(restartButton);
    
        startButton.addActionListener(e -> startRace());
        restartButton.addActionListener(e -> restartRace());

        customiseTrackButton = new JButton("Customise Track");
        customiseTrackButton.addActionListener(e -> openTrackCustomisationDialog());
        buttonPanel.add(customiseTrackButton);

        this.statistics = new Statistics(); 
        this.statisticsPanel = new StatisticsPanel();
        
        showStatsButton = new JButton("Show Statistics");
        showStatsButton.addActionListener(e -> SwingUtilities.invokeLater(this::updateStatisticsDisplay));

        buttonPanel.add(showStatsButton);

        add(statisticsPanel, BorderLayout.EAST); 

        winnerLabel.setForeground(Color.green);
        add(buttonPanel, BorderLayout.NORTH);
        statisticsPanel.setBackground(Color.BLACK);
        buttonPanel.setBackground(Color.BLACK); 
 



        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        
    }


    public void updateStatisticsDisplay() {
        SwingUtilities.invokeLater(() -> {
            statisticsPanel.updateStatistics(statistics.getRaceResults());
        });
    }

    private void openTrackCustomisationDialog() {
        if (trackCustomisationPanel == null) {
            trackCustomisationPanel = new TrackCustomisationPanel();
        }

        int result = JOptionPane.showConfirmDialog(this, trackCustomisationPanel, 
                "Customise Track", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            int numberOfTracks = trackCustomisationPanel.getNumberOfTracks();
            int trackLength = trackCustomisationPanel.getTrackLength();
            updateRaceTrack(numberOfTracks, trackLength);
        }
    }

    private void updateRaceTrack(int numberOfTracks, int trackLength) {
        setupRace();
        raceTrackPanel.setRaceTrackDistance(trackLength);

        if (numberOfTracks > race.getHorses().size()) {
            for (int i = race.getHorses().size() + 1; i <= numberOfTracks; i++) {
                
                race.addHorse(Horse.createRandomHorse(i), i);
            }
        }
        else if (numberOfTracks < race.getHorses().size()) {
            race.removeExcessHorses(numberOfTracks);
        }

        raceTrackPanel.setNumberOfLanes(numberOfTracks);
        raceTrackPanel.setHorses(race.getHorses()); 
    
        raceTrackPanel.repaint();
    
    }

    private void setupRace() {
        race = new Race(raceTrackDistance);
        race.setRaceListener(this);
        race.addHorse(new Horse('a', "PIPPI", 0.6), 1);
        race.addHorse(new Horse('b', "KOKOMO", 0.6), 2);
        race.addHorse(new Horse('c', "EL JEFE", 0.4), 3);
        raceTrackPanel.raceTrackDistance = this.raceTrackDistance;
        race.setRaceTrackPanel(raceTrackPanel);
    }

    private void startRace() {
        if (race == null) {
            setupRace();  
        }
        race.setStatistics(statistics);
        startButton.setEnabled(false);
        restartButton.setEnabled(true);
        new Thread(race::startRace).start();
    }

    private void restartRace() {
        updateStatisticsDisplay();
        if(race !=null){
            race.resetRace();
        }
        raceTrackPanel.clearTrack();
        winnerLabel.setText("Winner: None");
        startButton.setEnabled(true);
        restartButton.setEnabled(false);
    }
    
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RaceSimulatorGUI::new);
    }

    public void displayWinner(String winnerText) {
        winnerLabel.setText(winnerText); 
    }

    @Override
    public void onRaceFinished(String winner) {
        SwingUtilities.invokeLater(() -> {
            winnerLabel.setText("Winner(s): " + winner);

        });
    }
}
