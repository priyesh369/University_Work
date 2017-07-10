
/**
 * an interface to be implemented by all factories.
 * 
 * @author priyesh patel 
 * @version version 1
 */
public interface Factory
{
    public void setupColors(SimulatorView view);
    
    public Actor optionallyCreateActor(Field field, Location location);
}
