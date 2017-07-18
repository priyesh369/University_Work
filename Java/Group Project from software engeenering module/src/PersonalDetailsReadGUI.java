import com.toedter.calendar.JDateChooser;
import datastructures.PDEmployee;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * gui for crating Personal Details
 * Created by Group 29D on 02-Mar-17.
 */
class PersonalDetailsReadGUI extends JFrame {
    private JFrame frame;
    //TextFields
    private JTextField txtStaffNo = new JTextField();
    private JTextField txtSurname = new JTextField();
    private JTextField txtName = new JTextField();
    private JDateChooser txtDob = new JDateChooser();
    private JTextField txtAddress = new JTextField();
    private JTextField txtTown = new JTextField();
    private JTextField txtCounty = new JTextField();
    private JTextField txtPostcode = new JTextField();
    private JTextField txtTelNo = new JTextField();
    private JTextField txtMobileNo = new JTextField();
    private JTextField txtEmergencyContact = new JTextField();
    private JTextField txtEmergencyContactNo = new JTextField();

    private GridBagConstraints constraints;
    private JPanel buttonPanel;
    private JPanel mainPanel;
    //controller
    private PersonalDetailsController personalDetailsController;

    /**
     * constructor
     *
     * @param staffId the staff ID of the employee personal details to amend
     */
    PersonalDetailsReadGUI(String staffId) {
        constraints = new GridBagConstraints();
        frame = new JFrame("Yuconz - Personal Details");
        mainPanel = new JPanel(new GridBagLayout());
        buttonPanel = new JPanel(new GridBagLayout());

        personalDetailsController = new PersonalDetailsController();

        setup(staffId);
    }

    /**
     * Creates the gui window and it's content with use of other methods
     * once it is ready it makes the gui visible to the user.
     *
     * @param staffId the staff ID of the employee personal details to amend
     */
    private void setup(String staffId) {
        Container contentArea = frame.getContentPane();
        contentArea.setLayout(new BorderLayout());

        //constraints that are common for all items
        constraints.insets = new Insets(3, 3, 3, 3);
        constraints.fill = GridBagConstraints.HORIZONTAL;

        makeMenu();
        populateMainPanel(staffId);
        populateButtonPanel();

        contentArea.add(mainPanel, BorderLayout.CENTER);
        contentArea.add(buttonPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setMinimumSize(new Dimension(550, 200));
        frame.setLocationRelativeTo(null);//setting frame to the center of the screen
        frame.setVisible(true);
    }

    private void populateMainPanel(String staffId) {
        //Get Data on employee with employee id from HRDatabase
        PDEmployee employee = personalDetailsController.readPersonalDetails(staffId);
        //Store in variable
        //setText of each field individually


        int labelWeight = 0;
        int fieldWeight = 1;
        int gridYValue = 0;

        //staff no to panel
        setConstraints(0, gridYValue, labelWeight, 1);
        mainPanel.add(new JLabel("StaffNo:"), constraints);
        setConstraints(1, gridYValue, fieldWeight, 1);
        txtStaffNo.setText(employee.getStaffId());
        txtStaffNo.setEditable(false);
        mainPanel.add(txtStaffNo, constraints);
        gridYValue++;

        //surname to panel
        setConstraints(0, gridYValue, labelWeight, 1);
        mainPanel.add(new JLabel("Surname:"), constraints);
        setConstraints(1, gridYValue, fieldWeight, 1);
        txtSurname.setText(employee.getSurname());
        txtSurname.setEditable(false);
        mainPanel.add(txtSurname, constraints);
        gridYValue++;

        //name to panel
        setConstraints(0, gridYValue, labelWeight, 1);
        mainPanel.add(new JLabel("Name:"), constraints);
        setConstraints(1, gridYValue, fieldWeight, 1);
        txtName.setText(employee.getName());
        txtName.setEditable(false);
        mainPanel.add(txtName, constraints);
        gridYValue++;

        //DOB to panel
        setConstraints(0, gridYValue, labelWeight, 1);
        mainPanel.add(new JLabel("Date of Birth:"), constraints);
        setConstraints(1, gridYValue, fieldWeight, 1);
        txtDob.setDateFormatString("YYYY / MM / dd");
        txtDob.setDate(employee.getDob());
        txtDob.setEnabled(false);
        mainPanel.add(txtDob, constraints);
        gridYValue++;

        //Address to panel
        constraints.gridwidth = 1;
        setConstraints(0, gridYValue, labelWeight, 1);
        mainPanel.add(new JLabel("Address:"), constraints);
        setConstraints(1, gridYValue, fieldWeight, 1);
        txtAddress.setPreferredSize(new Dimension(450, 90));
        txtAddress.setEditable(true);
        txtAddress.setText(employee.getAddress());
        txtAddress.setEditable(false);
        mainPanel.add(txtAddress, constraints);
        gridYValue++;

        //town to panel
        constraints.gridwidth = 1;
        setConstraints(0, gridYValue, labelWeight, 1);
        mainPanel.add(new JLabel("Town:"), constraints);
        setConstraints(1, gridYValue, fieldWeight, 1);
        txtTown.setText(employee.getTown());
        txtTown.setEditable(false);
        mainPanel.add(txtTown, constraints);
        gridYValue++;

        //county to panel
        constraints.gridwidth = 1;
        setConstraints(0, gridYValue, labelWeight, 1);
        mainPanel.add(new JLabel("County:"), constraints);
        setConstraints(1, gridYValue, fieldWeight, 1);
        txtCounty.setText(employee.getCounty());
        txtCounty.setEditable(false);
        mainPanel.add(txtCounty, constraints);
        gridYValue++;

        //postcode to panel
        constraints.gridwidth = 1;
        setConstraints(0, gridYValue, labelWeight, 1);
        mainPanel.add(new JLabel("Postcode:"), constraints);
        setConstraints(1, gridYValue, fieldWeight, 1);
        txtPostcode.setText(employee.getPostcode());
        txtPostcode.setEditable(false);
        mainPanel.add(txtPostcode, constraints);
        gridYValue++;

        //tel no to panel
        constraints.gridwidth = 1;
        setConstraints(0, gridYValue, labelWeight, 1);
        mainPanel.add(new JLabel("Telephone Number:"), constraints);
        setConstraints(1, gridYValue, fieldWeight, 1);
        txtTelNo.setText(employee.getTelNo());
        txtTelNo.setEditable(false);
        mainPanel.add(txtTelNo, constraints);
        gridYValue++;

        //mobile no to panel
        constraints.gridwidth = 1;
        setConstraints(0, gridYValue, labelWeight, 1);
        mainPanel.add(new JLabel("Mobile Number:"), constraints);
        setConstraints(1, gridYValue, fieldWeight, 1);
        txtMobileNo.setText(employee.getMobileNo());
        txtMobileNo.setEditable(false);
        mainPanel.add(txtMobileNo, constraints);
        gridYValue++;

        //emergency contact to panel
        constraints.gridwidth = 1;
        setConstraints(0, gridYValue, labelWeight, 1);
        mainPanel.add(new JLabel("Emergency Contact Name:"), constraints);
        setConstraints(1, gridYValue, fieldWeight, 1);
        txtEmergencyContact.setText(employee.getEmergencyContact());
        txtEmergencyContact.setEditable(false);
        mainPanel.add(txtEmergencyContact, constraints);
        gridYValue++;

        //staff no to panel
        constraints.gridwidth = 1;
        setConstraints(0, gridYValue, labelWeight, 1);
        mainPanel.add(new JLabel("Emergency Contact Number:"), constraints);
        setConstraints(1, gridYValue, fieldWeight, 1);
        txtEmergencyContactNo.setText(employee.getEmergencyContactNo());
        txtEmergencyContactNo.setEditable(false);
        mainPanel.add(txtEmergencyContactNo, constraints);
    }

    private void populateButtonPanel() {
        JButton btnBack = new JButton("Back");
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 0;
        constraints.gridwidth = 1;
        buttonPanel.add(btnBack, constraints);
        buttonPanel.setBorder(new EmptyBorder(0, 0, 10, 0));

        btnBack.addActionListener(e -> frame.dispose());
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
        menuBar.add(menu);
    }

    private void setConstraints(int gridX, int gridY, int weightX, int gridWidth) {
        constraints.gridx = gridX;
        constraints.gridy = gridY;
        constraints.weightx = weightX;
        constraints.gridwidth = gridWidth;
    }
}
