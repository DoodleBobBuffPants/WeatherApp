//necessary imports
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.MatteBorder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
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
        l.setHorizontalAlignment(JLabel.CENTER);
        l.setVerticalAlignment(JLabel.CENTER);
        l.setFont(new Font("charcoal", Font.BOLD | Font.ITALIC, 23));
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
    	returnPanel.remove(this);
    	returnPanel.add(returnPanel.panelMain);
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
	        durationDropdown.setFont(new Font("charcoal", Font.BOLD | Font.ITALIC, 23));
	        ((JLabel) durationDropdown.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
	        durationDropdown.setBackground(new Color(198, 240, 254, 100));
	        addCenteredComponent(durationDropdown);

	        addLabel("Preferred Weather");
	        weatherDropdown = new JComboBox<WeatherEnum>(WeatherEnum.values());
	        weatherDropdown.setSelectedIndex(0);
	        weatherDropdown.setFont(new Font("charcoal", Font.BOLD | Font.ITALIC, 23));
	        ((JLabel) weatherDropdown.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
	        weatherDropdown.setBackground(new Color(198, 240, 254, 100));
	        addCenteredComponent(weatherDropdown);

	        addLabel("Your Location");
	        locationDropdown = new JComboBox<String>(cities.toArray(new String[0]));
	        locationDropdown.setSelectedIndex(0);
	        locationDropdown.setFont(new Font("charcoal", Font.BOLD | Font.ITALIC, 23));
	        ((JLabel) locationDropdown.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
	        locationDropdown.setBackground(new Color(198, 240, 254, 100));
	        addCenteredComponent(locationDropdown);
	        
	        //back to main screen
	        backButton = new JButton();
	        BufferedImage img = ImageIO.read(new File("resources/cc3backbygoogle.png"));
	        backButton.setIcon(new ImageIcon(img.getScaledInstance((int) (img.getWidth() * 1.5), (int) (img.getHeight() * 1.5), 0)));
	        backButton.setHorizontalAlignment(JButton.LEFT);
	        backButton.setBorder(new MatteBorder(2, 2, 2, 2, new Color(198, 240, 254)));
		    backButton.setBorderPainted(true);
		    backButton.setOpaque(false);
		    backButton.setContentAreaFilled(false);
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
            singletonSettingsPanel.setOpaque(false);
    	}
    	
    	singletonSettingsPanel.setVisible(true);	//make it visible
    	return singletonSettingsPanel;
    }
}