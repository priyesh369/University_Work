import java.util.List;
import java.util.Random;

/**
 * A simple model of a rabbit.
 * Rabbits age, move, breed, and die.
 * 
 * @author David J. Barnes and Michael Kolling
 * @version 2008.03.30
 */
public class Rabbit extends Animal
{
    // Characteristics shared by all rabbits (static fields).

    // The age at which a rabbit can start to breed.
    private static final int BREEDING_AGE = 5;
    // The age to which a rabbit can live.
    private static final int MAX_AGE = 40;
    // The likelihood of a rabbit breeding.
    private static final double BREEDING_PROBABILITY = 0.15;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 4;

    /**
     * Create a new rabbit. A rabbit may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the rabbit will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Rabbit(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        if(randomAge) {
            setAge(rand.nextInt(MAX_AGE));
        }
    }

    /**
     * This is what the rabbit does most of the time - it runs 
     * around. Sometimes it will breed or die of old age.
     * @param newRabbits A list to add newly born rabbits to.
     */
    public void act(List<Actor> newRabbits)
    {
        incrementAge();
        if(isActive()) {
            super.giveBirth(newRabbits);            
            // Try to move into a free location.
            Location newLocation = getField().freeAdjacentLocation(getLocation());
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    }

    /**
     * 
     * New births will be made into free adjacent locations.
     * @param newActors A list to add newly born rabbits to, not null.
     * @param field Where the rabbit is to be placed
     * @param loc the loaction where the rabbit is to be placed
     */
    protected void giveBirth(List<Actor> newActors,Field field, Location loc)
    {
        Rabbit young = new Rabbit(false, field, loc);
        newActors.add(young);
    }

    /**
     * Return the maximal age of the rabbit.
     * @return The maximal age of the rabbit.
     */
    public int getMaxAge()
    {
        return MAX_AGE;
    }

    /**
     * Return the breeding age of the rabbit.
     * @return The breeding age of the rabbit.
     */
    public int getBreedingAge()
    {
        return BREEDING_AGE;
    }

    /**
     * Return the breeding probability of the rabbit.
     * @return The breeding probability of the rabbit.
     */

    public double getBreedingProbability()
    {
        return BREEDING_PROBABILITY;
    }

    /**
     * Return the maximal litter size of the rabbit.
     * @return The maximal litter size of the rabbit.
     */

    public int getMaxLitterSize()
    {
        return MAX_LITTER_SIZE;
    }

}
