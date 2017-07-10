//importing three premade classes so they can be used within my code
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
/**
 * PersonalityList Class is designed to hold objects of the Personality class
 * as well as provide methods to veiw and manipulate these objects.
 * @author (Priyesh Patel) 
 * @version (Version 6.4)
 */

public class PersonalityList
{
    //one field of the ArrayList and containing personality objects
    private ArrayList<Personality> pList;

    /**
    *constructor to initialise the arraylist
    */
    public PersonalityList()
    {
        pList= new ArrayList<Personality>();
    }

    /**
    *method to add a personality object to the arraylist
    */
    public void addPersonality(Personality pObjectName)
    {
        pList.add(pObjectName);
    }

    /**
    *method to return the total number of items in pList
    */
    public int getSize()
    {
        return pList.size();
    }

    /**
    *method to print details on all items within pList by using getDetails for personality class
    */
    public void list()
    {
        for(Personality objectHolder : pList)
        {
            String toPrint = objectHolder.getDetails();
            System.out.println(toPrint);
        }
    }
    
    /**
     *  method to find an object using its name field then increment the value of it's vote field by 1
     *  if name matches exactly then add 1 to votes otherwise index incremented and loops again until found or index equals size of arraylist(pList)
     *  if found loop is terminated
     *  if index equals size then error is printed and loop is terminated
     */
    public void voteFor (String name)
    {
        boolean searching = true;
        int index = 0;

        while(searching)
        {
            if(index < pList.size())
            {
                Personality objectHolder = pList.get(index);
                String personalityName = objectHolder.getName();

                if(name.equals(personalityName))
                {
                    objectHolder.increaseVotes(1);
                    searching = false;
                }
            }
            else
            {
                System.out.println("No personality found with the name : " + name);
                searching = false;
            }

            index++;
        }
    }

    /**
     *method to remove entries from pList if they have less votes than the parameter entered
     */
    public void shortlist (int minimumVotes)
    {
        int index = 0;
        
        while(index<pList.size())
        {
                Personality objectHolder = pList.get(index);

                if(objectHolder.getVotes()<minimumVotes) //checks the votes against the parameter then removes the entry or increments index if entry isn't removed
                {
                    pList.remove(index);
                }
                else
                {
                    index++;
                }
        }
    }

    /**
     * method to return an arraylist containing only a given number of entries and based on order of votes
     */
    public ArrayList top(int newSize)
    {
        ArrayList <Personality> pListTop;
        pListTop = new ArrayList<Personality>();
        boolean checking = true;

        //sorter for pList
        Collections.sort(pList, new Comparator<Personality>()
            {
                public int compare(Personality p1, Personality p2)
                {
                    return Integer.compare(p2.getVotes(), p1.getVotes());
                }
            }
            );

        //changes newSize to the size of pList if newSize is greater than it to prevent the following going above the size of the array
        if (newSize>pList.size())
        {
            newSize = pList.size(); 
        }

        //adds entries to pListTop upto the newSize limit
        for (int index = 0; index < newSize; index++)
        {
            pListTop.add(pList.get(index));
        }

        //checks if the last new entry has the same number of votes as the next entry in pList
        //if yes it is added to pListTop and then process repeats until votes no longer match
        if(newSize>0 && newSize<pList.size())
        {
        for(int checkUnit = newSize; checkUnit<pList.size(); checkUnit++)
        {
            if (pListTop.get(checkUnit-1).getVotes() == pList.get(checkUnit).getVotes())
            {
                pListTop.add(pList.get(checkUnit));
            }
            else
            {
                return pListTop;
            }
        }
        }
        
        return pListTop;
    }

}
