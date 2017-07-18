import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * test class for the Authentication server
 * Created by Group 29D on 1/03/2017
 */
public class AuthenticationServerTest {
    private AuthenticationServer authenticationServer;
    @Before
    public void setUp() throws Exception {
        authenticationServer = new AuthenticationServer();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void connectToDatabase() throws Exception {
        //test that connecting to the database works.
        assert(authenticationServer.dbConnect() != null);
    }

    @Test
    public void userLoginWithCorrectRights() throws Exception {
        //test an employee login in with correct access right of employee selected
        assert (authenticationServer.authenticateLogin("emp1","password",1));
    }

    @Test
    public void userLoginWithWrongPassword() throws Exception {
        //test an employee login in with correct access right of employee selected
        assert (!authenticationServer.authenticateLogin("emp1","notThePassword",1));
    }

    @Test
    public void userLoginWithWrongName() throws Exception {
        //test an employee login in with correct access right of employee selected
        assert (!authenticationServer.authenticateLogin("emp01","password",1));
    }

    @Test
    public void userLoginWithElevatedRights() throws Exception {
        //test an employee login in with incorrect access right of director selected
        assert (!authenticationServer.authenticateLogin("emp1","password",4));
    }
    @Test
    public void userLoginWithLoweredRights() throws Exception {
        //test a director login in with lowered but correct access right of employee selected
        assert (authenticationServer.authenticateLogin("dir1","password",1));
    }

    @Test
    public void getARBoxValues() throws Exception {
        assert (!authenticationServer.getARBoxValues().isEmpty());
    }

    @Test
    public void userLogout() throws Exception {
        //test that logout works
        assert (authenticationServer.logout());
    }

    @Test
    public void gettingSessionFromServer() throws Exception {
        //test that getting the user session is not null when user has logged in
        //and that data inside is correct
        authenticationServer.authenticateLogin("emp1", "password", 1);
        assert (authenticationServer.getSession() != null);
        assert (authenticationServer.getSession().getUserName().equals("emp1"));
        assert (authenticationServer.getSession().getStaffID() == 100001); // if this fails check getStaffID method
        assert (authenticationServer.getSession().getAccessLevel() == 1);
        authenticationServer.logout();
    }

}