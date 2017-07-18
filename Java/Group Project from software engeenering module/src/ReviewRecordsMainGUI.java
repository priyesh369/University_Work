import datastructures.Review;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * gui for displaying review records main page
 * Created by Group 29D on 19-Mar-17.
 */
public class ReviewRecordsMainGUI {
    private AuthenticationController authenticator;
    private ReviewController reviewController;
    private JFrame frame;
    private JPanel mainPanel;
    private JPanel buttonPanel;
    private GridBagConstraints constraints;

    ReviewRecordsMainGUI(AuthenticationController authenticator) {
        frame = new JFrame("Yuconz - Annual Reviews");
        mainPanel = new JPanel(new GridBagLayout());
        buttonPanel = new JPanel(new GridBagLayout());
        constraints = new GridBagConstraints();
        this.authenticator = authenticator;
        reviewController = new ReviewController();
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
        if (authenticator.getSession().getAccessLevel() == 1) {
            makeConstraints(0, 0, 4);
            mainPanel.add(new JLabel("You are in the 14 day review period."));
            makeConstraints(0, 1, 3);
            if(reviewController.checkForReviews()){
                //If this employee has already created a review

                mainPanel.add(new JLabel("Read review record: "), constraints);
                JButton readbtn = new JButton("Read review");
                readbtn.addActionListener((ActionEvent e) -> {
                    frame.dispose();
                    new ReviewRecordsReadGUI(authenticator);
                });
                makeConstraints(4, 1, 1);
                mainPanel.add(readbtn, constraints);

            } else {

                mainPanel.add(new JLabel("Create a new review record: "), constraints);
                JButton createbtn = new JButton("Create review");
                createbtn.addActionListener((ActionEvent e) -> {
                    frame.dispose();
                    new ReviewRecordsCreateGUI(authenticator);
                });
                makeConstraints(4, 1, 1);
                mainPanel.add(createbtn, constraints);
            }
        }
        else if (authenticator.getSession().getAccessLevel()==3){
            makeConstraints(0, 0, 4);
            mainPanel.add(new JLabel("The following reviews are awaiting reviewers:"));

            ArrayList<Review> values = reviewController.getReviews();
            int reviewDetailsPositionCounter = 1; //these are used to move where labels and buttons are added during the foreach loop
            int allocateButtonPositionCounter = 1; //these are used to move where labels and buttons are added during the foreach loop

            if (values == null) {
                throw new NullPointerException();
            } else {
                if (values.isEmpty()) {
                    makeConstraints(0, 0, 4);
                    mainPanel.add(new JLabel("No reviews requiring your attention."), constraints);
                }

                String reviewDetails;
                for (Review x : values){
                    reviewDetails = "<html>Review ID: " + x.getReviewID() + "<br>Name: " + x.getName() +"</html>";
                    makeConstraints(0, reviewDetailsPositionCounter, 2);
                    mainPanel.add(new JLabel(reviewDetails), constraints);
                    JButton btnAllocateReviewers = new JButton("Allocate Reviewers");
                    btnAllocateReviewers.addActionListener(e -> {
                        frame.dispose();
                        new HRAllocateReviewersGUI(authenticator, x.getReviewID());
                    });
                    makeConstraints(2, allocateButtonPositionCounter, 1);
                    mainPanel.add(btnAllocateReviewers, constraints);
                    reviewDetailsPositionCounter = reviewDetailsPositionCounter + 1;
                    allocateButtonPositionCounter = allocateButtonPositionCounter + 1;
                }


            }

        }else if (authenticator.getSession().getAccessLevel()==2){
            makeConstraints(0, 0, 4);
            mainPanel.add(new JLabel("The following reviews are pending:"));

            ArrayList<Review> values = reviewController.getAllocatedReviews();
            int reviewDetailsPositionCounter = 1; //these are used to move where labels and buttons are added during the foreach loop
            int allocateButtonPositionCounter = 1; //these are used to move where labels and buttons are added during the foreach loop

            if (values == null) {
                throw new NullPointerException();
            } else {
                if (values.isEmpty()) {
                    makeConstraints(0, 0, 4);
                    mainPanel.add(new JLabel("No reviews requiring your attention."), constraints);
                }

                String reviewDetails;
                for (Review x : values){
                    reviewDetails = "<html>Review ID: " + x.getReviewID() + "<br>Name: " + x.getName() +"</html>";
                    makeConstraints(0, reviewDetailsPositionCounter, 2);
                    mainPanel.add(new JLabel(reviewDetails), constraints);
                    JButton btnSignReview = new JButton("Fill Review");
                    btnSignReview.addActionListener(e -> {
                        frame.dispose();
                        new ReviewRevieweeGUI(authenticator, x.getReviewID());
                    });
                    makeConstraints(2, allocateButtonPositionCounter, 1);
                    mainPanel.add(btnSignReview, constraints);
                    reviewDetailsPositionCounter = reviewDetailsPositionCounter + 1;
                    allocateButtonPositionCounter = allocateButtonPositionCounter + 1;
                }


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
    }

    private void makeConstraints(int gridX, int gridY, int gridWidth) {
        constraints.gridx = gridX;
        constraints.gridy = gridY;
        constraints.gridwidth = gridWidth;
    }


}
