//necessary imports
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.MatteBorder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainScreen extends JFrame {
	
	//elements for interface
    private JButton settingButton = new JButton();
    private JButton checkJourneyButton = new JButton();
    private JButton todayButton = new JButton();
    private JButton NextDay1Btn = new JButton();
    private JButton NextDay2Btn = new JButton();
    private JButton NextDay3Btn = new JButton();
    private JButton NextDay4Btn = new JButton();
    public JPanel panelMain = new JPanel(); 
    public Image bImg;
    public JLabel bg = new JLabel();
    
    private JButton[] nextWeekBtns = new JButton[] {NextDay1Btn, NextDay2Btn, NextDay3Btn, NextDay4Btn};	//array for buttons for days of the week
    private WeatherInformationParsed wiP;	//parsed data
    
    private String SETTINGS_ICON_PATH = "resources/settings-cog.png";	//setting icon path

    //following three methods will take care of screen transitions
    private void launchSettingsScreen() {
    	this.add(SettingsPanel.getInstance(this));
    	panelMain.setVisible(false);
    	checkJourneyButton.setText("Check journey");
    }

    private void launchJourneyScreen() {
    	String result = JourneyAlgorithm.checkJourney(Settings.getStartTime(), Settings.getDuration(), Settings.getPreferredWeather(), wiP);
    	JLabel tText = new JLabel(result);
    	JLabel bText = new JLabel("press again to check again");
    	tText.setFont(new Font("charcoal", Font.BOLD | Font.ITALIC, 23));
    	bText.setFont(new Font("charcoal", Font.BOLD | Font.ITALIC, 23));
    	tText.setHorizontalAlignment(JLabel.CENTER);
    	tText.setVerticalAlignment(JLabel.CENTER);
    	bText.setHorizontalAlignment(JLabel.CENTER);
    	bText.setVerticalAlignment(JLabel.CENTER);
    	checkJourneyButton.setText("");
    	checkJourneyButton.add(tText, BorderLayout.NORTH);
    	checkJourneyButton.add(bText, BorderLayout.SOUTH);
    }

    private void launchDailyScreen(weatherForADay dayWeather) {
    	this.add(new TodayScreen(this, dayWeather));
    	panelMain.setVisible(false);
    	checkJourneyButton.setText("Check journey");
    }

    //turns background of buttons transparent
    private void makeTransparent(JButton btn) {
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
    }
    
    private void addIcon(JButton btn, String imgPath) {
    	//add an image corresponding to weather
        try {
            BufferedImage img = ImageIO.read(new File(imgPath));
            btn.setIcon(new ImageIcon(img.getScaledInstance((int) (img.getWidth() * 1.5), (int) (img.getHeight() * 1.5), 0)));
        } catch (IOException e) {
        	e.printStackTrace();
        }
    }
    
    //updates information as in constructor
    public void updateData() {
		try {
			wiP = WeatherGet.run(Settings.getLocation());
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	addIcon(todayButton, wiP.getWeatherPerDay()[0].getList().get(3).getIconPath().toString());
    	for (int i = 0; i < nextWeekBtns.length; i++) {

            JButton btn = nextWeekBtns[i];
            String day = wiP.getWeatherPerDay()[i + 1].getDayOfWeek();
            addIcon(btn, wiP.getWeatherPerDay()[i + 1].getList().get(3).getIconPath().toString());
            double temperature = Math.floor(50 * (getMaximumTemperature(wiP.getWeatherPerDay()[i+1]) + getMinimumTemperature(wiP.getWeatherPerDay()[i+1]))) / 100;
            btn.setText(day + " - " + temperature);
            
        }
    	
    	//updates settings
    	Settings.saveSettings();
    	
    }
    
    public MainScreen(String title) {
    	super(title);	//sets title
    	panelMain.setLayout(new GridLayout(7, 1));	//layout
    	panelMain.setOpaque(false);	//allows background
    	
    	//parse JSON
    	try {
			wiP = WeatherGet.run("London");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
    	
        //add click listeners
        settingButton.addActionListener(actionEvent -> launchSettingsScreen());
        checkJourneyButton.addActionListener(actionEvent -> launchJourneyScreen());
        todayButton.addActionListener(actionEvent -> launchDailyScreen(wiP.getWeatherPerDay()[0]));
        
        for (int i = 0; i < nextWeekBtns.length; i++) {
        	JButton btn = nextWeekBtns[i];
        	final int j = i;
    		btn.addActionListener(actionEvent -> launchDailyScreen(wiP.getWeatherPerDay()[j + 1]));
    	}
        
        //add image icons to each button
        addIcon(settingButton, SETTINGS_ICON_PATH);
        addIcon(todayButton, wiP.getWeatherPerDay()[0].getList().get(3).getIconPath().toString());
        
        //today button information is added
        String today = wiP.getWeatherPerDay()[0].getDayOfWeek();
        double todayTemp = 0.5 * (getMaximumTemperature(wiP.getWeatherPerDay()[0]) + getMinimumTemperature(wiP.getWeatherPerDay()[0]));
        todayButton.setText(today + " - " + todayTemp + " °C");
        
        //icons for each day of week button
        for (int i = 0; i < nextWeekBtns.length; i++) {
        	
        	//button and day of week
            JButton btn = nextWeekBtns[i];
            String day = wiP.getWeatherPerDay()[i + 1].getDayOfWeek();

            addIcon(btn, wiP.getWeatherPerDay()[i + 1].getList().get(3).getIconPath().toString());	//icon for this button
            
            double temperature = Math.floor(50 * (getMaximumTemperature(wiP.getWeatherPerDay()[i+1]) + getMinimumTemperature(wiP.getWeatherPerDay()[i+1]))) / 100;	//temperature to 2 d.p.
            btn.setText(day + " - " + temperature + " °C");	//button text
            makeTransparent(btn);	//visual property
            
            //sets font
            btn.setFont(new Font("charcoal", Font.BOLD | Font.ITALIC, 21));

            //text goes above image
            btn.setHorizontalTextPosition(0);
            btn.setVerticalTextPosition(1);
        }
        
        //make remaining iconsTransp transparent
        makeTransparent(settingButton);
        makeTransparent(todayButton);
        
        //stylings
        checkJourneyButton.setFont(new Font("charcoal", Font.BOLD | Font.ITALIC, 23));
        
        todayButton.setFont(new Font("charcoal", Font.BOLD | Font.ITALIC, 21));
        todayButton.setHorizontalTextPosition(0);
        todayButton.setVerticalTextPosition(1);
        
        //size and positioning of elements
        int SCREEN_WIDTH = 600;
        int SCREEN_HEIGHT = 800;
        panelMain.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        
        int WIDTH_CENTER = SCREEN_WIDTH / 2;
        settingButton.setBounds(SCREEN_WIDTH - 50, 0, 50, 50);
        panelMain.add(settingButton);

        int checkButtonWidth = SCREEN_WIDTH / 2;
        checkJourneyButton.setBounds(WIDTH_CENTER - checkButtonWidth / 2, SCREEN_HEIGHT / 8, checkButtonWidth, SCREEN_HEIGHT / 4);
        checkJourneyButton.setLayout(new BorderLayout());
        checkJourneyButton.setText("Check journey");
        panelMain.add(checkJourneyButton);

        todayButton.setBounds(WIDTH_CENTER - SCREEN_WIDTH / 4, 3 * SCREEN_HEIGHT / 8, SCREEN_WIDTH / 2, SCREEN_HEIGHT / 4);
        panelMain.add(todayButton);

        for (int i = 0; i < nextWeekBtns.length; i++) {
            JButton currentBtn = nextWeekBtns[i];
            currentBtn.setBounds(i * SCREEN_WIDTH / 4, 3 * SCREEN_HEIGHT / 4, SCREEN_WIDTH / 4, SCREEN_HEIGHT / 4);
            panelMain.add(currentBtn);
        }

        setBorderColors();	//add style
        
        panelMain.setVisible(true);	//make content visible
        
    }

    private double getMinimumTemperature(weatherForADay todayWeather) {
    	//linear search for min temp
        double minTemp = Double.POSITIVE_INFINITY;
        for (int i = 0; i < todayWeather.getList().size(); i++) {
            if (todayWeather.getList().get(i).getTemp_min() < minTemp)
                minTemp = todayWeather.getList().get(i).getTemp_min();
        }
        return minTemp;
    }

    private double getMaximumTemperature(weatherForADay todayWeather) {
    	//linear search for max temp
        double maxTemp = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < todayWeather.getList().size(); i++) {
            if (todayWeather.getList().get(i).getTemp_min() > maxTemp)
                maxTemp = todayWeather.getList().get(i).getTemp_min();
        }
        return maxTemp;
    }
    
    private void setBorderColors() {
    	//styles buttons
    	settingButton.setBorder(new MatteBorder(2, 2, 2, 2, new Color(198, 240, 254)));
        checkJourneyButton.setBorder(new MatteBorder(2, 2, 2, 2, new Color(198, 240, 254)));
        checkJourneyButton.setOpaque(false);
        checkJourneyButton.setContentAreaFilled(false);
    	todayButton.setBorder(new MatteBorder(2, 2, 2, 2, new Color(198, 240, 254)));
        for(int i = 0; i < nextWeekBtns.length; i++) {
        	JButton btn = nextWeekBtns[i];
            btn.setBorder(new MatteBorder(2, 2, 2, 2, new Color(198, 240, 254)));
        }
        
        //adds adaptive background
        String bgName = wiP.getWeatherPerDay()[0].getList().get(0).getWeatherForBackground();
        try {
            bImg = ImageIO.read(new File("resources/" + bgName + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        bImg = bImg.getScaledInstance(panelMain.getWidth(), panelMain.getHeight(), Image.SCALE_SMOOTH);
        bg.setIcon(new ImageIcon(bImg));
        this.setContentPane(bg);
    }
    
    @Override
    public void paint(Graphics g) {
    	super.paint(g);
    	bImg = bImg.getScaledInstance(panelMain.getParent().getWidth(), panelMain.getParent().getHeight(), Image.SCALE_SMOOTH);
    	bg.setIcon(new ImageIcon(bImg));
    	bg.setSize(panelMain.getParent().getWidth(), panelMain.getParent().getHeight());
    }
    
    public static void main(String[] args) {

        MainScreen home = new MainScreen("SKYCLONE");	//creates instance of home screen
        
        Settings.loadSettings();	//loads stored settings
        
        //sets parameters and displays window
        home.setLayout(new BorderLayout());
        home.add(home.panelMain, BorderLayout.CENTER);
        home.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        home.pack();
        home.setVisible(true);
    }
}