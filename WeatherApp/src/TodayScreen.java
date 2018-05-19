//necessary imports
import javax.swing.*;
import javax.swing.border.EmptyBorder;

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
        this.add(createTop(todayWeather.getDayOfWeek(), getMaximumTemperature(), getMinimumTemperature()));
        this.add(createCentre());
        this.add(createBottom());
        
        this.setVisible(true);
        
    }

    private double getMinimumTemperature()
    {
        double minTemp = Double.POSITIVE_INFINITY;
        for (int i = 0; i < todayWeather.getList().size(); i++)
        {
            if(todayWeather.getList().get(i).getTemp_min() < minTemp)
                minTemp = todayWeather.getList().get(i).getTemp_min();
        }
        return minTemp;
    }

    private double getMaximumTemperature()
    {
        double maxTemp = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < todayWeather.getList().size(); i++)
        {
            if(todayWeather.getList().get(i).getTemp_min() > maxTemp)
                maxTemp = todayWeather.getList().get(i).getTemp_min();
        }
        return maxTemp;
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
        
        center.setLayout(new GridLayout(0, 5));	//structures the data
        
        //add times to panel, ignoring duplicate
        int index = (periodData.get(0).getTime() == periodData.get(periodData.size() - 1).getTime() ? periodData.size() - 1 : periodData.size());

        //String a = "Time\t\tWind Speed\t\tRain Amount\t\tTemperature\n";
        JLabel a;
        a = new JLabel("Time");
        a.setFont(new Font("charcoal",Font.BOLD, 23));
        a.setHorizontalAlignment(JLabel.CENTER);
        a.setVerticalAlignment(JLabel.CENTER);
        a.setBorder(new EmptyBorder(20, 10, 20, 10));
        center.add(a);

        a = new JLabel("Icon");
        a.setFont(new Font("charcoal",Font.BOLD, 23));
        a.setHorizontalAlignment(JLabel.CENTER);
        a.setVerticalAlignment(JLabel.CENTER);
        a.setBorder(new EmptyBorder(20, 10, 20, 10));
        center.add(a);

        a = new JLabel("Wind");
        a.setFont(new Font("charcoal", Font.BOLD, 23));
        a.setHorizontalAlignment(JLabel.CENTER);
        a.setVerticalAlignment(JLabel.CENTER);
        a.setBorder(new EmptyBorder(20, 10, 20, 10));
        center.add(a);
        a = new JLabel("Rain");
        a.setFont(new Font("charcoal",Font.BOLD, 23));
        a.setHorizontalAlignment(JLabel.CENTER);
        a.setVerticalAlignment(JLabel.CENTER);
        a.setBorder(new EmptyBorder(20, 10, 20, 10));
        center.add(a);
        a = new JLabel("Temp");
        a.setFont(new Font("charcoal", Font.BOLD, 23));
        a.setHorizontalAlignment(JLabel.CENTER);
        a.setVerticalAlignment(JLabel.CENTER);
        a.setBorder(new EmptyBorder(20, 10, 20, 10));
        center.add(a);


        for (int i = 0; i < index; i++){
            if(i > 0) {
                if (periodData.get(i).getTime().toString().equals(periodData.get(i - 1).getTime().toString()))
                    continue;
            }
            String data = periodData.get(i).getTime().toString() + "\t" + periodData.get(i).getWindSpeed() + " m/s" + "\t" + periodData.get(i).getRainAmount() + " mm" + "\t" + periodData.get(i).getTemp() + "°C";

            JLabel time = new JLabel(periodData.get(i).getTime().toString());
            time.setFont(new Font("charcoal", Font.ITALIC, 23));
            time.setHorizontalAlignment(JLabel.CENTER);
            time.setVerticalAlignment(JLabel.CENTER);
            center.add(time);

            JPanel imageIcon = new JPanel();
            imageIcon.setOpaque(false);
            ImageIcon todayIcon = new ImageIcon(periodData.get(i).getIconPath().toString());
            Image tiTemp = todayIcon.getImage();
            tiTemp = tiTemp.getScaledInstance(todayIcon.getIconWidth() / 2, todayIcon.getIconHeight() / 2, Image.SCALE_SMOOTH);
            imageIcon.add(new JLabel(new ImageIcon(tiTemp)), JLabel.CENTER);
            center.add(imageIcon);

            JLabel windSpeed = new JLabel(periodData.get(i).getWindSpeed() + "m/s");
            windSpeed.setFont(new Font("charcoal", Font.ITALIC, 23));
            windSpeed.setHorizontalAlignment(JLabel.CENTER);
            windSpeed.setVerticalAlignment(JLabel.CENTER);
            center.add(windSpeed);

            JLabel rainAmount = new JLabel(periodData.get(i).getRainAmount() + "mm");
            rainAmount.setFont(new Font("charcoal", Font.ITALIC, 23));
            rainAmount.setHorizontalAlignment(JLabel.CENTER);
            rainAmount.setVerticalAlignment(JLabel.CENTER);
            center.add(rainAmount);

            JLabel temp = new JLabel(periodData.get(i).getTemp() + "°C");
            temp.setFont(new Font("charcoal", Font.ITALIC, 23));
            temp.setHorizontalAlignment(JLabel.CENTER);
            temp.setVerticalAlignment(JLabel.CENTER);
            center.add(temp);
            
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
        ImageIcon todayIcon = new ImageIcon(todayWeather.getList().get(3).getIconPath().toString());
        Image tiTemp = todayIcon.getImage();
        tiTemp = tiTemp.getScaledInstance(todayIcon.getIconWidth() * 3, todayIcon.getIconHeight() * 3, Image.SCALE_SMOOTH);
        imageIcon.add(new JLabel(new ImageIcon(tiTemp)), BorderLayout.CENTER);
        imageIcon.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        imageIcon.setAlignmentY(JPanel.TOP_ALIGNMENT);
        mainData.add(imageIcon);

        //Adds Weather data
        JPanel weatherData = new JPanel();
        weatherData.setOpaque(false);
        weatherData.setLayout(new BoxLayout(weatherData, 1));
        JLabel hLabel = new JLabel("High: " + high + " °C");
        JLabel lLabel = new JLabel("Low: " + low + " °C");
        
        //adds windspeed
        double wind = todayWeather.getList().get(0).getWindSpeed();
        JLabel wLabel = new JLabel("Wind Speed: " + wind + " m/s");
        
        hLabel.setFont(new Font("charcoal", Font.BOLD | Font.ITALIC, 23));
        lLabel.setFont(new Font("charcoal", Font.BOLD | Font.ITALIC, 23));
        wLabel.setFont(new Font("charcoal", Font.BOLD | Font.ITALIC, 23));
        weatherData.add(hLabel);
        weatherData.add(lLabel);
        weatherData.add(wLabel);
        weatherData.setAlignmentX(LEFT_ALIGNMENT);
        mainData.add(weatherData);

        top.add(mainData, BorderLayout.CENTER);	//centralises the data

        return top;
    }
}