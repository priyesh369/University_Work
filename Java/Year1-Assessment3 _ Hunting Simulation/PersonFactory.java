import java.awt.Color;
import java.util.Random;
/**
 * A class responsible for creating the initial population
 * of people in the simulation.
 * @author priyesh patel
 * @version version 1.3
 */
public class PersonFactory implements Factory
{
    // The probability that a jedi will be created in any given grid position.
    private static final double JEDI_CREATION_PROBABILITY = 0.02;
    // The probability that a stormtrooper will be created in any given grid position.
    private static final double STORMTROOPER_CREATION_PROBABILITY = 0.09; 
    // The probability that a civilian will be created in any given grid position.
    private static final double CIVILIAN_CREATION_PROBABILITY = 0.06; 

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
        if(rand.nextDouble() <= JEDI_CREATION_PROBABILITY) {
            return new Jedi(true, field, location);
        }
        else if(rand.nextDouble() <= STORMTROOPER_CREATION_PROBABILITY) {
            return new Stormtrooper(true, field, location);
        }
        else if(rand.nextDouble() <= CIVILIAN_CREATION_PROBABILITY) {
            return new Civilian(true, field, location);
        }
        return null;
    }

    /**
     * Associate colors with the animal classes.
     */
    public void setupColors(SimulatorView view)
    {
        assert view != null : "Simulator view is null";

        view.setColor(Jedi.class, Color.green);
        view.setColor(Stormtrooper.class, Color.red);
        view.setColor(Civilian.class, Color.blue);
    }
}
