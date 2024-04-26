
import javax.swing.*;

public class TrackCustomisationPanel extends JPanel {
    private JSpinner numberOfTracksSpinner;
    private JSlider trackLengthSlider;

    public TrackCustomisationPanel() {
        numberOfTracksSpinner = new JSpinner(new SpinnerNumberModel(3, 2, 100, 1));
        add(new JLabel("Number of Tracks:"));
        add(numberOfTracksSpinner);
        
        trackLengthSlider = new JSlider(JSlider.HORIZONTAL, 10, 100, 10);
        trackLengthSlider.setMajorTickSpacing(100);
        trackLengthSlider.setPaintTicks(true);
        trackLengthSlider.setPaintLabels(true);
        add(new JLabel("Track Length:"));
        add(trackLengthSlider);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    public int getNumberOfTracks() {
        return (int) numberOfTracksSpinner.getValue();
    }

    public int getTrackLength() {
        return trackLengthSlider.getValue();
    }

}
