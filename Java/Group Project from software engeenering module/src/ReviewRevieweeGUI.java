import datastructures.Review;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by SSJ on 26-Mar-17.
 */
public class ReviewRevieweeGUI {
    private AuthenticationController authenticator;
    private JFrame frame;
    private JPanel mainPanel;
    private JPanel buttonPanel;
    private GridBagConstraints constraints;
    private ReviewController reviewController;
    private int reviewID;

    ReviewRevieweeGUI(AuthenticationController authenticator, int reviewID){
        frame = new JFrame("Yuconz - Annual Reviews");
        mainPanel = new JPanel(new GridBagLayout());
        buttonPanel = new JPanel(new GridBagLayout());
        constraints = new GridBagConstraints();
        this.authenticator = authenticator;
        reviewController = new ReviewController();
        this.reviewID =reviewID;
        setup();
    }
    private void setup(){
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
        ArrayList<Review> values = reviewController.getAllocatedReviews();
        int employeeDetailsPositionCounter = 0; //these are used to move where labels and buttons are added during the foreach loop
        int amendButtonPositionCounter = 0; //these are used to move where labels and buttons are added during the foreach loop
        int readButtonPositionCounter = 1; //these are used to move where labels and buttons are added during the foreach loop
        if (values == null) {
            throw new NullPointerException();
        } else {
            if (values.isEmpty()) {
                setConstraints(0, 0, 2, 2);
                mainPanel.add(new JLabel("ERROR: No reviews have been allocated to you yet."), constraints);
            }
            String employeeDetails;
            for (Review x : values) {
                int staffNo = Integer.parseInt(x.getStaffID());
                employeeDetails = "<html>Staff ID: " + staffNo + "<br>Name: " + x.getName() +  "</html>";
                ;

                setConstraints(0, employeeDetailsPositionCounter, 2, 2);
                mainPanel.add(new JLabel(employeeDetails), constraints);


                JButton btnAllocate = new JButton("Allocate Reviewer");
                btnAllocate.addActionListener(e -> {
                    if (!reviewController.saveReviewer(Integer.parseInt(btnAllocate.getActionCommand()))) {
                        btnAllocate.setText("First Allocated");
                        btnAllocate.setEnabled(false);
                    } else {
                        btnAllocate.setText("Second Allocated");
                        btnAllocate.setEnabled(false);
                    }
                });
                btnAllocate.setActionCommand(x.getStaffID());

                setConstraints(2, readButtonPositionCounter, 1, 1);
                mainPanel.add(btnAllocate, constraints);

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
            if (authenticator.logout()) {
                frame.dispose();
                new AuthenticationGUI();
            } else {
                System.out.println("Logout Failed");
            }
        });
        menuBar.add(item);
    }

    private void populateButtonPanel() {
        JButton back = new JButton("Back Home");
        back.addActionListener(e -> {
            frame.dispose();
            new HomepageGUI(authenticator);
        });
        makeConstraints(1, 0, 1);
        buttonPanel.add(back, constraints);
        JButton btnAllocateReviewers = new JButton("Confirm Reviewers");
        btnAllocateReviewers.addActionListener(e -> {
            reviewController.allocateReviewers(reviewID);
            frame.dispose();
            new ReviewRecordsMainGUI(authenticator);

        });
        makeConstraints(2, 0, 1);
        buttonPanel.add(btnAllocateReviewers, constraints);
        // HR to allocate the reviewers
        if (authenticator.getSession().getAccessLevel() == 3) {
            btnAllocateReviewers.setEnabled(true);
        } else {
            btnAllocateReviewers.setEnabled(false);
        }
    }

    private void makeConstraints(int gridX, int gridY, int gridWidth) {
        constraints.gridx = gridX;
        constraints.gridy = gridY;
        constraints.gridwidth = gridWidth;
    }

    private void setConstraints(int gridX, int gridY, int gridWidth, int gridHeight) {
        constraints.gridx = gridX;
        constraints.gridy = gridY;
        constraints.gridwidth = gridWidth;
        constraints.gridheight = gridHeight;
    }
}
