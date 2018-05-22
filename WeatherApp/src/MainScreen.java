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
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Collections;
import java.util.Locale;

public class MainScreen extends JFrame {
	
	//elements for interface
    private JButton settingButton = new JButton();
    private JButton checkJourneyButton = new JButton();


    private JPanel todayButton = new JPanel(); // today bit panel

    //panels for each of the next days - this needs to be cleared on updating
    private JPanel NextDay1Btn = new JPanel();
    private JPanel NextDay2Btn = new JPanel();
    private JPanel NextDay3Btn = new JPanel();
    private JPanel NextDay4Btn = new JPanel();
    private JPanel nextBtns = new JPanel();
    private JLabel tText;
    private JLabel bText;

    // main panel
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
    	tText.setText("");
    	bText.setText("");
    }

    private void launchJourneyScreen() {
    	String result = JourneyAlgorithm.checkJourney(Settings.getStartTime(), Settings.getDuration(), Settings.getPreferredWeather(), wiP);
    	tText.setText(result);
    	bText.setText("Press to check again");
    	checkJourneyButton.setText("");
    }

    private void launchDailyScreen(weatherForADay dayWeather) {
    	this.add(new TodayScreen(this, dayWeather));
    	panelMain.setVisible(false);
    	checkJourneyButton.setText("Check journey");
    	tText.setText("");
    	bText.setText("");
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
    	
    	//gets new data
		try {
			wiP = WeatherGet.run(Settings.getLocation());
			for (int i = 0; i < wiP.getWeatherPerDay().length; i++) {
			    // sorts the times of the data - this is important in getting the icons for the middle of the day
                Collections.sort(wiP.getWeatherPerDay()[i].getList(), (p1, p2) -> p1.getTime().getHour() - p2.getTime().getHour());
            }
		} catch (IOException e) {
			e.printStackTrace();
		}

		//clears old panels
        panelMain.remove(todayButton);
		panelMain.remove(nextBtns);
        nextBtns.removeAll();
        
        //reinitialise today button
		todayButton = new JPanel();
		todayButton.setOpaque(false);
        todayButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                launchDailyScreen(wiP.getWeatherPerDay()[0]);
            }
        }); // this is a click event - launches if the panel is clicked
        
        //get wind data, format it and add it
        double windAv = getAverageWind(wiP.getWeatherPerDay()[0]);

        // format it to 2dp (max)
        DecimalFormat formatter = new DecimalFormat("#.##", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        formatter.setRoundingMode(RoundingMode.UP);
        String sWind = formatter.format(windAv);

        // The today part is gridlayout with wind bit on the first column, icon and label in the second and the temperature on the third
        JLabel averageWind = new JLabel(sWind + " m/s", SwingConstants.CENTER);
        averageWind.setFont(new Font("charcoal", Font.BOLD | Font.ITALIC, 21));
        averageWind.setHorizontalTextPosition(SwingConstants.CENTER);
        averageWind.setVerticalTextPosition(SwingConstants.CENTER);

        // Today bit
        JButton todayIcon = new JButton();
        todayIcon.setText("Today");
        todayIcon.setFont(new Font("charcoal", Font.BOLD | Font.ITALIC, 21));
        todayIcon.setHorizontalTextPosition(0);
        todayIcon.setVerticalTextPosition(SwingConstants.TOP);

        ImageIcon temp = new ImageIcon(wiP.getWeatherPerDay()[0].getList().get(3).getIconPath().toString());
        Image newimg = temp.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH); // resizes the image to correct size
        todayIcon.setIcon(new ImageIcon(newimg));
        todayIcon.setOpaque(false);
        todayIcon.setContentAreaFilled(false);
        todayIcon.setBorderPainted(false);

        //today button information is added
        double todayTemp = Math.floor(50 * (getMaximumTemperature(wiP.getWeatherPerDay()[0]) + getMinimumTemperature(wiP.getWeatherPerDay()[0]))) / 100;
        JLabel descriptionText = new JLabel((todayTemp + " °C"), SwingConstants.CENTER);
        
        descriptionText.setFont(new Font("charcoal", Font.BOLD | Font.ITALIC, 21));
        descriptionText.setHorizontalTextPosition(SwingConstants.CENTER);
        descriptionText.setVerticalTextPosition(SwingConstants.CENTER);
        todayButton.add(averageWind);
        todayButton.add(todayIcon);
        todayButton.add(descriptionText);

        for (int i = 0; i < nextWeekBtns.length; i++) {
        	
        	//for each weekly button
            JPanel weekPanel = nextWeekBtns[i];
            weekPanel.removeAll();
            weekPanel.setOpaque(false);
            
            //button and day of week
            JButton btnWeek = new JButton();
            btnWeek.setOpaque(false);
            btnWeek.setContentAreaFilled(false);
            btnWeek.setBorderPainted(false);
            final int j = i;

            // action listener is added - for clicks it needs to open the correct screen
            btnWeek.addActionListener(actionEvent -> launchDailyScreen(wiP.getWeatherPerDay()[j + 1]));

            String day = wiP.getWeatherPerDay()[i + 1].getDayOfWeek();
            addIcon(btnWeek, wiP.getWeatherPerDay()[i + 1].getList().get(3).getIconPath().toString());	// icon for this button (size is correct automatically)

            double temperature = Math.floor(50 * (getMaximumTemperature(wiP.getWeatherPerDay()[i+1]) + getMinimumTemperature(wiP.getWeatherPerDay()[i+1]))) / 100;	//temperature to 2 d.p.
            btnWeek.setText(day);	//button text
            makeTransparent(btnWeek);	//visual property

            //sets font
            btnWeek.setFont(new Font("charcoal", Font.BOLD | Font.ITALIC, 21));

            //text goes above image
            btnWeek.setHorizontalTextPosition(0);
            btnWeek.setVerticalTextPosition(1);
            weekPanel.add(btnWeek);

            JLabel tempLabel = new JLabel(temperature + " °C");
            tempLabel.setFont(new Font("charcoal", Font.BOLD | Font.ITALIC, 23));
            weekPanel.add(tempLabel);
            
        }
        
        //updates panel of week ahead
        nextBtns = new JPanel();
        nextBtns.setLayout(new GridLayout(0, 4));
        nextBtns.setOpaque(false);
        
        int SCREEN_WIDTH = 600;
        int SCREEN_HEIGHT = 800;
        
        for (int i = 0; i < nextWeekBtns.length; i++) {
            JPanel currentBtn = nextWeekBtns[i];
            currentBtn.setBounds(i * SCREEN_WIDTH / 4, 3 * SCREEN_HEIGHT / 4, SCREEN_WIDTH / 4, SCREEN_HEIGHT / 4);
            nextBtns.add(currentBtn);
        }
        
        String bgName = wiP.getWeatherPerDay()[0].getList().get(0).getWeatherForBackground();
        try {
            bImg = ImageIO.read(new File("resources/" + bgName + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        bImg = bImg.getScaledInstance(panelMain.getWidth(), panelMain.getHeight(), Image.SCALE_SMOOTH);
        bg.setIcon(new ImageIcon(bImg));

        panelMain.add(todayButton);
        panelMain.add(nextBtns);

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
			wiP = WeatherGet.run(Settings.getLocation());
            for (int i = 0; i < wiP.getWeatherPerDay().length; i++) {
                Collections.sort(wiP.getWeatherPerDay()[i].getList(), (p1, p2) -> p1.getTime().getHour() - p2.getTime().getHour());
            }
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
        //add click listeners
        settingButton.addActionListener(actionEvent -> launchSettingsScreen());
        checkJourneyButton.addActionListener(actionEvent -> launchJourneyScreen());
        todayButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                launchDailyScreen(wiP.getWeatherPerDay()[0]);
            }
        });

        //add image icons to each button
        addIcon(settingButton, SETTINGS_ICON_PATH);
        
        //format and display wind data
        double windAv = getAverageWind(wiP.getWeatherPerDay()[0]);

        DecimalFormat formatter = new DecimalFormat("#.##", DecimalFormatSymbols.getInstance( Locale.ENGLISH ));
        formatter.setRoundingMode( RoundingMode.UP );
        String s = formatter.format(windAv);

        JLabel averageWind = new JLabel(s + " m/s", SwingConstants.CENTER);
        averageWind.setFont(new Font("charcoal", Font.BOLD | Font.ITALIC, 21));
        averageWind.setHorizontalTextPosition(SwingConstants.CENTER);
        averageWind.setVerticalTextPosition(SwingConstants.CENTER);
        averageWind.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                launchDailyScreen(wiP.getWeatherPerDay()[0]);
            }
        });

        //clickable button with icon for today
        JButton todayIcon = new JButton();
        todayIcon.setText("Today");
        todayIcon.setFont(new Font("charcoal", Font.BOLD | Font.ITALIC, 21));
        todayIcon.setHorizontalTextPosition(0);
        todayIcon.setVerticalTextPosition(SwingConstants.TOP);

        ImageIcon temp = new ImageIcon(wiP.getWeatherPerDay()[0].getList().get(3).getIconPath().toString());
        Image newimg = temp.getImage().getScaledInstance( 150, 150,  java.awt.Image.SCALE_SMOOTH ) ; // resize image
        todayIcon.setIcon(new ImageIcon(newimg));
        todayIcon.addActionListener(actionEvent -> launchDailyScreen(wiP.getWeatherPerDay()[0]));

        todayIcon.setOpaque(false);
        todayIcon.setContentAreaFilled(false);
        todayIcon.setBorderPainted(false);
        
        //today button information is added
        double todayTemp = Math.floor(50 * (getMaximumTemperature(wiP.getWeatherPerDay()[0]) + getMinimumTemperature(wiP.getWeatherPerDay()[0]))) / 100;
        JLabel descriptionText = new JLabel((todayTemp + " °C"), SwingConstants.CENTER);

        descriptionText.setFont(new Font("charcoal", Font.BOLD | Font.ITALIC, 21));
        descriptionText.setHorizontalTextPosition(SwingConstants.CENTER);
        descriptionText.setVerticalTextPosition(SwingConstants.CENTER);
        descriptionText.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                launchDailyScreen(wiP.getWeatherPerDay()[0]);
            }
        });

        todayButton.add(averageWind);
        todayButton.add(todayIcon);
        todayButton.add(descriptionText);

        //icons for each day of week button
        for (int i = 0; i < nextWeekBtns.length; i++) {
        	
        	JPanel weekPan = nextWeekBtns[i];
        	weekPan.setOpaque(false);
        	
        	//button and day of week
            JButton btnWeek = new JButton();
            btnWeek.setOpaque(false);
            btnWeek.setContentAreaFilled(false);
            btnWeek.setBorderPainted(false);
            final int j = i;
            btnWeek.addActionListener(actionEvent -> launchDailyScreen(wiP.getWeatherPerDay()[j + 1]));

            String day = wiP.getWeatherPerDay()[i + 1].getDayOfWeek();

            addIcon(btnWeek, wiP.getWeatherPerDay()[i + 1].getList().get(3).getIconPath().toString());	//icon for this button
            
            double temperature = Math.floor(50 * (getMaximumTemperature(wiP.getWeatherPerDay()[i+1]) + getMinimumTemperature(wiP.getWeatherPerDay()[i+1]))) / 100;	//temperature to 2 d.p.
            btnWeek.setText(day);	//button text
            makeTransparent(btnWeek);	//visual property
            
            //sets font
            btnWeek.setFont(new Font("charcoal", Font.BOLD | Font.ITALIC, 21));

            //text goes above image
            btnWeek.setHorizontalTextPosition(0);
            btnWeek.setVerticalTextPosition(1);
            weekPan.add(btnWeek);

            JLabel weekTemp = new JLabel(temperature + " °C");
            weekTemp.setFont(new Font("charcoal", Font.BOLD | Font.ITALIC, 23));
            weekPan.add(weekTemp);
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
        
        //text labels for journey button
    	tText = new JLabel();
    	bText = new JLabel();
    	tText.setFont(new Font("charcoal", Font.BOLD | Font.ITALIC, 23));
    	bText.setFont(new Font("charcoal", Font.BOLD | Font.ITALIC, 23));
    	tText.setHorizontalAlignment(JLabel.CENTER);
    	tText.setVerticalAlignment(JLabel.CENTER);
    	bText.setHorizontalAlignment(JLabel.CENTER);
    	bText.setVerticalAlignment(JLabel.CENTER);
    	checkJourneyButton.add(tText, BorderLayout.NORTH);
    	checkJourneyButton.add(bText, BorderLayout.SOUTH);
        
        checkJourneyButton.setText("Check journey");
        panelMain.add(checkJourneyButton);

        todayButton.setBounds(WIDTH_CENTER - SCREEN_WIDTH / 4, 3 * SCREEN_HEIGHT / 8, SCREEN_WIDTH / 2, SCREEN_HEIGHT / 4);
        panelMain.add(todayButton);

        nextBtns = new JPanel();
        nextBtns.setLayout(new GridLayout(0, 4));
        nextBtns.setOpaque(false);
        for (int i = 0; i < nextWeekBtns.length; i++) {
            JPanel currentBtn = nextWeekBtns[i];
            currentBtn.setBounds(i * SCREEN_WIDTH / 4, 3 * SCREEN_HEIGHT / 4, SCREEN_WIDTH / 4, SCREEN_HEIGHT / 4);
            nextBtns.add(currentBtn);
        }

        panelMain.add(nextBtns);

        setBorderColors();	//add style
        
        panelMain.setVisible(true);	//make content visible
        
    }
    
    private double getAverageWind(weatherForADay todayWeather) {
        int number = 0;
        int sum = 0;
        for (int i = 0; i < todayWeather.getList().size(); i++) {
            sum += todayWeather.getList().get(i).getWindSpeed();
            number++;
        }
        return (double) sum / number;
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

    	Settings.loadSettings();	//loads stored settings
    	
        MainScreen home = new MainScreen("SKYCLONE");	//creates instance of home screen
        
        //sets parameters and displays window
        home.setLayout(new BorderLayout());
        home.add(home.panelMain, BorderLayout.CENTER);
        home.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        home.pack();
        home.setVisible(true);
    }
}