import java.util.List;
import java.util.Random;

/**
 * A class representing shared characteristics of persons.
 * 
 * @author Priyesh patel
 * @version 1.0
 */
public abstract class Person implements Actor
{
    // Characteristics shared by all animals (static fields).
    // A shared random number generator to control breeding.
    protected static final Random rand = Randomizer.getRandom();

    // The animal's age.
    private int age;
    // Whether the animal is alive or not.
    private boolean alive;
    // The animal's field.
    private final Field field;
    // The animal's position in the field.
    private Location location;

    /**
     * Internal class invariants:
     * Age not negative.
     * Animals turn at most maximum age plus 1.
     * Alive animals are at most of maximum age.
     * Field and location are not null.
     */
    public void sane()
    {
        assert age >= 0 : "The age is negative";
        assert age <= getMaxAge() + 1 : "Too old"; 
        assert field != null : "The field is null";
        assert location != null : "The location is null";
    }

    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied, not null.
     * @param location The location within the field.
     */
    public Person(Field field, Location location)
    {
        assert field != null : "Field is null";
        assert field.inside(location) : "Location is not within the field";

        age = 0;
        alive = true;
        this.field = field;
        setLocation(location);

        this.sane();
    }

    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to add newly born animals to.
     */
    abstract public void act(List<Actor> newActors);

    /**
     * Set the animal's age to the given value.
     * Age is not negative.
     * @param a New age.
     */
    public void setAge(int a) 
    {
        assert a >= 0 : "Setting age negative";

        sane();
        age = a;
        sane();
    }

    /**
     * Increase the age. This could result in the animal's death.
     */
    public void incrementAge()
    {
        sane();

        age++;
        if(age > getMaxAge()) {
            setDead();
        }

        sane();
    }

    /**
     * Check whether or not this fox is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newFoxes A list to add newly born foxes to, not null.
     */
    protected void giveBirth(List<Actor> newActors)
    {
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            giveBirth(newActors,field,loc);
        }
        
        sane();
    }

    abstract protected void giveBirth(List<Actor> newActors,Field field, Location loc);
    
    /**
     * An animal can breed if it has reached the breeding age.
     * @return Whether the animal can breed.
     */
    public boolean canBreed()
    {
        sane();
        return age >= getMatingAge();
    }  

    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    protected int breed()
    {
        sane();

        int births = 0;
        if(canBreed() && rand.nextDouble() <= getMatingProbability()) {
            births = rand.nextInt(getMaxKid()) + 1;
        }

        sane();
        return births;
    }

    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    public boolean isActive()
    {
        sane();

        return alive;
    }

    /**
     * Indicate that the animal is no longer alive.
     * It is removed from the field.
     * Works whether the animal is already dead or not.
     */
    public void setDead()
    {
        sane();

        if (alive) {
            alive = false;
            field.clear(location);
        }

        sane();
    }

    /**
     * Return the animal's location.
     * @return The animal's location.
     */
    public Location getLocation()
    {
        sane();

        return location;
    }

    /**
     * Return the animal's field.
     * @return The animal's field.
     */
    public Field getField()
    {
        sane();

        return field;
    }

    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location, within field.
     */
    public void setLocation(Location newLocation)
    {
        assert field.inside(newLocation) : "Location is not within the field";
        // sane();  no, because this method is also used in constructor

        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);

        sane();
    }

    /**
     * Return the breeding probability of this animal.
     * @return The breeding probability of this animal.
     */

    abstract public double getMatingProbability();

    /**
     * Return the maximal litter size of this animal.
     * @return The maximal litter size of this animal.
     */

    abstract public int getMaxKid();

    /** 
     * Return the breeding age of this animal.
     * @return The breeding age of this animal.
     */
    abstract public int getMatingAge();

    /**
     * Return the maximal age of this animal.
     * @return The maximal age of this animal.
     */
    abstract public int getMaxAge();

}
