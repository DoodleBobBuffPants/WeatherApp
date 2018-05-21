//necessary imports
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.MatteBorder;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainScreen extends JFrame {
	
	//elements for interface
    private JButton settingButton = new JButton();
    private JButton checkJourneyButton = new JButton();
    private JPanel todayButton = new JPanel();
    private JPanel NextDay1Btn = new JPanel();
    private JPanel NextDay2Btn = new JPanel();
    private JPanel NextDay3Btn = new JPanel();
    private JPanel NextDay4Btn = new JPanel();
    public JPanel panelMain = new JPanel(); 
    public Image bImg;
    public JLabel bg = new JLabel();
    
    private JPanel[] nextWeekBtns = new JPanel[] {NextDay1Btn, NextDay2Btn, NextDay3Btn, NextDay4Btn};	//array for buttons for days of the week
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


		todayButton = new JPanel();
        todayButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                launchDailyScreen(wiP.getWeatherPerDay()[0]);
            }
        });
        JButton icon = new JButton();
        addIcon(icon, wiP.getWeatherPerDay()[0].getList().get(3).getIconPath().toString());

        JLabel descriptionText = new JLabel();

        //today button information is added
        String today = wiP.getWeatherPerDay()[0].getDayOfWeek();
        double todayTemp = Math.floor(50 * (getMaximumTemperature(wiP.getWeatherPerDay()[0]) + getMinimumTemperature(wiP.getWeatherPerDay()[0]))) / 100;
        descriptionText.setText("Today" + " - " + todayTemp + " °C");

        todayButton.add(icon);
        todayButton.add(descriptionText);

        for (int i = 0; i < nextWeekBtns.length; i++) {

            JPanel item = new JPanel();
            item.setOpaque(false);
            //button and day of week
            JButton btn = new JButton();
            btn.setOpaque(false);
            btn.setContentAreaFilled(false);
            btn.setBorderPainted(false);
            final int j = i;
            btn.addActionListener(actionEvent -> launchDailyScreen(wiP.getWeatherPerDay()[j + 1]));

            String day = wiP.getWeatherPerDay()[i + 1].getDayOfWeek();

            addIcon(btn, wiP.getWeatherPerDay()[i + 1].getList().get(3).getIconPath().toString());	//icon for this button

            double temperature = Math.floor(50 * (getMaximumTemperature(wiP.getWeatherPerDay()[i+1]) + getMinimumTemperature(wiP.getWeatherPerDay()[i+1]))) / 100;	//temperature to 2 d.p.
            btn.setText(day);	//button text
            makeTransparent(btn);	//visual property

            //sets font
            btn.setFont(new Font("charcoal", Font.BOLD, 21));

            //text goes above image
            btn.setHorizontalTextPosition(0);
            btn.setVerticalTextPosition(1);
            item.add(btn);

            JLabel secondBit = new JLabel(temperature + " °C");
            secondBit.setFont(new Font("charcoal", Font.PLAIN, 23));
            item.add(secondBit);
            
        }
    	
    	//updates settings
    	Settings.saveSettings();
    	
    }
    
    public MainScreen(String title) {
    	super(title);	//sets title
    	panelMain.setLayout(new GridLayout(4, 0));	//layout
    	panelMain.setOpaque(false);	//allows background
        todayButton = new JPanel(new GridLayout(0, 3));
    	//parse JSON
    	try {
			wiP = WeatherGet.run("London");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
    	
        //add click listeners
        settingButton.addActionListener(actionEvent -> launchSettingsScreen());
        checkJourneyButton.addActionListener(actionEvent -> launchJourneyScreen());
        //todayButton.addActionListener(actionEvent -> launchDailyScreen(wiP.getWeatherPerDay()[0]));
        todayButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                launchDailyScreen(wiP.getWeatherPerDay()[0]);
            }
        });

        /*
        for (int i = 0; i < nextWeekBtns.length; i++) {

        	JPanel btn = nextWeekBtns[i];
        	final int j = i;
    		btn.addActionListener(actionEvent -> launchDailyScreen(wiP.getWeatherPerDay()[j + 1]));
    	}
    	*/

        //add image icons to each button
        addIcon(settingButton, SETTINGS_ICON_PATH);
        double average = getAverageWind(wiP.getWeatherPerDay()[0]);
        JLabel averageWind = new JLabel(Double.toString(average), SwingConstants.CENTER);
        averageWind.setFont(new Font("charcoal", Font.BOLD | Font.ITALIC, 21));
        averageWind.setHorizontalTextPosition(SwingConstants.CENTER);
        averageWind.setVerticalTextPosition(SwingConstants.CENTER);
        JButton icon = new JButton();
        addIcon(icon, wiP.getWeatherPerDay()[0].getList().get(3).getIconPath().toString());
        icon.setOpaque(false);
        icon.setContentAreaFilled(false);
        icon.setBorderPainted(false);

        
        //today button information is added
        String today = wiP.getWeatherPerDay()[0].getDayOfWeek();
        double todayTemp = Math.floor(50 * (getMaximumTemperature(wiP.getWeatherPerDay()[0]) + getMinimumTemperature(wiP.getWeatherPerDay()[0]))) / 100;
        JLabel descriptionText = new JLabel(convertToMultiline("Today" + "\n" + todayTemp + " °C"), SwingConstants.CENTER);

        descriptionText.setFont(new Font("charcoal", Font.BOLD | Font.ITALIC, 21));
        descriptionText.setHorizontalTextPosition(SwingConstants.CENTER);
        descriptionText.setVerticalTextPosition(SwingConstants.CENTER);
        todayButton.add(averageWind);
        todayButton.add(icon);
        todayButton.add(descriptionText);

        
        //icons for each day of week button
        for (int i = 0; i < nextWeekBtns.length; i++) {
        	JPanel item = nextWeekBtns[i];
        	item.setOpaque(false);
        	//button and day of week
            JButton btn = new JButton();
            btn.setOpaque(false);
            btn.setContentAreaFilled(false);
            btn.setBorderPainted(false);
            final int j = i;
            btn.addActionListener(actionEvent -> launchDailyScreen(wiP.getWeatherPerDay()[j + 1]));

            String day = wiP.getWeatherPerDay()[i + 1].getDayOfWeek();

            addIcon(btn, wiP.getWeatherPerDay()[i + 1].getList().get(3).getIconPath().toString());	//icon for this button
            
            double temperature = Math.floor(50 * (getMaximumTemperature(wiP.getWeatherPerDay()[i+1]) + getMinimumTemperature(wiP.getWeatherPerDay()[i+1]))) / 100;	//temperature to 2 d.p.
            btn.setText(day);	//button text
            makeTransparent(btn);	//visual property
            
            //sets font
            btn.setFont(new Font("charcoal", Font.BOLD, 21));

            //text goes above image
            btn.setHorizontalTextPosition(0);
            btn.setVerticalTextPosition(1);
            item.add(btn);

            JLabel secondBit = new JLabel(temperature + " °C");
            secondBit.setFont(new Font("charcoal", Font.PLAIN, 23));
            item.add(secondBit);
        }
        
        //make remaining iconsTransp transparent
        makeTransparent(settingButton);
        todayButton.setOpaque(false);
        
        //stylings
        checkJourneyButton.setFont(new Font("charcoal", Font.BOLD | Font.ITALIC, 23));
        

        
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

        JPanel nextBtns = new JPanel();
        nextBtns.setLayout(new GridLayout(0, 4));
        nextBtns.setOpaque(false);
        for (int i = 0; i < nextWeekBtns.length; i++) {
            JPanel currentBtn = nextWeekBtns[i];
            currentBtn.setBounds(i * SCREEN_WIDTH / 4, 3 * SCREEN_HEIGHT / 4, SCREEN_WIDTH / 4, SCREEN_HEIGHT / 4);
            nextBtns.add(currentBtn);
            //panelMain.add(currentBtn);
        }

        panelMain.add(nextBtns);

        setBorderColors();	//add style
        
        panelMain.setVisible(true);	//make content visible
        
    }
    private double getAverageWind(weatherForADay todayWeather)
    {
        int number = 0;
        double sum = 0;
        for (int i = 0; i < todayWeather.getList().size(); i++)
        {
            sum += todayWeather.getList().get(i).getWindSpeed();
            number++;
        }
        return sum / number;
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
        	JPanel btn = nextWeekBtns[i];
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
    public static String convertToMultiline(String orig)
    {
        return "<html>" + orig.replaceAll("\n", "<br>");
    }
}