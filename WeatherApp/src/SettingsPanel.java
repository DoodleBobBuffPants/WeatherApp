//necessary imports
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.MatteBorder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.TreeSet;

public class SettingsPanel extends JPanel {
	
	private static SettingsPanel singletonSettingsPanel = null;	//singleton object
	private MainScreen returnPanel;	//main screen to return to
	
	//elements of the interface
	private JSpinner timePicker;
    private JComboBox<String> durationDropdown;
    private JComboBox<WeatherEnum> weatherDropdown;
    private JComboBox<String> locationDropdown;
    private JButton backButton;
    
    //structure for menus
    private String[] times = {"30 minutes", "1 hour", "1 hour 30 minutes", "2 hours", "2 hours 30 minutes", "3 hours", "3 hours 30 minutes", "4 hours", "4 hours 30 minutes", "5 hours"};
    
    //getters and setters
    public LocalTime getPreferredTime() {
    	//Returns the value in the time picker, correctly accounting for time zones
        return LocalDateTime.ofInstant(((Date) timePicker.getValue()).toInstant(), ZoneId.systemDefault()).toLocalTime();
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
        //Centres the label in the grid layout
        l.setHorizontalAlignment(JLabel.CENTER);
        l.setVerticalAlignment(JLabel.CENTER);
        l.setFont(new Font("charcoal", Font.BOLD | Font.ITALIC, 23));
        this.add(l);
    }

    private void addCenteredComponent(JComponent c) {
    	//Aligns to centre horizontally in grid layout
        c.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(c);
    }
    
    private void backToHome() {
    	
    	//Update the settings object, effectively saving the changes
    	Settings.setDuration(getDurationOfCycle());
    	Settings.setLocation(getLocationName());
    	Settings.setPreferredWeather(getPreferredWeather());
    	Settings.setStartTime(getPreferredTime());
    	
    	returnPanel.updateData();	//update according to new settings
    	
    	//switch panels
    	returnPanel.panelMain.setVisible(true);
    	returnPanel.add(returnPanel.panelMain);
    	this.setVisible(false);
    }
    
    public SettingsPanel(MainScreen returnPanel) {
    	
    	//gets locations
    	try {
			TreeSet<String> cities = CityParse.parse();	//all locations
			this.returnPanel = returnPanel;	//panel to go back to
	        this.setLayout(new GridLayout(9, 1)); //Set the layout to a grid layout
	        
	        //each preference
	        addLabel("Preferred Time");
	        //Creates a JSpinner for selecting a time. Effectively a dropdown without seeing all options at once
	        Date date = Date.from(Settings.getStartTime().atDate(LocalDate.of(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH)).atZone(ZoneId.systemDefault()).toInstant());
	        SpinnerDateModel sdm = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
	        timePicker = new JSpinner(sdm);
	        JSpinner.DateEditor de = new JSpinner.DateEditor(timePicker, "HH:mm");
	        de.getTextField().setHorizontalAlignment(JTextField.CENTER);
	        timePicker.setEditor(de);
	        timePicker.setFont(new Font("character", Font.BOLD | Font.ITALIC, 23));
	        timePicker.getEditor().getComponent(0).setBackground(new Color(198, 240, 254, 255));
	        addCenteredComponent(timePicker);

	        addLabel("Duration of Cycle");
	        durationDropdown = new JComboBox<String>(times);
	        durationDropdown.setSelectedItem(Settings.getDuration());
	        durationDropdown.setFont(new Font("charcoal", Font.BOLD | Font.ITALIC, 23));
	        ((JLabel) durationDropdown.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
	        durationDropdown.setBackground(new Color(198, 240, 254, 100));
	        addCenteredComponent(durationDropdown);

	        addLabel("Preferred Weather");
	        weatherDropdown = new JComboBox<WeatherEnum>(WeatherEnum.values());
	        weatherDropdown.setSelectedItem(Settings.getPreferredWeather());
	        weatherDropdown.setFont(new Font("charcoal", Font.BOLD | Font.ITALIC, 23));
	        ((JLabel) weatherDropdown.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
	        weatherDropdown.setBackground(new Color(198, 240, 254, 100));
	        addCenteredComponent(weatherDropdown);

	        addLabel("Your Location");
	        locationDropdown = new JComboBox<String>(cities.toArray(new String[0]));
	        locationDropdown.setSelectedItem(Settings.getLocation());
	        locationDropdown.setFont(new Font("charcoal", Font.BOLD | Font.ITALIC, 23));
	        ((JLabel) locationDropdown.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
	        locationDropdown.setBackground(new Color(198, 240, 254, 255));
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

    	//If null, singleton hasn't been created for the first time yet, so construct now
    	if (singletonSettingsPanel == null) {
            singletonSettingsPanel = new SettingsPanel(returnPanel);
            singletonSettingsPanel.setSize(600, 800);
            singletonSettingsPanel.setOpaque(false);
    	}
    	
    	singletonSettingsPanel.setVisible(true);	//make it visible
    	return singletonSettingsPanel;
    }
}