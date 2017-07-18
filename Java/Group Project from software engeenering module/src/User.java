/**
 * Created by Priyesh Patel on 03/03/2017.
 */
public class User {
    private String username;
    private int accessLevel;
    private int staffID;

    User(String username, int accessLevel, int staffID){
        this.username = username;
        this.accessLevel = accessLevel;
        this.staffID = staffID;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public int getStaffID() {
        return staffID;
    }

    public String getUserName() {
        return username;
    }
}
