import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import datastructures.PDEmployee;
import static org.junit.Assert.*;

/**
 * test class for the personal details controller
 * Created by Group 29D on 22/03/2017
 */
public class PersonalDetailsControllerTest {
    private PersonalDetailsController personalDetailsController;
    private Date date = new Date(1490204545); //Wed, 22 Mar 2017 17:42:24 GMT
    private PDEmployee employeeExpected = new PDEmployee();
    private PDEmployee employeeActual = new PDEmployee();

    @Before
    public void setUp() throws Exception {
        personalDetailsController = new PersonalDetailsController();
        //creates a test employee personal details
        personalDetailsController.createPersonalDetails(91546, "Smith", "John", date,
                "52 Test Road", "Test Town", "Test County", "AB1 2CD", "09876543210",
                "09876543210", "Emergency Test", "08976543210");
        employeeActual = personalDetailsController.readPersonalDetails("91546");
    }



    @Test
    public void getData() throws Exception {
        //checks that getting all personal details from database works (doesn't return null)
        assert (!personalDetailsController.getData().isEmpty());
    }

    @Test
    public void ReadingPersonalDetails() throws Exception {
        //checks that getting a single record for a given staff member works
        assert (personalDetailsController.readPersonalDetails("100001")!=null);
    }

    @Test
    public void staffIDFree() throws Exception {
        //checks that giving a staffID that isn't taken is correctly recognised
        assert(personalDetailsController.staffIDFree(999999));
    }

    @Test
    public void staffIDNotFree() throws Exception {
        //checks that giving a staffID that is taken is correctly recognised
        assert(!personalDetailsController.staffIDFree(100001));
    }

    @Test
    public void createPersonalDetailsTestStaffID() throws Exception {
        //checks if it StaffID is added
        assertEquals("91546",employeeActual.getStaffId());
    }

    @Test
    public void createPersonalDetailsTestName() throws Exception {
        //checks if it has successfully added name
        assertEquals("John",employeeActual.getName());
    }

    @Test
    public void createPersonalDetailsTestSurname() throws Exception {
        //checks if it has successfully added surname
        assertEquals("Smith",employeeActual.getSurname());
    }

    @Test
    public void createPersonalDetailsTestAddress() throws Exception {
        //checks if it has successfully added address
        assertEquals("52 Test Road",employeeActual.getAddress());
    }

    @Test
    public void createPersonalDetailsTestDOB() throws Exception {
        //checks if it has successfully added date
        assertEquals(date,employeeActual.getDob());
    }

    @Test
    public void createPersonalDetailsTestTown() throws Exception {
        //checks if it has successfully added town
        assertEquals("Test Town",employeeActual.getTown());
    }

    @Test
    public void createPersonalDetailsTestCounty() throws Exception {
        //checks if it has successfully added county
        assertEquals("Test County",employeeActual.getCounty());
    }

    @Test
    public void createPersonalDetailsTestSPostcode() throws Exception {
        //checks if it has successfully added postcode
        assertEquals("AB1 2CD",employeeActual.getPostcode());
    }

    @Test
    public void createPersonalDetailsTel() throws Exception {
        //checks if it has successfully added tel
        assertEquals("09876543210",employeeActual.getTelNo());
    }

    @Test
    public void createPersonalDetailsTestMobile() throws Exception {
        //checks if it has successfully added mobile
        assertEquals("09876543210",employeeActual.getMobileNo());
    }

    @Test
    public void createPersonalDetailsTestEmergencyName() throws Exception {
        //checks if it has successfully added emergency contact name
        assertEquals("Emergency Test",employeeActual.getEmergencyContact());
    }

    @Test
    public void createPersonalDetailsTestEmergencyContact() throws Exception {
        //checks if it has successfully added emergency contact no
        assertEquals("08976543210",employeeActual.getEmergencyContactNo());
    }

    @Test
    public void amendPersonalDetailsTest() throws Exception {
        //Amends the created test record
        personalDetailsController.amendPersonalDetails(91546, "Smith", "John", date,
                "52 New Test Road", "Test Town", "Test County", "AB1 2CD", "09876543210",
                "09876543210", "Emergency Test", "08976543210");
        employeeActual = personalDetailsController.readPersonalDetails("91546");
        //checks if it has successfully amended personal details record
        assertEquals("52 New Test Road",employeeActual.getAddress());
    }

    @After
    public void tearDown() throws Exception {
        //deletes the created test record
        personalDetailsController.deleteRecord(91546);
    }


}