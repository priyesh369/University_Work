
/**
 * Personality class creates personality objects to be stored by the PersonalityList
 * also provides methods to see what the object contains as well as change number of votes
 * @author (Priyesh Patel) 
 * @version (Version 2.3)
 */
public class Personality
{
    //fields: 2 strings and 1 int
    private String name;
    private String sport;
    private int votes;
    
    /**
    *constructor to inisalise object with 2 inputed strings and fixed value 0 for votes
    */
    public Personality(String name, String sport)
    {
        this.name = name;
        this.sport = sport;
        votes = 0;
    }
    
    /**
    *accessor for name
    */
    public String getName()
    {
        return name;
    }
    
    /**
    *accessor for sport
    */
    public String getSport()
    {
        return sport;
    }
    
    /**
    *accessor for votes
    */
    public int getVotes()
    {
        return votes;
    }
    
    /**
    *mutator to increase votes by given parameter 
    *no checks to do, assumption that checked prior to entry
    */
    public void increaseVotes(int numberOfVotes)
    {
        votes = votes + numberOfVotes;
    }
    
    /**
    *accessor to create then return a string containing details of the object
    *simple check so if number of votes is 0 OR greater than 1 the string will display n votes
    *if votes equals 1 the string will display n vote
    */
    public String getDetails()
    {
        String detailsHolder;
        
        if (votes==0 || votes>1)
        {
        detailsHolder = (name + " takes part in " + sport + " and has " + votes + " votes.");
        return detailsHolder;
        }
        else
        {
        detailsHolder = (name + " takes part in " + sport + " and has " + votes + " vote.");
        return detailsHolder;
        }
    }
    
}
