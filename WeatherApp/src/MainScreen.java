//necessary imports
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainScreen extends JFrame {
	
	//elements for interface
    private JButton settingButton;
    private JButton checkJourneyButton;
    private JButton todayButton;
    private JButton NextDay1Btn;
    private JButton NextDay2Btn;
    private JButton NextDay3Btn;
    private JButton NextDay4Btn;
    private JPanel panelMain;
    
    private JButton[] nextWeekBtns = new JButton[] {NextDay1Btn, NextDay2Btn, NextDay3Btn, NextDay4Btn};	//array for buttons for days of the week
    private WeatherInformationParsed wiP;	//parsed data
    
    //icons
    private final String SETTINGS_ICON_PATH = "resources/settings-cog.png";

    //following three methods will take care of screen transitions
    private void launchSettingsScreen() {
    	this.setContentPane(SettingsPanel.getInstance());
    	panelMain.setVisible(false);
    }

    private void launchJourneyScreen() {
    	String result = JourneyAlgorithm.checkJourney(Settings.getStartTime(), Settings.getDuration(), Settings.getPreferredWeather(), wiP);
    	System.out.println(result);
    	//want a pop up to display result
    }

    private void launchDailyScreen(JButton btn) {
    	//create new panel with information for that day
    	//can use e.g. text of button to identify day
    }

    //turns background of buttons transparent
    private void makeTransparent(JButton btn) {
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setOpaque(false);
    }
    
    private void addIcon(JButton btn, String imgPath) {
        try {	//add an image corresponding to weather
            Image icon = ImageIO.read(getClass().getResource(imgPath));
            btn.setIcon(new ImageIcon(icon));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MainScreen(String title) {
    	super(title);	//sets window title

        //add click listeners
        settingButton.addActionListener(actionEvent -> launchSettingsScreen());
        checkJourneyButton.addActionListener(actionEvent -> launchJourneyScreen());
        for (JButton btn : nextWeekBtns) btn.addActionListener(actionEvent -> launchDailyScreen(btn));

        //add image icons to each button
        addIcon(settingButton, SETTINGS_ICON_PATH);
        addIcon(todayButton, wiP.getWeatherPerDay()[0].getList().get(0).getIconPath().toString());
        
        //icons for each day of week button
        for (int i = 0; i < 4; i++) {
        	
        	//button and day of week
            JButton btn = nextWeekBtns[i];
            String day = wiP.getWeatherPerDay()[i + 1].getDayOfWeek();

            addIcon(btn, wiP.getWeatherPerDay()[i + 1].getList().get(0).getIconPath().toString());	//icon for this button
            
            double temperature = wiP.getWeatherPerDay()[i + 1].getList().get(0).getTemp();	//temperature
            btn.setText(day + " - " + temperature);	//button text
            makeTransparent(btn);	//visual property
            
            //text goes above image
            btn.setHorizontalTextPosition(0);
            btn.setVerticalTextPosition(1);
        }

        //make remaining icons transparent
        makeTransparent(settingButton);
        makeTransparent(todayButton);

    }

    public static void main(String[] args) {
    	
        MainScreen app = new MainScreen("Home");	//creates instance of application
        
        //get parsed weather data
        try {
			app.wiP = WeatherGet.run("London");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (RequestFailed e) {
			System.out.println(e.getMessage());
		}
        
        //sets parameters and displays window
        app.setContentPane(app.panelMain);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setSize(600, 800);
        app.setVisible(true);
    }
}