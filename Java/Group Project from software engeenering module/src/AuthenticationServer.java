import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * server class for the authentication process,
 * handles all the database queries that is used by the authenticationController class.
 * Created by Group 29D on 1/03/2017
 */
class AuthenticationServer {

    // Acts as a session variable that will store details on the user and their state.
    // Initial state is null to show no user logged in
    private static User session = null;

    /**
     * Connects to the authentication database
     * @return the connection if successful or null if not.
     */
    Connection dbConnect() {
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection("jdbc:sqlite:authenticationDatabase.sqlite");
        } catch (Exception e) {
            return null;
        }
    }

    private void newSession (String username, int accessLevel){
        Integer userID = getStaffID(username);
        if(userID != null) {
            session = new User(username, accessLevel, userID);
        } else{
            throw new NullPointerException("Database error: user's staffID not found.");
        }
    }

    User getSession(){
            return session;
    }

    /**
     * looks up the users login details with the authentication database and returns
     * the appropriate outcome based on the lookup result.
     *
     * @param username the name of the user to use in the database lookup
     * @param password the password of the user to use in the database lookup
     * @param accessRight the requested access level of the user to use in the database lookup
     * @return true if login is successful false if not
     */
    boolean authenticateLogin(String username, String password, int accessRight) {
        try {
            Connection connection = this.dbConnect();
            String query = "SELECT * FROM users WHERE userName=? AND password=? AND (accessLevel=? OR accessLevel2=? OR accessLevel3=?)";
            assert connection != null;
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setInt(3, accessRight);
            ps.setInt(4,accessRight);
            ps.setInt(5,accessRight);


            ResultSet rs = ps.executeQuery();
            int counter = 0;
            //if the result of the lookup is non empty that means user is present and
            //counter is incremented.
            while (rs.next()) {
                counter++;
            }
            //if the counter is 1 the connections are closed and true is returned, else false
            if (counter == 1) {
                rs.close();
                ps.close();
                newSession(username,accessRight);
                return true;
            } else {
                rs.close();
                ps.close();
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @return true if logout successful false if not
     * currently no chance of false due to simplicity of server system
     * but on moving to code external server in which lookups are needed
     * then there would be chance of failure
     */
    boolean logout(){
        session = null;
        return true;
    }

    /**
     * Gets the values of accessLevel table from the database and stores them into an arrayList
     * @return an arrayList containing the results of the query if successful or null if not
     */
    ArrayList<String> getARBoxValues(){
        ArrayList<String> output = new ArrayList<>();
        try{
            Connection connection = this.dbConnect();

            String query= "select * from accessLevel";
            assert connection != null;
            PreparedStatement ps= connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                output.add(rs.getString("accessLevel"));
            }
            rs.close();
            ps.close();
            return output;
        }catch (Exception e){
            return null;
        }
    }

    /**
     * gets the id linked to the given username
     * @param username the username for which an id is wanted
     * @return the id if found or null if error occurs
     */
    private Integer getStaffID(String username){
        try {
            Connection connection = this.dbConnect();
            String query = "SELECT * FROM users WHERE userName=?";
            assert connection != null;
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();
            Integer id = rs.getInt(1);
            rs.close();
            ps.close();
            return id;
        } catch (Exception e) {
            return null;
        }
    }
}
