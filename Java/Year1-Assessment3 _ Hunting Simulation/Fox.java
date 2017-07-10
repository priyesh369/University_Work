import java.util.List;
import java.util.Iterator;

/**
 * A simple model of a fox.
 * Foxes age, move, eat rabbits, and die.
 * 
 * @author David J. Barnes and Michael Kolling
 * @version 2008.03.30
 */
public class Fox extends Animal
{
    // Characteristics shared by all foxes (static fields).

    // The age at which a fox can start to breed.
    private static final int BREEDING_AGE = 10;
    // The age to which a fox can live.
    private static final int MAX_AGE = 160;
    // The likelihood of a fox breeding.
    private static final double BREEDING_PROBABILITY = 0.35;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 5;
    // The food value of a single rabbit. In effect, this is the
    // number of steps a fox can go before it has to eat again.
    private static final int RABBIT_FOOD_VALUE = 7;

    // Individual characteristics (instance fields).
    // The fox's food level, which is increased by eating rabbits.
    private int foodLevel;

    /** 
     * Internal class invariants:
     * Those of superclass plus food level within sensible range.
     */
    public void sane()
    {
        super.sane();
        assert 0 <= foodLevel && foodLevel <= RABBIT_FOOD_VALUE :
        "Food level " + foodLevel + " outside range";
    }

    /**
     * Create a fox. A fox can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the fox will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Fox(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        if(randomAge) {
            setAge(rand.nextInt(MAX_AGE));
            foodLevel = rand.nextInt(RABBIT_FOOD_VALUE-1)+1;
        }
        else {
            foodLevel = RABBIT_FOOD_VALUE;
        }

        sane();
    }

    /**
     * This is what the fox does most of the time: it hunts for
     * rabbits. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param field The field currently occupied.
     * @param newFoxes A list to add newly born foxes to.
     */
    public void act(List<Actor> newFoxes)
    {
        sane();

        incrementAge();
        incrementHunger();
        if(isActive()) {
            super.giveBirth(newFoxes);            
            // Move towards a source of food if found.
            Location newLocation = findFood();
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
     * Make this fox more hungry. This could result in the fox's death.
     */
    private void incrementHunger()
    {
        sane();

        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }

        sane();
    }

    /**
     * Tell the fox to look for rabbits adjacent to its current location.
     * Only the first live rabbit is eaten.
     * @return Where food was found, or null if it wasn't.
     */
    private Location findFood()
    {
        sane();

        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Actor actor = field.getObjectAt(where);
            if(actor instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) actor;
                if(rabbit.isActive()) { 
                    rabbit.setDead();
                    foodLevel = RABBIT_FOOD_VALUE;
                    // Remove the dead rabbit from the field.
                    sane();
                    return where;
                }
            }
        }
        sane();
        return null;
    }

    /**
     * 
     * New births will be made into free adjacent locations.
     * @param newActors A list to add newly born fox to, not null.
     * @param field Where the fox is to be placed
     * @param loc the loaction where the fox is to be placed
     */
    protected void giveBirth(List<Actor> newActors,Field field, Location loc)
    {
    Fox young = new Fox(false, field, loc);
    newActors.add(young);
    }

    /**
     * Return the maximal age of the fox.
     * @return The maximal age of the fox.
     */
    public int getMaxAge()
    {
        return MAX_AGE;
    }

    /**
     * Return the breeding age of the fox.
     * @return The breeding age of the fox.
     */
    public int getBreedingAge()
    {
        return BREEDING_AGE;
    }

    /**
     * Return the breeding probability of the fox.
     * @return The breeding probability of the fox.
     */

    public double getBreedingProbability()
    {
        return BREEDING_PROBABILITY;
    }

    /**
     * Return the maximal litter size of the fox.
     * @return The maximal litter size of the fox.
     */

    public int getMaxLitterSize()
    {
        return MAX_LITTER_SIZE;
    }

}
