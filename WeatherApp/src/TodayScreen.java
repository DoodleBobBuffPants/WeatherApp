//necessary imports
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TodayScreen extends JPanel {

	private MainScreen returnPanel;
	private weatherForADay todayWeather;
	
    public TodayScreen(MainScreen returnPanel, weatherForADay weather) {
    	
    	//initialise variables
    	this.returnPanel = returnPanel;
		this.todayWeather = weather;
		
        this.setLayout(new GridLayout(3, 1));	//set layout
        
        this.setSize(600,  800);
        
        //add elements
        this.add(createTop(todayWeather.getDayOfWeek(), todayWeather.getList().get(0).getTemp_max(), todayWeather.getList().get(0).getTemp_min()), BorderLayout.NORTH);
        this.add(createCentre(), BorderLayout.CENTER);
        this.add(createBottom(), BorderLayout.SOUTH);
        
        this.setVisible(true);
        
    }
    
    private void backToMain() {
    	
    	//switch screens
    	returnPanel.panelMain.setVisible(true);
    	returnPanel.setContentPane(returnPanel.panelMain);
    	this.setVisible(false);
    }

    private JPanel createBottom() {
    	
    	//bottom to return to main screen
        JPanel bottom = new JPanel();
        bottom.setLayout(new GridLayout(1, 1));

        ImageIcon back = new ImageIcon("resources/cc3backbygoogle.png");
        JButton backbutton = new JButton(back);
        
        backbutton.addActionListener(actionEvent -> backToMain());

        bottom.add(backbutton);

        return bottom;
    }

    private JPanel createCentre() {
        JPanel center = new JPanel();	//panel of data
        
        List<weatherForAThreeHourlyPeriod> periodData = todayWeather.getList();	//list of all information needed
        
        center.setLayout(new GridLayout(periodData.size(), 2));	//structures the data
        
        for (int i = 0; i < periodData.size(); i++){
        	//creates data for this period
        	String data = periodData.get(i).getTime().toString() + "    " + periodData.get(i).getTemp();
            Label time = new Label(data);
            time.setAlignment(Label.CENTER);
            center.add(time);
            
        }
        
        return center;
    }

    private JPanel createTop(String dayofweek, double high, double low) {
    	
        //creates top panel
        JPanel top = new JPanel();
        top.setLayout(new BorderLayout());

        //adds day label
        Label dayLabel = new Label(dayofweek);
        dayLabel.setAlignment(Label.CENTER);
        top.add(dayLabel, BorderLayout.NORTH);

        //for the general weather data
        JPanel mainData = new JPanel();
        mainData.setLayout(new GridLayout(1,2));

        //Adds Image Icon
        JPanel imageIcon = new JPanel();
        ImageIcon todayIcon = new ImageIcon(todayWeather.getList().get(0).getIconPath().toString());
        imageIcon.add(new JLabel(todayIcon));
        imageIcon.setAlignmentX(Component.RIGHT_ALIGNMENT);
        mainData.add(imageIcon);

        //Adds Weather data
        JPanel weatherData = new JPanel();
        weatherData.setLayout(new BoxLayout(weatherData, 1));
        weatherData.add(new Label("High: " + high));
        weatherData.add(new Label("Low: " + low));
        weatherData.setAlignmentX(LEFT_ALIGNMENT);
        mainData.add(weatherData);

        top.add(mainData, BorderLayout.CENTER);	//centralises the data

        return top;
    }
}