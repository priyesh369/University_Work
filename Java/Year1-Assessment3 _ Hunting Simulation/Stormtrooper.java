import java.util.List;
import java.util.Iterator;

/**
 * A simple model of a stormtrooper.
 * Stormtrooperes age, move, kill civilians, and die.
 * 
 * @author Priyesh Patel
 * @version 2.2
 */
public class Stormtrooper extends Person
{
    // Characteristics shared by all stormtrooperes (static fields).

    // The age at which a stormtrooper can start to breed.
    private static final int MATING_AGE = 18;
    // The age to which a stormtrooper can live.
    private static final int MAX_AGE = 100;
    // The likelihood of a stormtrooper mating.
    private static final double MATING_PROBABILITY = 0.00;
    // The maximum number of births.
    private static final int MAX_KID_SIZE = 0;


    /** 
     * Internal class invariants:
     * Those of superclass plus food level within sensible range.
     */
    public void sane()
    {
        super.sane();
    }

    /**
     * Create a stormtrooper. A stormtrooper can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the stormtrooper will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Stormtrooper(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        if(randomAge) {
            setAge(rand.nextInt(MAX_AGE));
        }

        sane();
    }

    /**
     * This is what the stormtrooper does most of the time: it hunts for
     * stormtoopers. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param field The field currently occupied.
     * @param newStormtrooperes A list to add newly born stormtrooperes to.
     */
    public void act(List<Actor> newStormtrooperes)
    {
        sane();

        incrementAge();
        if(isActive()) {
            super.giveBirth(newStormtrooperes);            
            // Move towards a source of food if found.
            Location newLocation = findTarget();
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
     * Tell the stormtrooper to look for stormtoopers adjacent to its current location.
     * Only the first live stormtooper is eaten.
     * @return Where food was found, or null if it wasn't.
     */
    private Location findTarget()
    {
        sane();

        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Actor actor = field.getObjectAt(where);
            if(actor instanceof Civilian) {
                Civilian civilian = (Civilian) actor;
                if(civilian.isActive()) { 
                    civilian.setDead();
                    // Remove the dead civilian from the field.
                    sane();
                    return where;
                }
            }
        }
        sane();
        return null;
    }

    protected void giveBirth(List<Actor> newActors,Field field, Location loc)
    {
        Stormtrooper young = new Stormtrooper(false, field, loc);
        newActors.add(young);
    }
    
    /**
     * Return the maximal age of the stormtrooper.
     * @return The maximal age of the stormtrooper.
     */
    public int getMaxAge()
    {
        return MAX_AGE;
    }

    /**
     * Return the mating age of the stormtrooper.
     * @return The mating age of the stormtrooper.
     */
    public int getMatingAge()
    {
        return MATING_AGE;
    }

    /**
     * Return the mating probability of the stormtrooper.
     * @return The mating probability of the stormtrooper.
     */

    public double getMatingProbability()
    {
        return MATING_PROBABILITY;
    }

    /**
     * Return the maximal kid size of the stormtrooper.
     * @return The maximal kid size of the stormtrooper.
     */

    public int getMaxKid()
    {
        return MAX_KID_SIZE;
    }
}