import javax.swing.*;
import java.awt.*;
import java.util.List;


public class RaceTrackPanel extends JPanel {
    private List<Horse> horses;
    public int raceTrackDistance = 0;
    private int numberOfLanes;


    public RaceTrackPanel(int width, int height, int numberOfLanes, int raceTrackDistance) {
        this.numberOfLanes = numberOfLanes;
        this.raceTrackDistance = raceTrackDistance;
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.black);
    }

    public void setNumberOfLanes(int numberOfLanes) {
        this.numberOfLanes = numberOfLanes;
        repaint();
    }

    public void setHorses(List<Horse> horses) {
        this.horses = horses;
        repaint();
    }

    public void setRaceTrackDistance(int raceTrackDistance) {
        this.raceTrackDistance = raceTrackDistance;
        repaint();
    }

    public void clearTrack() {
        if (horses != null) {
            for (Horse horse : horses) {
                horse.goBackToStart();  
            }
        }
        repaint(); 
    }

    private void drawHorse(Graphics2D g2, Horse horse, int trackWidth) {
        int laneIndex = horses.indexOf(horse); 
        if (laneIndex >= numberOfLanes) {
            return; 
        }
        int laneHeight = getHeight() / horses.size();
        int x = (int) (trackWidth * horse.getDistanceTravelled() / raceTrackDistance); 
        int y = laneHeight * horses.indexOf(horse) + laneHeight / 2;
    
        g2.setColor(Color.BLACK); 
        if (horse.hasFallen()) {
            g2.setColor(Color.red);
            g2.drawString("x", x, y);  
        } else {
            g2.setColor(Color.blue);
            g2.drawString(String.valueOf(horse.getSymbol()), x, y);  
        }
    
        String info = horse.getName() + " (Confidence: " + String.format("%.2f", horse.getConfidence()) + ")";
        g2.drawString(info, trackWidth + 10, y);  
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;  
    
        int trackWidth = getWidth() - 200; 
    
        g2.setStroke(new BasicStroke(3));  
    
        int laneHeight = getHeight() / numberOfLanes;
    
        for (int i = 0; i < numberOfLanes; i++) {
            int y = laneHeight * i;
            g2.setColor(Color.GRAY);
            g2.fillRect(0, y, trackWidth, laneHeight);
            g2.setColor(Color.black);
            g2.drawRect(0, y, trackWidth, laneHeight - 1);
        }
    
        g2.setColor(Color.RED);
        g2.drawLine(1, 0, 1, getHeight());  
        g2.drawLine(trackWidth +5, 0, trackWidth + 5, getHeight());  
    
        g2.setStroke(new BasicStroke(1));
    
        if (horses != null) {
            for (Horse horse : horses) {
                if (horses.indexOf(horse) < numberOfLanes) {
                    drawHorse(g2, horse, trackWidth);
                }
        }
    }
    
}  
}
