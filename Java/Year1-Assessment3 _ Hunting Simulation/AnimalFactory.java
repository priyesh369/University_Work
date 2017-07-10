import java.awt.Color;
import java.util.Random;
/**
 * A class responsible for creating the initial population
 * of animals in the simulation.
 * 
 * @author David J. Barnes, Michael Kolling and Olaf Chitil
 * @version 2016.02.25
 */
public class AnimalFactory implements Factory
{    
    // The probability that a fox will be created in any given grid position.
    private static final double FOX_CREATION_PROBABILITY = 0.02;
    // The probability that a rabbit will be created in any given grid position.
    private static final double RABBIT_CREATION_PROBABILITY = 0.08; 

    /**
     * Optionally create an animal.
     * Whether an animal is created will depend upon probabilities
     * of animal creation.
     * For proper implementation this method may need some parameters.
     * @return A newly created Animal, or null if none is created.
     */
    public Actor optionallyCreateActor(Field field, Location location)
    {
        Random rand = Randomizer.getRandom();
        if(rand.nextDouble() <= FOX_CREATION_PROBABILITY) {
            return new Fox(true, field, location);
        }
        else if(rand.nextDouble() <= RABBIT_CREATION_PROBABILITY) {
            return new Rabbit(true, field, location);
        }
        return null;
    }

    /**
     * Associate colors with the animal classes.
     */
    public void setupColors(SimulatorView view)
    {
        assert view != null : "Simulator view is null";

        view.setColor(Rabbit.class, Color.orange);
        view.setColor(Fox.class, Color.blue);
    }
}
