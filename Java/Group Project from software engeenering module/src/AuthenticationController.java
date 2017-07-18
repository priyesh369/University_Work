import java.util.ArrayList;

/**
 * Controller class for the authentication process,
 * handles all the logic that is used by the authenticationGUI class.
 * Created by Group 29D on 16/02/2017
 */
class AuthenticationController {
    private AuthenticationServer server;

    /**
     * Constructor for AuthenticationController object
     */
    AuthenticationController(){
        server = new AuthenticationServer();
    }

    /**
     * sends the users login details to the AuthenticationServer and returns
     * the result.
     *
     * @param username the id of the user
     * @param password the password of the user
     * @param accessRight the requested access level
     * @return true if login is successful false if not
     */
    boolean login (String username, String password, int accessRight){
        return server.authenticateLogin(username,password,accessRight);
    }

    /**
     * @return true if logout successful false if not
     */
    boolean logout(){
        return server.logout();
    }

    User getSession(){
        return server.getSession();
    }

    /**
     * Gets the value from the server and returns it to the gui
     * @return an arrayList containing the value from the server, may be null if server query fails
     */
    ArrayList<String> getARBoxValues(){
        return server.getARBoxValues();
    }

}
