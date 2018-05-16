import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;

public class MainScreenNew extends JPanel {
    private static WeatherInformationParsed wiP;
    //Placeholder Data
    private final String SETTINGS_ICON_PATH = "settings-cog.png";
    private final String SUN_ICON_PATH = "resources/cc3backbygoogle.png";
    private final String DAILY_ICON_PATH = "resources/cc3backbygoogle.png";
    private final String TODAY_ICON_PATH = "resources/cc3backbygoogle.png";
    private JButton settingButton;
    private JButton checkJourneyButton;
    private JButton todayButton;
    private JButton[] nextWeekBtns;
    private int CURRENT_DAY_OFFSET = 0;


    private MainScreenNew(int SCREEN_WIDTH, int SCREEN_HEIGHT) {
        final int WIDTH_CENTER = SCREEN_WIDTH / 2;
        setLayout(null);
        createButtons();
        settingButton.setBounds(SCREEN_WIDTH - 50, 0, 50, 50);
        add(settingButton);

        int checkButtonWidth = SCREEN_WIDTH / 2;
        checkJourneyButton.setBounds(WIDTH_CENTER - checkButtonWidth / 2, SCREEN_HEIGHT / 8, checkButtonWidth, SCREEN_HEIGHT / 4);
        add(checkJourneyButton);

        todayButton.setBounds(WIDTH_CENTER - SCREEN_WIDTH / 4, 3 * SCREEN_HEIGHT / 8, SCREEN_WIDTH / 2, SCREEN_HEIGHT / 4);
        add(todayButton);

        for (int i = 0; i < nextWeekBtns.length; i++) {
            JButton currentBtn = nextWeekBtns[i];
            currentBtn.setBounds(i * SCREEN_WIDTH / 4, 3 * SCREEN_HEIGHT / 4, SCREEN_WIDTH / 4, SCREEN_HEIGHT / 4);
            add(currentBtn);
        }


    }

    public static void main(String[] args) {
        JFrame t = new JFrame("MainScreenNew");
        t.setSize(800, 600);
        JPanel today = new MainScreenNew(t.getWidth(), t.getHeight());
        t.setContentPane(today);
        t.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        t.setVisible(true);
    }

    private void createButtons() {
        settingButton = new JButton();
        addIcon(settingButton, SETTINGS_ICON_PATH);

        checkJourneyButton = new JButton();
        checkJourneyButton.setText("Check Journey");

        todayButton = new JButton();
        todayButton.setHorizontalTextPosition(SwingConstants.CENTER);
        todayButton.setText("Today's Weather");
        nextWeekBtns = new JButton[4];
        for (int i = 0; i < nextWeekBtns.length; i++) {
            nextWeekBtns[i] = new JButton();
        }
    }

    private void addIcon(JButton btn, String imgPath) {
        try {
            Image icon = ImageIO.read(getClass().getResource(imgPath));
            btn.setIcon(new ImageIcon(icon));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
