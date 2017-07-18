import datastructures.AREmployee;
import datastructures.PDEmployee;
import datastructures.Review;
import datastructures.Reviewer;

import java.sql.*;
import java.util.ArrayList;

/**
 * handles all the database queries that is used by the personalDetailsController and ReviewerController classes.
 * Created by Group 29D on 02-Mar-17.
 */
class HRDatabase {

    /**
     * Connects to the HRDatabase
     *
     * @return the connection if successful or null if not.
     */
    private Connection dbConnect() {
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection("jdbc:sqlite:HRDatabase.sqlite");
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * adds the personal details of an employee
     *
     * @param staffID-            value of the staffID (6 digit value)
     * @param surname-            value of surname of employee
     * @param name                value of name of employee
     * @param dob-                value of dob of employee
     * @param address-            value of address of employee
     * @param town-               value of town of employee
     * @param county-             value of county of employee
     * @param postCode-           value of postCode of employee
     * @param telNo-              value of telNo of employee
     * @param mobileNo-           value of mobileNo of employee
     * @param emergencyContact-   value of emergencyContact of employee
     * @param emergencyContactNo- value of emergencyContactNo of employee
     */
    void addPDRecord(Integer staffID, String surname, String name, java.util.Date dob, String address, String town, String county,
                     String postCode, String telNo, String mobileNo, String emergencyContact, String emergencyContactNo) {

        try {
            Connection connection = this.dbConnect();
            String insertSql = "insert into personalDetails(staffID,surname,name,dob,address,town,county,postcode,telNo," +
                    "mobileNo,emergencyContact,emergencyContactNo) values(?,?,?,?,?,?,?,?,?,?,?,?)";
            assert connection != null;
            PreparedStatement ps = connection.prepareStatement(insertSql);
            ps.setInt(1, staffID);
            ps.setString(2, surname);
            ps.setString(3, name);
            ps.setDate(4, (new java.sql.Date(dob.getTime())));
            ps.setString(5, address);
            ps.setString(6, town);
            ps.setString(7, county);
            ps.setString(8, postCode);
            ps.setString(9, telNo);
            ps.setString(10, mobileNo);
            ps.setString(11, emergencyContact);
            ps.setString(12, emergencyContactNo);

            //adding the values to the database
            ps.executeUpdate();


        } catch (Exception e) {
            e.getMessage();
        }
    }

    /**
     * adds the personal details of an employee
     *
     * @param staffID-            value of the staffID (6 digit value)
     * @param surname-            value of surname of employee
     * @param name                value of name of employee
     * @param dob-                value of dob of employee
     * @param address-            value of address of employee
     * @param town-               value of town of employee
     * @param county-             value of county of employee
     * @param postCode-           value of postCode of employee
     * @param telNo-              value of telNo of employee
     * @param mobileNo-           value of mobileNo of employee
     * @param emergencyContact-   value of emergencyContact of employee
     * @param emergencyContactNo- value of emergencyContactNo of employee
     */
    void updatePDRecord(Integer staffID, String surname, String name, java.util.Date dob, String address, String town, String county,
                        String postCode, String telNo, String mobileNo, String emergencyContact, String emergencyContactNo) {

        try {
            Connection connection = this.dbConnect();
            String insertSql = "update personalDetails set staffID=?,surname=?,name=?,dob=?,address=?,town=?," +
                    "county=?,postcode=?,telNo=?,mobileNo=?,emergencyContact=?,emergencyContactNo=? where staffID=?";
            assert connection != null;
            PreparedStatement ps = connection.prepareStatement(insertSql);
            ps.setInt(1, staffID);
            ps.setString(2, surname);
            ps.setString(3, name);
            ps.setDate(4, (new java.sql.Date(dob.getTime())));
            ps.setString(5, address);
            ps.setString(6, town);
            ps.setString(7, county);
            ps.setString(8, postCode);
            ps.setString(9, telNo);
            ps.setString(10, mobileNo);
            ps.setString(11, emergencyContact);
            ps.setString(12, emergencyContactNo);
            ps.setInt(13, staffID);

            //adding the values to the database
            ps.executeUpdate();


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void deleteRecord(Integer StaffID) {
        try {
            Connection connection = this.dbConnect();
            String insertSql = "delete from personalDetails where StaffID=?";
            assert connection != null;
            PreparedStatement ps = connection.prepareStatement(insertSql);
            ps.setInt(1, StaffID);

            //deleting a record
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * gets a list of all employees for which a personal detail record exists
     * @return employeeList a list of employees
     */
    ArrayList<PDEmployee> getPersonalDetails() {
        ArrayList<PDEmployee> employeeList = new ArrayList<>();
        Connection connection = this.dbConnect();
        String sql = "select staffID, surname, name from personalDetails";
        assert connection != null;
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PDEmployee employee = new PDEmployee();
                employee.setStaffId(rs.getString("staffID"));
                employee.setSurname(rs.getString("surname"));
                employee.setName(rs.getString("name"));
                employeeList.add(employee);
            }
            rs.close();
            ps.close();
            return employeeList;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Get's an individual employee's personal details
     *
     * @param staffId an individual employee's staff ID
     */
    PDEmployee getPDRecord(String staffId) {
        PDEmployee employee = new PDEmployee();
        Connection connection = this.dbConnect();
        String sql = "select * from personalDetails where staffID=?";
        assert connection != null;
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1,staffId);
            ResultSet rs = ps.executeQuery();

            //Set employee details
            employee.setStaffId(rs.getString("staffID"));
            employee.setSurname(rs.getString("surname"));
            employee.setName(rs.getString("name"));

            //Just date formatting. Convert the sql date non-sense to a date format
            //suitable for the datepicker in the GUI
            java.util.Date date1 = rs.getTimestamp("dob");
            employee.setDob(date1);

            //Set rest of employee details
            employee.setAddress(rs.getString("address"));
            employee.setTown(rs.getString("town"));
            employee.setCounty(rs.getString("county"));
            employee.setPostcode(rs.getString("postcode"));
            employee.setTelNo(rs.getString("telNo"));
            employee.setMobileNo(rs.getString("mobileNo"));
            employee.setEmergencyContact(rs.getString("emergencyContact"));
            employee.setEmergencyContactNo(rs.getString("emergencyContactNo"));

            rs.close();
            ps.close();
            return employee;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    boolean staffIDFree (Integer staffID){
        Connection connection = this.dbConnect();
        String sql = "select * from personalDetails where staffID = ?";
        assert connection != null;
        PreparedStatement ps;
        boolean out = false;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, staffID);
            ResultSet rs = ps.executeQuery();
            if (!rs.isBeforeFirst() ) {
                out = true;
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return out;
    }

    void addReviewRecord(int staffID, String name, String rev1name, String rev2name, String sector, String jobTitle, String summary, String comments){
        try {
            Connection connection = this.dbConnect();
            String insertSql = "insert into review(readonly, reviewID,staffID,name,director,secondDirector, section,jobTitle,performanceSummary, reviewerComments) values(?,?,?,?,?,?,?,?,?,?)";
            assert connection != null;
            PreparedStatement ps = connection.prepareStatement(insertSql);
            ps.setString(1, "N"); //read-only set to N
            ps.setInt(3, staffID);
            ps.setString(4, name);
            ps.setString(5, rev1name);
            ps.setString(6, rev2name);
            ps.setString(7, sector);
            ps.setString(8, jobTitle);
            ps.setString(9, summary);
            ps.setString(10, comments);


            //adding the values to the database
            ps.executeUpdate();


        } catch (Exception e) {
            e.getMessage();
        }
    }

    void addReviewGoal(int staffID, String goal){
        try {
            Connection connection = this.dbConnect();
            int reviewID = getReviewID(staffID);
            String insertSql = "insert into goals(goalID, reviewID, staffID, goals) values(?,?,?,?)";
            assert connection != null;
            PreparedStatement ps = connection.prepareStatement(insertSql);
            ps.setInt(2, reviewID);
            ps.setInt(3, staffID);
            ps.setString(4, goal);
            ps.executeUpdate();
            ps.close();
        }catch (Exception e){
            e.getMessage();
        }
    }

     int getReviewID(int staffID){
        Connection connection = this.dbConnect();
        String sql = "SELECT * from review where staffID = ? AND readonly = 'N'";
        assert connection != null;
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, staffID);
            ResultSet rs = ps.executeQuery();
            int out = rs.getInt("reviewID");
            rs.close();
            ps.close();
            return out;
        }catch (Exception e){
            return 0;
        }
    }

    /*read review*/
    AREmployee getReviewRecord(int staffId) {
        AREmployee employee = new AREmployee();
        Connection connection = this.dbConnect();
        String sql = "select * from review where staffID=? AND readonly = 'N'";
        assert connection != null;
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1,staffId);
            ResultSet rs = ps.executeQuery();

            //Set review details
            employee.setStaffId(rs.getString("staffID"));
            employee.setName(rs.getString("name"));
            //Set rest of review details
            employee.setRev1name(rs.getString("director"));
            employee.setRev2name(rs.getString("secondDirector"));
            employee.setSecttion(rs.getString("section"));
            employee.setJobTitle(rs.getString("jobTitle"));
            employee.setSummary(rs.getString("performanceSummary"));
            employee.setComments(rs.getString("reviewerComments"));

            rs.close();
            ps.close();

            return employee;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /* read PAST review
    AREmployee getPastReviewRecord(int staffId) {
        AREmployee employee = new AREmployee();
        Connection connection = this.dbConnect();
        String sql = "select * from review where staffID=? AND readonly = 'N'";
        assert connection != null;
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1,staffId);
            ResultSet rs = ps.executeQuery();

            //Set review details
            employee.setStaffId(rs.getString("staffID"));
            employee.setName(rs.getString("name"));
            //Set rest of review details
            employee.setRev1name(rs.getString("director"));
            employee.setRev2name(rs.getString("secondDirector"));
            employee.setSecttion(rs.getString("section"));
            employee.setJobTitle(rs.getString("jobTitle"));
            employee.setSummary(rs.getString("performanceSummary"));
            employee.setComments(rs.getString("reviewerComments"));
            //Just date formatting. Convert the sql date non-sense to a date format
            //suitable for the datepicker in the GUI
            java.util.Date revieweeSignatureDate = rs.getTimestamp("revieweeSignitureDate");
            employee.setEmpsign(revieweeSignatureDate);
            java.util.Date directorSignatureDate = rs.getTimestamp("directorSignitureDate");
            employee.setRev1sign(directorSignatureDate);
            java.util.Date secondReviewerSignatureDate = rs.getTimestamp("secondReviewerSignatureDate");
            employee.setRev1sign(secondReviewerSignatureDate);
            rs.close();
            ps.close();

            return employee;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }*/

    void amendReviewRecord(/*multiple parameters*/){}

    void setReadOnly(int reviewID){}

    void allocateReviewer(int reviewID, int reviewerID, int reviewerID2){
        try {
            Connection connection = this.dbConnect();
            Reviewer rev1 = getReviewer(reviewerID);
            Reviewer rev2 = getReviewer(reviewerID2);

            String insertSql = "update review set director = ?, secondDirector = ? where reviewID = ?";
            assert connection != null;
            PreparedStatement ps = connection.prepareStatement(insertSql);
            ps.setInt(3, reviewID);
            ps.setString(1, rev1.getName());
            ps.setString(2, rev2.getName());
            ps.executeUpdate();
            ps.close();
        }catch (Exception e){
            e.getMessage();
        }
    }

    private Reviewer getReviewer(int reviewerID){
        Reviewer reviewer = new Reviewer();
        Connection connection = this.dbConnect();
        String sql = "select * from reviewers where staffID=?";
        assert connection != null;
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1,reviewerID);
            ResultSet rs = ps.executeQuery();

            //Set employee details
            reviewer.setStaffID(rs.getString("staffID"));
            reviewer.setName(rs.getString("Name"));


            rs.close();
            ps.close();
            return reviewer;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    ArrayList<Reviewer> getReviewers(){
        ArrayList<Reviewer> reviewerList = new ArrayList<>();
        Connection connection = this.dbConnect();
        String sql = "select * from reviewers";
        assert connection != null;
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Reviewer reviewer = new Reviewer();
                reviewer.setStaffID(rs.getString("StaffID"));
                reviewer.setName(rs.getString("Name"));
                reviewerList.add(reviewer);
            }
            rs.close();
            ps.close();
            return reviewerList;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /*
    method to retrieve reviews that have not been allocated to reviewers
     */
    ArrayList<Review> getReviews(){
        ArrayList<Review> reviewList = new ArrayList<>();
        Connection connection = this.dbConnect();
        String sql = "select reviewID, name from review WHERE director ='' and secondDirector='';";
        assert connection != null;
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Review review = new Review();
                review.setReviewID(rs.getInt("reviewID"));
                review.setName(rs.getString("name"));
                reviewList.add(review);
            }
            rs.close();
            ps.close();
            return reviewList;
        }catch (Exception e){
            return null;
        }
    }

    /**
     * getting the allocated reviewers
     * @param staffId- vlaue of the staff id allocated by HR
     * @return list of reviews been allocated by HR to review
     */
    ArrayList<Review> getAllocatedReviews(int staffId){
        ArrayList<Review> reviewList = new ArrayList<>();
        Connection connection = this.dbConnect();
        String sql = "select reviewID, name from review WHERE director =( select Name from reviewers where StaffID=?) or secondDirector=" +
                "( select Name from reviewers where StaffID=?) ";
        assert connection != null;
        PreparedStatement ps;

        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1,staffId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Review review = new Review();
                review.setReviewID(rs.getInt("reviewID"));
                review.setName(rs.getString("name"));
                reviewList.add(review);
            }
            rs.close();
            ps.close();
            return reviewList;
        }catch (Exception e){
            return null;
        }
    }

    /**
     * chekcing if this employee has created a review
     * @param staffId- vlaue of the staff id allocated by HR
     * @return true if review document exists, otherwise false
     */
    boolean checkForReviews(int staffId){
        Connection connection = this.dbConnect();
        String sql = "select staffID from review WHERE StaffID=?";
        assert connection != null;
        PreparedStatement ps;

        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1,staffId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return true;


            }
            rs.close();
            ps.close();

        }catch (Exception e){
            return false;
        }
        return false;
    }
}
