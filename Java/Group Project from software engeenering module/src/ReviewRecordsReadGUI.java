
import com.toedter.calendar.JDateChooser;
import datastructures.AREmployee;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by Group 29D on 18/03/2017.
 */
public class ReviewRecordsReadGUI {
    private AuthenticationController authenticator;
    private ReviewController reviewController = new ReviewController();
    private JFrame frame;
    private JPanel mainPanel;
    private JPanel buttonPanel;
    private JButton btnCreate = new JButton();
    private GridBagConstraints constraints;

    //TextFields
    private JTextField txtStaffNo = new JTextField();
    private JTextField txtName = new JTextField();
    private JTextField txtDirector = new JTextField();
    private JTextField txtDircetor2 = new JTextField();
    private JTextField txtSection = new JTextField();
    private JTextField txtJobtitle = new JTextField();
    private JTextArea txtSummary = new JTextArea();
    private JTextArea txtComments = new JTextArea();
    private JDateChooser txtRev1sign = new JDateChooser();
    private JDateChooser txtRev2sign = new JDateChooser();
    private JDateChooser txtEmpsign = new JDateChooser();
    private JComboBox txtRecommendation = new JComboBox();

    ReviewRecordsReadGUI(AuthenticationController authenticator) {
        frame = new JFrame("Yuconz - Read Annual Review");
        mainPanel = new JPanel(new GridBagLayout());
        buttonPanel = new JPanel(new GridBagLayout());
        constraints = new GridBagConstraints();
        this.authenticator = authenticator;
        setup();
    }

    private void setup() {
        Container contentArea = frame.getContentPane();
        contentArea.setLayout(new BorderLayout());

        //constraints that are common for all items
        constraints.insets = new Insets(3, 3, 3, 3);
        constraints.fill = GridBagConstraints.HORIZONTAL;

        // makeMenu();
        populateMainPanel();
        populateButtonPanel();

        contentArea.add(mainPanel, BorderLayout.CENTER);
        contentArea.add(buttonPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setMinimumSize(new Dimension(550, 200));
        frame.setLocationRelativeTo(null);//setting frame to the center of the screen
        frame.setVisible(true);
    }

    private void populateMainPanel() {

        AREmployee employee = reviewController.readReviewRecord();

        int labelWeight = 0;
        int fieldWeight = 1;
        int gridYValue = 0;

        //staff no to panel
        setConstraints(0, gridYValue, labelWeight);
        mainPanel.add(new JLabel("StaffNo:"), constraints);
        setConstraints(1, gridYValue, fieldWeight);
        String theUser = Integer.toString(authenticator.getSession().getStaffID());
        txtStaffNo.setText(theUser);
        txtStaffNo.setEditable(false);
        mainPanel.add(txtStaffNo, constraints);
        gridYValue++;

        //name to panel
        setConstraints(0, gridYValue, labelWeight);
        mainPanel.add(new JLabel("Name:"), constraints);
        setConstraints(1, gridYValue, fieldWeight);
        txtName.setText(employee.getName());
        txtName.setEditable(false);
        mainPanel.add(txtName, constraints);
        gridYValue++;

        //manager/director 1 to panel
        setConstraints(0, gridYValue, labelWeight);
        mainPanel.add(new JLabel("Manager/Director:"), constraints);
        setConstraints(1, gridYValue, fieldWeight);
        txtDirector.setText(employee.getRev1name());
        txtDirector.setEditable(false);
        mainPanel.add(txtDirector, constraints);
        gridYValue++;

        //manager/director 2 to panel
        setConstraints(0, gridYValue, labelWeight);
        mainPanel.add(new JLabel(" Second Manager/Director:"), constraints);
        setConstraints(1, gridYValue, fieldWeight);
        txtDircetor2.setText(employee.getRev2name());
        txtDircetor2.setEditable(false);
        mainPanel.add(txtDircetor2, constraints);
        gridYValue++;

        //Section to panel
        setConstraints(0, gridYValue, labelWeight);
        mainPanel.add(new JLabel("Section:"), constraints);
        setConstraints(1, gridYValue, fieldWeight);
        txtSection.setText(employee.getSection());
        txtSection.setEditable(false);
        mainPanel.add(txtSection, constraints);
        gridYValue++;

        //Job title to panel
        constraints.gridwidth = 1;
        setConstraints(0, gridYValue, labelWeight);
        mainPanel.add(new JLabel("Job Title:"), constraints);
        setConstraints(1, gridYValue, fieldWeight);
        txtJobtitle.setText(employee.getJobTitle());
        txtJobtitle.setEditable(false);
        mainPanel.add(txtJobtitle, constraints);
        gridYValue++;
        //perfomance review

        //Performance Review to panel
        constraints.gridwidth = 1;
        setConstraints(0, gridYValue, labelWeight);
        mainPanel.add(new JLabel("Performance Summary:"), constraints);
        setConstraints(1, gridYValue, fieldWeight);
        txtSummary.setPreferredSize(new Dimension(450, 90));
        txtSummary.setText(employee.getSummary());
        txtSummary.setEditable(false);
        mainPanel.add(txtSummary, constraints);
        gridYValue++;

        // future performance goals/ planned outcomes
        setConstraints(0, gridYValue, labelWeight);
        mainPanel.add(new JLabel("Add Goals:"), constraints);
        setConstraints(1, gridYValue, fieldWeight);
        JButton goalButton = new JButton("add goal");
        goalButton.addActionListener((ActionEvent e) -> {
            String S = (String)JOptionPane.showInputDialog(frame, "Enter Goal: ", "Review Goal",JOptionPane.PLAIN_MESSAGE,null,null,null);
            //reviewController.addGoal();
        });
        mainPanel.add(goalButton, constraints);
        gridYValue++;

        //Reviewer comments to panel
        constraints.gridwidth = 1;
        setConstraints(0, gridYValue, labelWeight);
        mainPanel.add(new JLabel("Reviewer Comments:"), constraints);
        setConstraints(1, gridYValue, fieldWeight);
        txtComments.setPreferredSize(new Dimension(450, 90));
        txtComments.setText(employee.getComments());
        txtComments.setEditable(false);
        mainPanel.add(txtComments, constraints);
        gridYValue++;

        //Recommendation to panel
        constraints.gridwidth = 1;
        setConstraints(0, gridYValue, labelWeight);
        mainPanel.add(new JLabel("Recommendation"), constraints);
        setConstraints(1, gridYValue, fieldWeight);
        txtRecommendation.addItem("Stay in Post");
        txtRecommendation.addItem("Salary Increase");
        txtRecommendation.addItem("Promotion");
        txtRecommendation.addItem("Probation");
        txtRecommendation.addItem("Termination");
        txtRecommendation.setEditable(false);
        mainPanel.add(txtRecommendation, constraints);
        gridYValue++;

        //Reviewee Signature to panel
        constraints.gridwidth = 1;
        setConstraints(0, gridYValue, labelWeight);
        mainPanel.add(new JLabel("Reviewee Signature:"), constraints);
        setConstraints(1, gridYValue, fieldWeight);
        txtEmpsign.setDateFormatString("YYYY / MM / dd");
        txtEmpsign.setEnabled(false);
        mainPanel.add(txtEmpsign, constraints);
        gridYValue++;

        //Manager/Director Signature to panel
        constraints.gridwidth = 1;
        setConstraints(0, gridYValue, labelWeight);
        mainPanel.add(new JLabel("Manager/Director Signature:"), constraints);
        setConstraints(1, gridYValue, fieldWeight);
        txtRev1sign.setDateFormatString("YYYY / MM / dd");
        txtRev1sign.setEnabled(false);
        mainPanel.add(txtRev1sign, constraints);
        gridYValue++;

        //2nd Manager/Director Signature to panel
        constraints.gridwidth = 1;
        setConstraints(0, gridYValue, labelWeight);
        mainPanel.add(new JLabel("Second Manager/Director Signature:"), constraints);
        setConstraints(1, gridYValue, fieldWeight);
        txtRev2sign.setDateFormatString("YYYY / MM / dd");
        txtRev2sign.setEnabled(false);
        mainPanel.add(txtRev2sign, constraints);

    }

    private void populateButtonPanel() {
        JButton btnSave = new JButton("Save");
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.weightx = 0;
        buttonPanel.add(btnSave, constraints);
        JButton btnCancel = new JButton("Cancel");
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 0;
        constraints.gridwidth = 1;
        buttonPanel.add(btnCancel, constraints);
        buttonPanel.setBorder(new EmptyBorder(0, 0, 10, 0));

        btnCancel.addActionListener(e -> {

            frame.dispose();
            //redirects to createPD (will change it with the correct access right
            new ReviewRecordsMainGUI(authenticator);
        });

        btnSave.addActionListener((ActionEvent e) -> {
            //gets the input values from the gui to be passed to the controller for checking to add to the database
            Integer staffId = null;
            try {
                staffId = Integer.parseInt(txtStaffNo.getText());
            } catch (Exception ex) {
                ex.getMessage();
            }
            String name = txtName.getText();
            String reveiwer1 = txtDirector.getText();
            String reveiwer2 = txtDircetor2.getText();
            String section = txtSection.getText();
            String jobtitle = txtJobtitle.getText();
            String summary = txtSummary.getText();
            String comments = txtComments.getText();

            reviewController.createReviewRecord(staffId,
                    name,
                    reveiwer1,
                    reveiwer2,
                    section,
                    jobtitle,
                    summary,
                    comments
            );
            JOptionPane.showMessageDialog(null, " successfully saved", "Yuconz - Create Review", JOptionPane.INFORMATION_MESSAGE);


            frame.dispose();
            //redirects to homepage
            new HomepageGUI(authenticator);

        });
    }

    private void setConstraints(int gridX, int gridY, int weightX) {
        constraints.gridx = gridX;
        constraints.gridy = gridY;
        constraints.weightx = weightX;
        constraints.gridwidth = 1;
    }
}

