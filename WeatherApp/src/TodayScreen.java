//necessary imports
import javax.swing.*;
import javax.swing.border.MatteBorder;

import java.awt.*;
import java.util.List;

public class TodayScreen extends JPanel {

	private MainScreen returnPanel;
	private weatherForADay todayWeather;
	
    public TodayScreen(MainScreen returnPanel, weatherForADay weather) {
    	
    	//initialise variables
    	this.returnPanel = returnPanel;
		this.todayWeather = weather;
		 
		this.setOpaque(false);	//allows background
		
        this.setLayout(new GridLayout(3, 1));	//set layout
        
        this.setSize(600,  800);
        
        //add elements
        this.add(createTop(todayWeather.getDayOfWeek(), todayWeather.getList().get(0).getTemp_max(), todayWeather.getList().get(0).getTemp_min()));
        this.add(createCentre());
        this.add(createBottom());
        
        this.setVisible(true);
        
    }
    
    private void backToMain() {
    	
    	//switch screens
    	returnPanel.panelMain.setVisible(true);
    	returnPanel.add(returnPanel.panelMain);
    	this.setVisible(false);
    }

    private JPanel createBottom() {
    	
    	//bottom to return to main screen
        JPanel bottom = new JPanel();
        bottom.setOpaque(false);
        bottom.setLayout(new GridLayout(1, 1));

        ImageIcon back = new ImageIcon("resources/cc3backbygoogle.png");
        JButton backbutton = new JButton(back);
        
        backbutton.setBorder(new MatteBorder(0, 2, 2, 0, new Color(198, 240, 254)));
        backbutton.setBorderPainted(true);
        backbutton.setOpaque(false);
        backbutton.setContentAreaFilled(false);
        
        backbutton.addActionListener(actionEvent -> backToMain());

        bottom.add(backbutton);

        return bottom;
    }

    private JPanel createCentre() {
    	//panel for data
        JPanel center = new JPanel();
        center.setOpaque(false);
        List<weatherForAThreeHourlyPeriod> periodData = todayWeather.getList();	//list of all information needed
        
        center.setLayout(new GridLayout(periodData.size(), 2));	//structures the data
        
        //add times to panel, ignoring duplicate
        int index = (periodData.get(0).getTime() == periodData.get(periodData.size() - 1).getTime() ? periodData.size() - 1 : periodData.size());
        
        for (int i = 0; i < index; i++){
        	
        	String data = periodData.get(i).getTime().toString() + "    " + periodData.get(i).getTemp();
            JLabel time = new JLabel(data);
            time.setHorizontalAlignment(JLabel.CENTER);
            time.setVerticalAlignment(JLabel.CENTER);
            center.add(time);
            
        }
        
        return center;
    }

    private JPanel createTop(String dayofweek, double high, double low) {
    	
        //creates top panel
        JPanel top = new JPanel();
        top.setOpaque(false);
        top.setLayout(new BorderLayout());

        //adds day label
        JLabel dayLabel = new JLabel(dayofweek);
        dayLabel.setHorizontalAlignment(JLabel.CENTER);
        dayLabel.setVerticalAlignment(JLabel.CENTER);
        top.add(dayLabel, BorderLayout.NORTH);

        //for the general weather data
        JPanel mainData = new JPanel();
        mainData.setOpaque(false);
        mainData.setLayout(new GridLayout(1,2));

        //Adds Image Icon
        JPanel imageIcon = new JPanel();
        imageIcon.setOpaque(false);
        ImageIcon todayIcon = new ImageIcon(todayWeather.getList().get(0).getIconPath().toString());
        imageIcon.add(new JLabel(todayIcon));
        imageIcon.setAlignmentX(Component.RIGHT_ALIGNMENT);
        mainData.add(imageIcon);

        //Adds Weather data
        JPanel weatherData = new JPanel();
        weatherData.setOpaque(false);
        weatherData.setLayout(new BoxLayout(weatherData, 1));
        weatherData.add(new JLabel("High: " + high));
        weatherData.add(new JLabel("Low: " + low));
        weatherData.setAlignmentX(LEFT_ALIGNMENT);
        mainData.add(weatherData);

        top.add(mainData, BorderLayout.CENTER);	//centralises the data

        return top;
    }
}