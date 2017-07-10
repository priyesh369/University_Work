import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

/**
 * The test class PersonalityListTest.
 *
 * @author  David J. Barnes
 * @version 2015.11.07
 */
public class PersonalityListTestDJB
{
    private PersonalityList personal1;
    private Personality personal2;
    private Personality personal3;
    private Personality personal4;
    private Personality personal5;
    private Personality personal6;

    /**
     * Default constructor for test class PersonalityListTest
     */
    public PersonalityListTestDJB()
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
        setupStandardData();        
        personal2.increaseVotes(100);
        personal3.increaseVotes(98);
        personal4.increaseVotes(50);
        personal5.increaseVotes(50);
        personal6.increaseVotes(42);
    }
    
    /**
     * Create a new PersonalityList and Personalities to go in it.
     * The Personalities are not added to the list.
     */
    private void setupStandardData()
    {
        personal1 = new PersonalityList();
        personal2 = new Personality("Lewis Hamilton", "F1");
        personal3 = new Personality("Sarah Storey", "Cycling");
        personal4 = new Personality("Maggie Alphonsi", "Rugby");
        personal5 = new Personality("Jo Pavey", "Athletics");
        personal6 = new Personality("Moeen Ali", "Cricket");
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
    public void add()
    {
        // Basic test of addition.
        personal1.addPersonality(personal2);
        assertEquals(1, personal1.getSize());
    }

    @Test
    public void size()
    {
        // Basic tests of the getSize method.
        assertEquals(0, personal1.getSize());
        
        personal1.addPersonality(personal2);
        assertEquals(1, personal1.getSize());
        
        personal1.addPersonality(personal3);
        assertEquals(2, personal1.getSize());
        
        personal1.addPersonality(personal4);
        personal1.addPersonality(personal5);
        personal1.addPersonality(personal6);
        assertEquals(5, personal1.getSize());
    }

    @Test
    public void list()
    {
        // Visual checks needed to make sure this is correct.
        // Empty list.
        personal1.list();
        
        // One person.
        personal1.addPersonality(personal2);
        personal1.list();
        
        // Five people.
        personal1.addPersonality(personal3);
        personal1.addPersonality(personal4);
        personal1.addPersonality(personal5);
        personal1.addPersonality(personal6);
        personal1.list();
    }

    @Test
    public void shortListEmpty()
    {
        // Shortlist empty list.
        personal1.shortlist(0);
        assertEquals(0, personal1.getSize());
    }
    
    @Test
    public void shortListNoOne()
    {        
        // Remove no-one.
        setupAll();
        personal1.shortlist(0);
        assertEquals(5, personal1.getSize());
    }
    
    @Test
    public void shortListBoundary()
    {
        setupAll();
        // Remove no-one.
        personal1.shortlist(42);
        assertEquals(5, personal1.getSize());
    }
    
    @Test
    public void shortListRemoveOne()
    {        
        setupAll();
        // Remove one.
        personal1.shortlist(43);
        assertEquals(4, personal1.getSize());
    }
    
    @Test
    public void shortListRemoveMultiple()
    {        
        setupAll();
        // Remove multiple with the same number of votes..
        personal1.shortlist(51);
        assertEquals(2, personal1.getSize());
    }
    
    @Test
    public void shortListKeepOne()
    {        
        setupAll();
        // Keep just one.
        personal1.shortlist(100);
        assertEquals(1, personal1.getSize());
    }
    
    @Test
    public void shortListKeepNoOne()
    {    
        setupAll();
        // Keep no one.
        personal1.shortlist(1000);
        assertEquals(0, personal1.getSize());
    }

    @Test
    public void top0()
    {
        // Start with an empty list of personalities.
        ArrayList<Personality> result = personal1.top(0);
        assertNotNull(result);
        assertEquals(0, result.size());
        
        // Now with some personalities.
        setupAll();
        int originalSize = personal1.getSize();
        result = personal1.top(0);
        assertNotNull(result);
        assertEquals(0, result.size());
        assertEquals(personal1.getSize(), originalSize);
    }
    
    @Test
    public void top1()
    {
        setupAll();
        int originalSize = personal1.getSize();
        ArrayList<Personality> result = personal1.top(1);
        System.out.println(result.get(0).getDetails());
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(isInList(result, "Lewis Hamilton"));
        assertEquals(personal1.getSize(), originalSize);
    }
    
    @Test
    public void top2()
    {
        setupAll();
        int originalSize = personal1.getSize();
        ArrayList<Personality> result = personal1.top(2);
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(isInList(result, "Lewis Hamilton"));
        assertTrue(isInList(result, "Sarah Storey"));
        assertEquals(personal1.getSize(), originalSize);
    }
    
    @Test
    public void top3Tied()
    {
        setupAll();
        int originalSize = personal1.getSize();
        ArrayList<Personality> result = personal1.top(3);
        assertNotNull(result);
        assertEquals(4, result.size());
        assertTrue(isInList(result, "Lewis Hamilton"));
        assertTrue(isInList(result, "Sarah Storey"));
        assertTrue(isInList(result, "Maggie Alphonsi"));
        assertTrue(isInList(result, "Jo Pavey"));
        assertEquals(personal1.getSize(), originalSize);
    }
    
    @Test
    public void top3NotTied()
    {
        setupAll();
        int originalSize = personal1.getSize();
        personal4.increaseVotes(2);
        ArrayList<Personality> result = personal1.top(3);
        assertNotNull(result);
        assertEquals(3, result.size());
        assertTrue(isInList(result, "Lewis Hamilton"));
        assertTrue(isInList(result, "Sarah Storey"));
        assertTrue(isInList(result, "Maggie Alphonsi"));
        assertEquals(personal1.getSize(), originalSize);
    }
    
    @Test
    public void topAllExactly()
    {
        setupAll();
        int originalSize = personal1.getSize();
        
        int number = personal1.getSize();
        
        ArrayList<Personality> result = personal1.top(number);
        assertNotNull(result);
        // Check that the original list is unchanged.
        assertEquals(number, personal1.getSize());
        // Check we have them all.
        assertEquals(number, result.size());
        assertEquals(personal1.getSize(), originalSize);
    }
    
    @Test
    public void topAllMore()
    {
        setupAll();
        int originalSize = personal1.getSize();
        
        int number = personal1.getSize();
        
        // Try to get more than available.
        ArrayList<Personality> result = personal1.top(number + 1);
        assertNotNull(result);
        // Check that the original list is unchanged.
        assertEquals(number, personal1.getSize());
        // Check we have them all.
        assertEquals(number, result.size());
        assertEquals(personal1.getSize(), originalSize);
    }
    
    @Test
    public void topTwice()
    {
        setupAll();
        int originalSize = personal1.getSize();
        
        ArrayList<Personality> result = personal1.top(2);
        assertEquals(2, result.size());
        // Check that the previous results are not
        // retained.
        result = personal1.top(1);
        assertEquals(1, result.size());
        assertEquals(personal1.getSize(), originalSize);
    }
    
    @Test
    public void topAllSameVotes()
    {
        // Start with a fresh set of test objects.
        setupStandardData();
        
        personal2.increaseVotes(100);
        personal3.increaseVotes(100);
        personal4.increaseVotes(100);
        personal5.increaseVotes(100);
        personal6.increaseVotes(100);
        
        setupAll();
        
        ArrayList<Personality> result = personal1.top(2);
        assertEquals(personal1.getSize(), result.size());
        
    }
    
    /**
     * Put all of the personalities into the list.
     */
    private void setupAll()
    {
        personal1.addPersonality(personal2);
        personal1.addPersonality(personal3);
        personal1.addPersonality(personal4);
        personal1.addPersonality(personal5);
        personal1.addPersonality(personal6);
    }
    
    /**
     * Determine whether the given list contains the given
     * personality.
     * @param list The list of personalities.
     * @param name The name sought.
     * @return true if present, false otherwise.
     */
    private boolean isInList(ArrayList<Personality> list,
                             String name)
    {
        boolean found = false;
        int index = 0;
        while(!found && index < list.size()) {
            found = name.equals(list.get(index).getName());
            index++;
        }
        return found;
    }

    @Test
    public void voteForPositive()
    {
        setupAll();
        Personality person = personal6;
        int beforeVotes = person.getVotes();
        personal1.voteFor(person.getName());
        assertEquals(beforeVotes + 1, person.getVotes());
    }

    @Test
    public void voteForNegative()
    {
        setupAll();
        Personality person = personal6;
        int beforeVotes = person.getVotes();
        personal1.voteFor(person.getName() + "?");
        assertEquals(beforeVotes, person.getVotes());
    }

    @Test
    public void voteForNegativeContains()
    {
        setupAll();
        Personality person = personal2;
        int beforeVotes = person.getVotes();
        // Exact match is required for a name.
        personal1.voteFor("a");
        assertEquals(beforeVotes, person.getVotes());
    }
}





