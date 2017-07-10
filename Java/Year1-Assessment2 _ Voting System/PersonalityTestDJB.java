

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class PersonalityTest.
 *
 * @author  David J. Barnes
 * @version 2015.11.07
 */
public class PersonalityTestDJB
{
    private Personality personal1;

    /**
     * Default constructor for test class PersonalityTest
     */
    public PersonalityTestDJB()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        personal1 = new Personality("Lewis Hamilton", "F1");
        personal1.increaseVotes(500);
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }

    @Test
    public void getName()
    {
        assertEquals("Lewis Hamilton", personal1.getName());
    }

    @Test
    public void getSport()
    {
        assertEquals("F1", personal1.getSport());
    }

    @Test
    public void getVotes()
    {
        assertEquals(500, personal1.getVotes());
    }

    @Test
    public void increaseVotes()
    {
        personal1.increaseVotes(0);
        assertEquals(500, personal1.getVotes());
        personal1.increaseVotes(1);
        assertEquals(501, personal1.getVotes());
        personal1.increaseVotes(9);
        assertEquals(510, personal1.getVotes());
    }

    @Test
    public void getDetails()
    {
        assertEquals("Lewis Hamilton takes part in F1 and has 500 votes.", personal1.getDetails());
    }
}




