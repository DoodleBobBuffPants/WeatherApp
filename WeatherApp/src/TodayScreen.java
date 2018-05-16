
import javax.swing.*;
import java.awt.*;

public class TodayScreen extends JPanel {


    public TodayScreen(){

        BorderLayout borderLayout = new BorderLayout();
        this.setLayout(borderLayout);
        add(createTop("Monday", 0f, 0f), BorderLayout.NORTH);
        add(createCentre(), BorderLayout.CENTER);
        add(createBottom(), BorderLayout.SOUTH);
    }

    private JPanel createBottom() {
        JPanel bottom = new JPanel();
        bottom.setLayout(new GridLayout(1,3));


        ImageIcon back = new ImageIcon("resources/cc3backbygoogle.png");
        JButton backbutton = new JButton(back);


        bottom.add(backbutton, 0);
        bottom.add(new Label("Button licensed under CC3 from google"));

        return bottom;
    }

    private JPanel createCentre() {
        JPanel center = new JPanel();
        center.setLayout(new GridLayout(8, 2));
        for (Integer i = 0; i < 8; i++){
            Label time = new Label((200 + 300*i) + "   ");
            time.setAlignment(Label.RIGHT);
            center.add(time, i);
            center.add(new Label(i.toString()), i);
        }
        return center;
    }

    private JPanel createTop(String dayofweek, float high, float low){
        //Creates JPanel to return
        JPanel top = new JPanel();
        top.setLayout(new BorderLayout());

        //Adds Day Label
        Label dayLabel = new Label(dayofweek);
        dayLabel.setAlignment(Label.CENTER);
        top.add(dayLabel, BorderLayout.NORTH);


        JPanel center = new JPanel();
        center.setLayout(new GridLayout(1,2));

        //Adds Image Icon
        JPanel imageIcon = new JPanel();

        ImageIcon todayIcon = new ImageIcon("resources/01d.png");
        imageIcon.add(new JLabel(todayIcon));
        imageIcon.setAlignmentX(Component.RIGHT_ALIGNMENT);
        center.add(imageIcon);

        //Adds Weather data
        JPanel weatherData = new JPanel();
        weatherData.setLayout(new BoxLayout(weatherData, 1));
        weatherData.add(new Label("High: " + high));
        weatherData.add(new Label("Low: " + low));
        weatherData.setAlignmentX(LEFT_ALIGNMENT);
        center.add(weatherData);


        top.add(center, BorderLayout.CENTER);

        return top;
    }

    public static void main(String[] args){
    	JFrame t = new JFrame("Today's weather");
        JPanel today = new TodayScreen();
        t.setContentPane(today);
        t.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        t.setSize(600, 800);
        t.setVisible(true);
    }
}
