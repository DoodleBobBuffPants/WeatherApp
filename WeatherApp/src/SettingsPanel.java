//necessary imports
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.TreeSet;
import com.github.lgooddatepicker.components.TimePicker;

public class SettingsPanel extends JPanel {
	
	private static SettingsPanel singletonSettingsPanel = null;	//singleton object
	private MainScreen returnPanel;	//main screen to return to
	
	//elements of the interface
	private TimePicker timeSelector;
    private JComboBox<String> durationDropdown;
    private JComboBox<WeatherEnum> weatherDropdown;
    private JComboBox<String> locationDropdown;
    private JButton backButton;
    
    //structure for menus
    private String[] times = {"30 minutes", "1 hour", "1 hour 30 minutes", "2 hours", "2 hours 30 minutes", "3 hours", "3 hours 30 minutes", "4 hours", "4 hours 30 minutes", "5 hours"};
    
    //getters and setters
    public LocalTime getPreferredTime() {
        return timeSelector.getTime();
    }

    public Duration getDurationOfCycle() {
        int sel = durationDropdown.getSelectedIndex();
        Duration d = Duration.ofMinutes(30 * (sel + 1));	//30 minute increments available to select so scales time
        return d;
    }

    public WeatherEnum getPreferredWeather() {
        return weatherDropdown.getItemAt(weatherDropdown.getSelectedIndex());
    }

    public String getLocationName() {
        return locationDropdown.getItemAt(locationDropdown.getSelectedIndex());
    }
    
    private void addLabel(String text) {
    	//adds a label of text
        JLabel l = new JLabel(text);
        l.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(l);
    }

    private void addCenteredComponent(JComponent c) {
    	//sets alignment
        c.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(c);
    }
    
    private void backToHome() {
    	
    	//update settings
    	Settings.setDuration(getDurationOfCycle());
    	Settings.setLocation(getLocationName());
    	Settings.setpreferredWeather(getPreferredWeather());
    	Settings.setStartTime(getPreferredTime());
    	
    	returnPanel.updateData();	//update according to new settings
    	
    	//switch panels
    	returnPanel.panelMain.setVisible(true);
    	returnPanel.setContentPane(returnPanel.panelMain);
    	this.setVisible(false);
    }
    
    public SettingsPanel(MainScreen returnPanel) {
    	
    	//gets locations
    	try {
			TreeSet<String> cities = CityParse.parse();	//all locations
			this.returnPanel = returnPanel;	//panel to go back to
	        this.setLayout(new GridLayout(9, 1));	//layout
	        
	        //each preference
	        addLabel("Preferred Time");
	        timeSelector = new TimePicker();
	        timeSelector.setTimeToNow();
	        addCenteredComponent(timeSelector);

	        addLabel("Duration of Cycle");
	        durationDropdown = new JComboBox<String>(times);
	        durationDropdown.setSelectedIndex(0);
	        addCenteredComponent(durationDropdown);

	        addLabel("Preferred Weather");
	        weatherDropdown = new JComboBox<WeatherEnum>(WeatherEnum.values());
	        weatherDropdown.setSelectedIndex(0);
	        addCenteredComponent(weatherDropdown);

	        addLabel("Your Location");
	        locationDropdown = new JComboBox<String>(cities.toArray(new String[0]));
	        locationDropdown.setSelectedIndex(0);
	        addCenteredComponent(locationDropdown);
	        
	        //back to main screen
	        backButton = new JButton();
	        backButton.setText("Back");
	        addCenteredComponent(backButton);
	        backButton.addActionListener(actionEvent -> backToHome());	//click event
	        
		} catch (IOException e) {
			//if file not found
			e.printStackTrace();
		}
    }
    
    //singleton getter
    public static SettingsPanel getInstance(MainScreen returnPanel) {
    	
    	if (singletonSettingsPanel == null) {
    		//construct as needed
            singletonSettingsPanel = new SettingsPanel(returnPanel);
            singletonSettingsPanel.setSize(600, 800);
    	}
    	
    	singletonSettingsPanel.setVisible(true);	//make it visible
    	return singletonSettingsPanel;
    }
}