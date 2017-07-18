import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * gui class for the authentication process,
 * sets up and display the gui and handles user input to provide
 * input to controller and return the output in suitable forms.
 * Created by Group 29D on 16/02/2017
 */
class AuthenticationGUI extends JFrame {
    private JComboBox<String> accessRightsBox;
    private JTextField userNameField;
    private JPasswordField pWordField;
    private AuthenticationController authenticator;
    private JFrame frame;
    private JPanel mainPanel;
    private JPanel buttonPanel;
    private GridBagConstraints constraints;

    /**
     * Constructor for GUI
     */
    AuthenticationGUI() {
        frame = new JFrame("Yuconz - Login");
        mainPanel = new JPanel(new GridBagLayout());
        buttonPanel = new JPanel(new GridBagLayout());
        constraints = new GridBagConstraints();
        authenticator = new AuthenticationController();
        setUp();
    }

    /**
     * Creates the gui window and it's content with use of other methods
     * once it is ready it makes the gui visible to the user.
     */
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
        JOptionPane.showMessageDialog(frame, "users all have the  password: password \n and the usernames " +
                        "are emp1, hre1, dir1, rev1 \n for employee, hremployee, director and reviewer respectively. " +
                        "\n\nThis is not part of the final system, It is just to aid in your marking.",
                "Marking Aid", JOptionPane.INFORMATION_MESSAGE);
    }

    private void populateMainPanel() {
        //username field to panel
        userNameField = new JTextField();
        makeConstraints(0, 0, 1);
        mainPanel.add(new JLabel("Username: "), constraints);
        makeConstraints(1, 0, 2);
        mainPanel.add(userNameField, constraints);

        //adding options to the combobox for the user to select the access right
        makeConstraints(0, 1, 1);
        mainPanel.add(new JLabel("Login as: "), constraints);
        accessRightsBox = new JComboBox<>();
        fillAccessRightValues();
        makeConstraints(1, 1, 2);
        mainPanel.add(accessRightsBox, constraints);

        //password field to panel
        pWordField = new JPasswordField();
        makeConstraints(0, 2, 1);
        mainPanel.add(new JLabel("Password: "), constraints);
        makeConstraints(1, 2, 2);
        mainPanel.add(pWordField, constraints);

        mainPanel.setBorder(new EmptyBorder(0, 10, 0, 0));
    }

    private void populateButtonPanel() {
        JButton loginButton = new JButton("Login");
        makeConstraints(0, 0, 1);
        buttonPanel.add(loginButton, constraints);

        JButton cancelButton = new JButton("Cancel");
        makeConstraints(1, 0, 1);
        buttonPanel.add(cancelButton, constraints);

        buttonPanel.setBorder(new EmptyBorder(0, 0, 10, 0));

        loginButton.addActionListener((ActionEvent e) -> {
            //gets the input values from the gui to be passed to the controller for checking
            String username = userNameField.getText();
            String pass = new String(pWordField.getPassword());
            int accessRightValue = accessRightsBox.getSelectedIndex();

            if (authenticator.login(username, pass, accessRightValue)) {
                frame.dispose();
                JOptionPane.showMessageDialog(null, "Welcome " + username +
                        ", to Yuconz Management System", "Yuconz - Login", JOptionPane.INFORMATION_MESSAGE);

                //redirects to PersonalDetailsMainGUI
                new HomepageGUI(authenticator);

            } else {
                JOptionPane.showMessageDialog(null, "Invalid username, access level or password" +
                        ", \nplease check and try again.", "Yuconz - Login", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e ->
                System.exit(0));
    }

    /**
     * Creates the menuBar for the gui and allocates it to the gui frame.
     */
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

        item = new JMenuItem("Marking Aid");
        item.addActionListener(e ->
                JOptionPane.showMessageDialog(frame, "users all have the  password: password \n and the " +
                        "usernames are emp1, hre1, dir1, rev1 \n for employee, hremployee, director and " +
                        "reviewer respectively. \n\nThis is not part of the final system, It is just to aid " +
                        "in your marking.", "Marking Aid", JOptionPane.INFORMATION_MESSAGE));
        menu.add(item);
        menuBar.add(menu);
    }

    /**
     * Gets values from the controller to populate the combobox with.
     */
    private void fillAccessRightValues() {
        accessRightsBox.addItem("Select login level...");
        ArrayList<String> values = authenticator.getARBoxValues();
        if (values == null) {
            throw new NullPointerException();
        } else {
            for (String x : values) {
                accessRightsBox.addItem(x);
            }
        }
    }

    private void makeConstraints(int gridX, int gridY, int gridWidth) {
        constraints.gridx = gridX;
        constraints.gridy = gridY;
        constraints.gridwidth = gridWidth;
    }

}
