import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * A simple predator-prey simulator, based on a rectangular field
 * containing rabbits and foxes.
 * 
 * @author David J. Barnes, Michael Kolling and Olaf Chitil
 * @version 2016/2/24
 */
public class Simulator
{
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 100;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 100;
    // List of actors in the field.
    private final List<Actor> actors;
    // The current state of the field.
    private final Field field;
    // The current step of the simulation.
    private int step;
    // A graphical view of the simulation.
    private final SimulatorView view;
    // A factory for creating actors - unused as yet.
    private Factory factory;
    

    /**
     * Internal class invariants:
     * All actors in list are alive.
     * Same actors on field and in list.
     * Locations in field and in actors should agree.
     * Simulation and all actors use the same field.
     */
    public void sane()
    {
        assert actors != null : "The list of actors is null";
        assert field != null : "The field is null";
        assert step >= 0 : "Negative step";
        assert view != null : "The view is null";

        // All actors in the list are alive
        for (Actor actor : actors) {
            assert actor.isActive() : "Dead actor in list";
        }

        // First check all actors in the list are in the field
        // and field and location agree.
        for (Actor actor : actors) {
            assert actor.getField() == field : 
            "An actor has a different field: " + 
            actor.getField() + " " + field;
            assert field.getObjectAt(actor.getLocation()) == actor : 
            "Actor not at its location in the field";
        }

        // Then check all actors in the field are in the list;
        // Together ensures that both are the same set of actors.
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                Actor actor = field.getObjectAt(new Location(row,col));
                if (actor != null) {
                    assert actors.contains(actor) :
                    "List does not contain an actor on the field.";
                }
            }
        }
    }

    /**
     * Construct a simulation field with default size.
     */
    public Simulator(Factory factory)
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH, factory);
        sane();
    }

    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width,Factory factory)
    {
        assert (width > 0 && depth > 0) : 
        "The dimensions are not greater than zero.";

        actors = new ArrayList<Actor>();
        field = new Field(depth, width);

        // Create a view of the state of each location in the field.
        view = new SimulatorView(depth, width);
        this.factory = factory;
        factory.setupColors(view);

        // Setup a valid starting point.
        reset();
        sane();
    }

    /**
     * Run the simulation from its current state for a reasonably long period,
     * e.g. 500 steps.
     */
    public void runLongSimulation()
    {
        sane();
        simulate(500);
        sane();
    }

    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps)
    {
        sane();
        for(int step = 1; step <= numSteps && view.isViable(field); step++) {
            simulateOneStep();
        }
        sane();
    }

    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each
     * fox and rabbit.
     */
    public void simulateOneStep()
    {
        sane();

        step++;

        // Provide space for newborn actors.
        List<Actor> newActors = new ArrayList<Actor>(); 

        // Let all actors act.
        for(Iterator<Actor> it = actors.iterator(); it.hasNext(); ) {
            it.next().act(newActors);
        }

        // Add the newly born actors to the main lists.
        actors.addAll(newActors);

        // Remove dead actors.
        for(Iterator<Actor> it = actors.iterator(); it.hasNext(); ) {
            if(! it.next().isActive()) {
                it.remove();
            }
        }

        view.showStatus(step, field);

        sane();
    }

    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        sane();

        step = 0;
        actors.clear();
        field.clear();
        populate();

        // Show the starting state in the view.
        view.showStatus(step, field);

        sane();
    }

    /**
     * Randomly populate the field with foxes and rabbits.
     * Pre-condition: the field is empty
     */
    private void populate()
    {
        sane();

        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                Location location = new Location(row, col);
                Actor holder = factory.optionallyCreateActor(field, location);
                if(holder != null){
                    actors.add(holder);
                }
            }
        }

        sane();
    }
}
