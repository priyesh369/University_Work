import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * test class for the hr database class
 * Created by Group 29D on 22/03/2017
 */
public class HRDatabaseTest {
    private HRDatabase hrDatabase= new HRDatabase();
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void addPDRecord() throws Exception {

    }

    @Test
    public void updatePDRecord() throws Exception {

    }

    @Test
    public void getPersonalDetails() throws Exception {

    }

    @Test
    public void getPDRecord() throws Exception {

    }

    @Test
    public void staffIDFree() throws Exception {

    }

    @Test
    public void addReviewRecord() throws Exception {

    }

    @Test
    public void createReviewRecord() throws Exception {

    }

    @Test
    public void amendReviewRecord() throws Exception {

    }

    @Test
    public void setReadOnly() throws Exception {

    }

    @Test
    public void recordAllocatedReviewer() throws Exception {

    }

    @Test
    public void getReviewID() throws Exception {
        System.out.println(hrDatabase.getReviewID(100001));
    }

    @Test
    public void getReviewers() throws Exception {
        assert( !hrDatabase.getReviewers().isEmpty());
    }

}