import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainScreen {
    private JButton settingButton;
    private JPanel panelMain;
    private JButton checkJourneyButton;
    private JButton todayButton;
    private JButton NextDay1Btn;
    private JButton NextDay2Btn;
    private JButton NextDay3Btn;
    private JButton NextDay4Btn;
    private JButton[] nextWeekBtns;
    private final String SETTINGS_ICON_PATH = "resources/settings-cog.png";
    private final String SUN_ICON_PATH = "resources/sun.png";
    private final String DAILY_ICON_PATH = "resources/DailyWeather.png";
    private final String TODAY_ICON_PATH = "resources/TodayWeather.png";
    private int CURRENT_DAY_OFFSET = 0;

    //Following three methods will take care of screen transitions
    private void launchSettingsScreen() {
    	
    }

    private void launchJourneyScreen() {
    	
    }

    private void launchDailyScreen(JButton btn) {
    	
    }

     //Turns background of Jbuttons transparent
    private void makeTransparent(JButton btn) {
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setOpaque(false);
    }

    private void addIcon(JButton btn, String imgPath) {
        try {
            Image icon = ImageIO.read(getClass().getResource(imgPath));
            btn.setIcon(new ImageIcon(icon));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MainScreen() {
        nextWeekBtns = new JButton[]{NextDay1Btn, NextDay2Btn, NextDay3Btn, NextDay4Btn};
        String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

        //Add click listeners
        settingButton.addActionListener(actionEvent -> launchSettingsScreen());
        checkJourneyButton.addActionListener(actionEvent -> launchJourneyScreen());
        for (JButton btn : nextWeekBtns) btn.addActionListener(actionEvent -> launchDailyScreen(btn));

        //Add image icons to each button
        addIcon(settingButton, SETTINGS_ICON_PATH);
        addIcon(todayButton, TODAY_ICON_PATH);
        //Display Icons for the next 4 days
        for (int i = 0; i < 4; i++) {
            JButton btn = nextWeekBtns[(i +CURRENT_DAY_OFFSET) % 7 ];
            String day = daysOfWeek[(i + CURRENT_DAY_OFFSET) % 7];

            addIcon(btn, DAILY_ICON_PATH);
            String temperature = "17";
            btn.setText(day + " - " + temperature);
            makeTransparent(btn);
            //Text goes above image
            btn.setHorizontalTextPosition(0);
            btn.setVerticalTextPosition(1);
        }

        //Make remaining icons transparent
        makeTransparent(settingButton);
        makeTransparent(todayButton);

    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("MainScreen");
        frame.setContentPane((new MainScreen()).panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

}
