//necessary imports
import javax.swing.*;

import java.awt.*;
import java.util.Collections;
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
        Image bTemp = back.getImage();
        bTemp = bTemp.getScaledInstance(back.getIconWidth() * 2, back.getIconHeight() * 2, Image.SCALE_SMOOTH);
        JButton backbutton = new JButton(new ImageIcon(bTemp));
        
        backbutton.setBorderPainted(false);
        backbutton.setOpaque(false);
        backbutton.setContentAreaFilled(false);
        backbutton.setHorizontalAlignment(JButton.LEFT);
        
        backbutton.addActionListener(actionEvent -> backToMain());

        bottom.add(backbutton);

        return bottom;
    }

    private JPanel createCentre() {
    	//panel for data
        JPanel center = new JPanel();
        center.setOpaque(false);
        List<weatherForAThreeHourlyPeriod> periodData = todayWeather.getList();	//list of all information needed
        
        Collections.sort(periodData, (d1, d2) -> d1.getTime().getHour() - d2.getTime().getHour());	//sorts the data by time
        
        center.setLayout(new GridLayout(periodData.size(), 2));	//structures the data
        
        //add times to panel, ignoring duplicate
        int index = (periodData.get(0).getTime() == periodData.get(periodData.size() - 1).getTime() ? periodData.size() - 1 : periodData.size());
        
        for (int i = 0; i < index; i++){
        	
        	String data = periodData.get(i).getTime().toString() + "    " + periodData.get(i).getTemp() + " °C";
            JLabel time = new JLabel(data);
            time.setFont(new Font("charcoal", Font.BOLD | Font.ITALIC, 23));
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
        top.setLayout(new GridLayout(2, 1));

        //adds day label
        JLabel dayLabel = new JLabel(dayofweek);
        dayLabel.setFont(new Font("charcoal", Font.BOLD | Font.ITALIC, 23));
        dayLabel.setSize(dayLabel.getWidth(), dayLabel.getHeight() * 2);
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
        Image tiTemp = todayIcon.getImage();
        tiTemp = tiTemp.getScaledInstance(todayIcon.getIconWidth() * 3, todayIcon.getIconHeight() * 3, Image.SCALE_SMOOTH);
        imageIcon.add(new JLabel(new ImageIcon(tiTemp)), BorderLayout.NORTH);
        imageIcon.setAlignmentX(CENTER_ALIGNMENT);
        imageIcon.setAlignmentY(TOP_ALIGNMENT);
        mainData.add(imageIcon);

        //Adds Weather data
        JPanel weatherData = new JPanel();
        weatherData.setOpaque(false);
        weatherData.setLayout(new BoxLayout(weatherData, 1));
        JLabel hLabel = new JLabel("High: " + high + " °C");
        JLabel lLabel = new JLabel("Low: " + low + " °C");
        hLabel.setFont(new Font("charcoal", Font.BOLD | Font.ITALIC, 23));
        lLabel.setFont(new Font("charcoal", Font.BOLD | Font.ITALIC, 23));
        weatherData.add(hLabel);
        weatherData.add(lLabel);
        weatherData.setAlignmentX(LEFT_ALIGNMENT);
        mainData.add(weatherData);

        top.add(mainData, BorderLayout.CENTER);	//centralises the data

        return top;
    }
}