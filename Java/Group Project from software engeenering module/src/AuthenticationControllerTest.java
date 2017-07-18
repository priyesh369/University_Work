import org.junit.Before;
import org.junit.Test;
import org.junit.After;

/**
 * test class for the Authentication controller
 * Created by Group 29D on 16/02/2017
 */
public class AuthenticationControllerTest {
    private AuthenticationController authenticationController;

    @Before
    public void setUp() throws Exception {
        authenticationController = new AuthenticationController();

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void userLoginWithCorrectRights() throws Exception {
        //test an employee login in with correct access right of employee selected
        assert (authenticationController.login("emp1", "password", 1));
        authenticationController.logout();
    }

    @Test
    public void userLoginWithWrongPassword() throws Exception {
        //test an employee login in with correct access right of employee selected
        assert (!authenticationController.login("emp1", "notThePassword", 1));
        authenticationController.logout();
    }

    @Test
    public void userLoginWithWrongName() throws Exception {
        //test an employee login in with correct access right of employee selected
        assert (!authenticationController.login("notTheUsername", "password", 1));
        authenticationController.logout();
    }

    @Test
    public void userLoginWithElevatedRights() throws Exception {
        //test an employee login in with incorrect access right of director selected
        assert (!authenticationController.login("emp1", "password", 4));
        authenticationController.logout();
    }

    @Test
    public void userLoginWithLoweredRights() throws Exception {
        //test a director login in with lowered but correct access right of employee selected
        assert (authenticationController.login("dir1", "password", 1));
        authenticationController.logout();
    }

    @Test
    public void userLogout() throws Exception {
        //test that logout works
        assert (authenticationController.logout());
    }

    @Test
    public void gettingSessionFromController() throws Exception {
        //test that getting the user session is not null when user has logged in
        //and that data inside is correct
        authenticationController.login("emp1", "password", 1);
        assert (authenticationController.getSession() != null);
        assert (authenticationController.getSession().getUserName().equals("emp1"));
        assert (authenticationController.getSession().getStaffID() == 100001);
        assert (authenticationController.getSession().getAccessLevel() == 1);
        authenticationController.logout();
    }

    @Test
    public void accessLevelsForChoiceBox() throws Exception {
        //test that access levels are received properly from the database (result array is not empty)
        assert (!authenticationController.getARBoxValues().isEmpty());
    }


}