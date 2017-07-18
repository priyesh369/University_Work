import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
/**
 * gui for displaying a Homepage once loggedin
 * Created by Group 29D on 19-Mar-17.
 */
class HomepageGUI {
    private User user;
    private JFrame frame;
    private JPanel mainPanel;
    private JPanel buttonPanel;
    private GridBagConstraints constraints;
    private AuthenticationController authenticator;

    HomepageGUI(AuthenticationController authenticator){
        frame = new JFrame("Yuconz - Homepage");
        mainPanel = new JPanel(new GridBagLayout());
        buttonPanel = new JPanel(new GridBagLayout());
        constraints = new GridBagConstraints();
        this.authenticator = authenticator;
        setUp();
    }

    private void setUp() {
        Container contentArea = frame.getContentPane();
        contentArea.setLayout(new BorderLayout());

        //constraints that are common for all items
        constraints.insets = new Insets(3, 3, 3, 3);
        constraints.fill = GridBagConstraints.HORIZONTAL;

        makeMenu();
        populateMainPanel();
        populateButtonPanel();

        contentArea.add(mainPanel, BorderLayout.CENTER);
        contentArea.add(buttonPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setMinimumSize(new Dimension(300, 200));
        frame.setLocationRelativeTo(null);//setting frame to the center of the screen
        frame.setVisible(true);
    }

    private void populateMainPanel() {

        makeConstraints(0, 0, 4);
        mainPanel.add(new JLabel("Welcome, " + authenticator.getSession().getUserName() +"."), constraints);

        makeConstraints(0, 1, 3);
        mainPanel.add(new JLabel("Go to personal details: "), constraints);
        JButton pdButton = new JButton("Personal Details");
        pdButton.addActionListener((ActionEvent e) -> {
            frame.dispose();
            new PersonalDetailsMainGUI(authenticator);
        });
        makeConstraints(4, 1, 1);
        mainPanel.add(pdButton, constraints);

        makeConstraints(0, 3, 3);
        mainPanel.add(new JLabel("Go to annual reviews: "), constraints);
        JButton annRevButton = new JButton("Annual Reviews");
        annRevButton.addActionListener((ActionEvent e) -> {
            frame.dispose();
            new ReviewRecordsMainGUI(authenticator);
        });
        makeConstraints(4, 3, 1);
        mainPanel.add(annRevButton, constraints);
    }

    private void populateButtonPanel() {
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener((ActionEvent e) -> {
            if(authenticator.logout()){
                frame.dispose();
                new AuthenticationGUI();
            }else{
                System.out.println("Logout Failed");
            }
                });
        makeConstraints(0, 0, 1);
        buttonPanel.add(logoutButton, constraints);

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener((ActionEvent e) -> System.exit(0));
        makeConstraints(2, 0, 1);
        buttonPanel.add(quitButton, constraints);
    }


    private void makeMenu() {
        //creates and populates a menuBar with a file and help which contain quit and about respectively
        JMenuBar menuBar = new JMenuBar();
        JMenu menu;
        JMenuItem item;

        frame.setJMenuBar(menuBar);

        menu = new JMenu("File");

        item = new JMenuItem("Quit");
        item.addActionListener(e ->
                System.exit(0));
        menu.add(item);
        menuBar.add(menu);

        menu = new JMenu("Help");

        item = new JMenuItem("About");
        item.addActionListener(e ->
                JOptionPane.showMessageDialog(frame, "Created by Group 29D.", "About",
                        JOptionPane.INFORMATION_MESSAGE));
        menu.add(item);
        menuBar.add(menu);
    }

    private void makeConstraints(int gridX, int gridY, int gridWidth) {
        constraints.gridx = gridX;
        constraints.gridy = gridY;
        constraints.gridwidth = gridWidth;
    }
}
