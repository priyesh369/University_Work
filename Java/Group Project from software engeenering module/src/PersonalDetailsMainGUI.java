import datastructures.PDEmployee;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * gui for displaying Personal Details
 * Created by Group 29D on 02-Mar-17.
 */
class PersonalDetailsMainGUI {
    private JFrame frame;
    private JPanel mainPanel;
    private JPanel buttonPanel;
    private JButton btnCreate;
    private GridBagConstraints constraints;
    private AuthenticationController authenticator;;
    private PersonalDetailsController personalDetailsController;

    PersonalDetailsMainGUI(AuthenticationController authenticator) {
        constraints = new GridBagConstraints();
        frame = new JFrame("Yuconz - Personal Details");
        mainPanel = new JPanel(new GridBagLayout());
        buttonPanel = new JPanel(new GridBagLayout());
        this.authenticator = authenticator;

        personalDetailsController = new PersonalDetailsController();
        setup();
    }

    private void setup() {
        Container contentArea = frame.getContentPane();
        contentArea.setLayout(new BorderLayout());

        //constraints that are common for all items
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(3, 3, 3, 3);

        makeMenu();
        populateMainPanel();
        populateButtonPanel();

        contentArea.add(mainPanel, BorderLayout.NORTH);


        contentArea.add(buttonPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setMinimumSize(new Dimension(550, 200));
        frame.setLocationRelativeTo(null);//setting frame to the center of the screen
        frame.setVisible(true);
    }

    private void populateMainPanel() {
        ArrayList<PDEmployee> values = personalDetailsController.getData();
        int employeeDetailsPositionCounter = 0; //these are used to move where labels and buttons are added during the foreach loop
        int amendButtonPositionCounter = 0; //these are used to move where labels and buttons are added during the foreach loop
        int readButtonPositionCounter = 1; //these are used to move where labels and buttons are added during the foreach loop
        if (values == null) {
            throw new NullPointerException();
        } else {
            if (values.isEmpty()) {
                setConstraints(0, 0, 2, 2);
                if (authenticator.getSession().getAccessLevel() == 3) {
                    mainPanel.add(new JLabel("No records found."), constraints);
                } else {
                    mainPanel.add(new JLabel("Your personal details have not been found, please contact HR."), constraints);
                }
            }
            String employeeDetails;
            for (PDEmployee x : values) {
                int staffNo = Integer.parseInt(x.getStaffId());
                employeeDetails = "<html>Staff ID: " + x.getStaffId() + "<br>Name: " + x.getName() + " " + x.getSurname() + "</html>";

                setConstraints(0, employeeDetailsPositionCounter, 2, 2);
                mainPanel.add(new JLabel(employeeDetails), constraints);

                btnCreate = new JButton("Create Record");

                JButton btnAmend = new JButton("Amend");
                btnAmend.addActionListener(e -> {
                    frame.dispose();
                    new PersonalDetailsAmendGUI(btnAmend.getActionCommand(), authenticator);
                });
                btnAmend.setActionCommand(x.getStaffId());

                JButton btnRead = new JButton("Read");
                btnRead.addActionListener(e -> new PersonalDetailsReadGUI(btnRead.getActionCommand()));
                btnRead.setActionCommand(x.getStaffId());

                setConstraints(2, amendButtonPositionCounter, 1, 1);
                mainPanel.add(btnAmend, constraints);

                setConstraints(2, readButtonPositionCounter, 1, 1);
                mainPanel.add(btnRead, constraints);

                if (authenticator.getSession().getStaffID() == staffNo) {
                    btnRead.setEnabled(true);
                    btnAmend.setEnabled(true);
                } else if (authenticator.getSession().getAccessLevel() == 3) {
                    btnCreate.setEnabled(true);
                    btnAmend.setEnabled(true);
                    btnRead.setEnabled(true);
                } else if (authenticator.getSession().getAccessLevel() == 4) {
                    btnCreate.setEnabled(false);
                    btnAmend.setEnabled(true);
                    btnRead.setEnabled(true);
                } else {
                    btnCreate.setEnabled(false);
                    btnRead.setEnabled(false);
                    btnAmend.setEnabled(false);
                }

                employeeDetailsPositionCounter = employeeDetailsPositionCounter + 2;
                amendButtonPositionCounter = amendButtonPositionCounter + 2;
                readButtonPositionCounter = readButtonPositionCounter + 2;

            }
        }
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

        item = new JMenuItem("Logout");
        item.addActionListener(e -> {
            if(authenticator.logout()){
                frame.dispose();
                new AuthenticationGUI();
            }else{
                System.out.println("Logout Failed");
            }
        });
        menuBar.add(item);
    }

    private void populateButtonPanel() {
        btnCreate.addActionListener(e -> {
            frame.dispose();
            new PersonalDetailsCreateGUI(authenticator);
        });
        setConstraints(1, 0, 1, 1);
        buttonPanel.add(btnCreate, constraints);

        JButton back = new JButton("Back Home");
        back.addActionListener(e -> {
            frame.dispose();
            new HomepageGUI(authenticator);
        });
        setConstraints(5, 0, 1, 1);
        buttonPanel.add(back, constraints);
    }

    private void setConstraints(int gridX, int gridY, int gridWidth, int gridHeight) {
        constraints.gridx = gridX;
        constraints.gridy = gridY;
        constraints.gridwidth = gridWidth;
        constraints.gridheight = gridHeight;
    }

}
