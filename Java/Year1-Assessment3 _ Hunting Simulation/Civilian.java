import java.util.List;
import java.util.Iterator;

/**
 * A simple model of a civilian.
 * Civilianes age, move, and die.
 * 
 * @author priyesh patel
 * @version 1
 */
public class Civilian extends Person
{
    // Characteristics shared by all civilianes (static fields).

    // The age at which a civilian can start to breed.
    private static final int MATING_AGE = 13;
    // The age to which a civilian can live.
    private static final int MAX_AGE = 50;
    // The likelihood of a civilian mating.
    private static final double MATING_PROBABILITY = 0.02;
    // The maximum number of births.
    private static final int MAX_KID_SIZE = 3;


    /** 
     * Internal class invariants:
     * Those of superclass plus food level within sensible range.
     */
    public void sane()
    {
        super.sane();
    }

    /**
     * Create a civilian. A civilian can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the civilian will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Civilian(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        if(randomAge) {
            setAge(rand.nextInt(MAX_AGE));
        }

        sane();
    }

    /**
     * This is what the civilian does most of the time: it hunts for
     * civilians. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param field The field currently occupied.
     * @param newCivilianes A list to add newly born civilianes to.
     */
    public void act(List<Actor> newCivilianes)
    {
        sane();

        incrementAge();
        if(isActive()) {
            super.giveBirth(newCivilianes);            
            // Move towards a source of food if found.
            Location newLocation = findNextLocation();
            if(newLocation == null) { 
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            // See if it was possible to move.
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }

        sane();
    }


    /**
     * Tell the civilian to look for civilians adjacent to its current location.
     * Only the first live civilian is eaten.
     * @return Where food was found, or null if it wasn't.
     */
    private Location findNextLocation()
    {
        sane();

        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Actor actor = field.getObjectAt(where);
            if(actor == null) {
                    return where;
            }
        }
        sane();
        return null;
    }

    protected void giveBirth(List<Actor> newActors,Field field, Location loc)
    {
        Civilian young = new Civilian(false, field, loc);
        newActors.add(young);
    }
    
    /**
     * Return the maximal age of the civilian.
     * @return The maximal age of the civilian.
     */
    public int getMaxAge()
    {
        return MAX_AGE;
    }

    /**
     * Return the mating age of the civilian.
     * @return The mating age of the civilian.
     */
    public int getMatingAge()
    {
        return MATING_AGE;
    }

    /**
     * Return the mating probability of the civilian.
     * @return The mating probability of the civilian.
     */

    public double getMatingProbability()
    {
        return MATING_PROBABILITY;
    }

    /**
     * Return the maximal kid size of the civilian.
     * @return The maximal kid size of the civilian.
     */

    public int getMaxKid()
    {
        return MAX_KID_SIZE;
    }
}