import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.Duration;
import java.time.LocalTime;
import com.github.lgooddatepicker.components.TimePicker;


public class SettingsPanel extends JPanel {

    public LocalTime getPreferredTime() {
        return timeSelector.getTime();
    }

    public Duration getDurationOfCycle() {
        int sel = durationDropdown.getSelectedIndex();
        //30 minute increments in dropdown, first one is 30 mins, sel starts at 0 -> 30 mins
        Duration d = Duration.ofMinutes(30 * (sel + 1));
        return d;
    }

    public WeatherEnum getPreferredWeather() {
        return weatherDropdown.getItemAt(weatherDropdown.getSelectedIndex());
    }

    public double getLocationLatitude() {
        return locationDropdown.getItemAt(locationDropdown.getSelectedIndex()).coord.getLat();
    }

    public double getLocationLongitude() {
        return locationDropdown.getItemAt(locationDropdown.getSelectedIndex()).coord.getLon();
    }

    public String getLocationName() {
        return locationDropdown.getItemAt(locationDropdown.getSelectedIndex()).name;
    }

    private TimePicker timeSelector;
    private JComboBox<String> durationDropdown;
    private JComboBox<WeatherEnum> weatherDropdown;
    private JButton backButton;
    private JComboBox<Location> locationDropdown;

    private String[] times = {"30 minutes", "1 hour", "1 hour 30 minutes", "2 hours", "2 hours 30 minutes", "3 hours", "3 hours 30 minutes", "4 hours", "4 hours 30 minutes", "5 hours"};
    private Location[] locations;

    private static class Location {
        public WeatherData.coord coord;
        public String name;

        public Location(String name, double lat, double lon) {
            this.coord = new WeatherData.coord();
            this.coord.setLat(lat);
            this.coord.setLon(lon);
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

    private void addLabel(String text) {
        JLabel l = new JLabel(text);
        l.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(l);
    }

    private void addButton(String text) {
        JButton l = new JButton(text);
        l.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(l);
    }

    private void addCenteredComponent(JComponent c) {
        c.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(c);
    }

    public SettingsPanel() {
        super();

        locations = new Location[3];
        locations[0] = new Location("Cambridge", 52.2053, 0.1218);
        locations[1] = new Location("Manchester", 53.4808, 2.2426);
        locations[2] = new Location("Liverpool", 53.4084, 2.9916);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        addLabel("Preferred Time");
        timeSelector = new TimePicker();
        addCenteredComponent(timeSelector);

        addLabel("Duration of Cycle");
        durationDropdown = new JComboBox<>(times);
        addCenteredComponent(durationDropdown);

        addLabel("Preferred Weather");
        weatherDropdown = new JComboBox<>(WeatherEnum.values());
        addCenteredComponent(weatherDropdown);

        addLabel("Your Location");
        locationDropdown = new JComboBox<>(locations);
        addCenteredComponent(locationDropdown);

    }

    public static void main(String[] args) {
        JFrame f = new JFrame("Test");
        SettingsPanel p = new SettingsPanel();
        f.setContentPane(p);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(800, 600);
        f.pack();
        f.setVisible(true);

        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.out.println(p.getLocationName() + ": " + p.getLocationLatitude() + "N + " + p.getLocationLongitude() + "E");
                System.out.println(p.getPreferredWeather());
                System.out.println(p.getPreferredTime());
            }
        });
    }
}
