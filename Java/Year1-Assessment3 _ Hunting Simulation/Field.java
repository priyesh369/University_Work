import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Represent a rectangular grid of field positions.
 * Each position is able to store a single actor.
 * 
 * @author David J. Barnes, Michael Kolling and Olaf Chitil
 * @version 2016.02.25
 */
public class Field
{
    // A random number generator for providing random locations.
    private static final Random rand = Randomizer.getRandom();
    
    // The depth and width of the field.
    private int depth, width;
    // Storage for the actors.
    private final Actor[][] field;

    /**
     * Represent a field of the given dimensions.
     * @param depth The depth of the field; positive.
     * @param width The width of the field; positive.
     */
    public Field(int depth, int width)
    {
        assert depth > 0 : "Depth not positive";
        assert width > 0 : "Width not positive";
        
        this.depth = depth;
        this.width = width;
        field = new Actor[depth][width];
        clear();
    }
    
    /**
     * Empty the field.
     */
    public void clear()
    {
        for(int row = 0; row < depth; row++) {
            for(int col = 0; col < width; col++) {
                field[row][col] = null;
            }
        }
    }
    
    /**
     * Clear the given location. It is inside the field.
     * @param location The location to clear.
     */
    public void clear(Location location)
    {
        assert inside(location) : "Location not within the field";
        
        field[location.getRow()][location.getCol()] = null;
    }
    
    /**
     * Place an actor at the given location, inside the field.
     * At the given locatin the field is free.
     * @param actor The actor to be placed, not null.
     * @param row Row coordinate of the location.
     * @param col Column coordinate of the location.
     */
    public void place(Actor actor, int row, int col)
    {
        assert actor != null : "Actor is null";
        assert inside(new Location(row,col)) : "Location not within field";
        assert getObjectAt(row,col) == null : "Field location is free";
        
        place(actor, new Location(row, col));
    }
    
    /**
     * Place an actor at the given location.
     * @param actor The actor to be placed, not null.
     * @param location Where to place the actor, inside the field.
     */
    public void place(Actor actor, Location location)
    {
        assert actor != null : "Actor is null";
        assert inside(location) : "Location not within field";
        assert getObjectAt(location) == null : "Field location is free";
        
        field[location.getRow()][location.getCol()] = actor;
    }
    
    /**
     * Return the actor at the given location, if any.
     * @param location Where, inside the field.
     * @return The actor at the given location, or null if there is none.
     */
    public Actor getObjectAt(Location location)
    {
        assert inside(location) : "Location not within field";
        
        return getObjectAt(location.getRow(), location.getCol());
    }
    
    /**
     * Return the actor at the given location (inside the field), if any.
     * @param row The desired row.
     * @param col The desired column.
     * @return The actor at the given location, or null if there is none.
     */
    public Actor getObjectAt(int row, int col)
    {
        assert inside(new Location(row,col)) : "Location not within field";

        return field[row][col];
    }
    
    /**
     * Generate a random location that is adjacent to the
     * given location, or is the same location.
     * The returned location will be within the valid bounds
     * of the field.
     * @param location The location from which to generate an adjacency, inside the field.
     * @return A valid location within the grid area.
     */
    public Location randomAdjacentLocation(Location location)
    {
        assert inside(location) : "Location not within field";

        List<Location> adjacent = adjacentLocations(location);
        Location newLocation = adjacent.get(0);
        
        assert inside(newLocation) : "New location not within field";
        return newLocation;
    }
    
    /**
     * Get a shuffled list of the free adjacent locations.
     * @param location Get locations adjacent to this, inside the field.
     * @return A list of free adjacent locations.
     */
    public List<Location> getFreeAdjacentLocations(Location location)
    {
        assert inside(location) : "Location not within field";

        List<Location> free = new LinkedList<Location>();
        List<Location> adjacent = adjacentLocations(location);
        for(Location next : adjacent) {
            if(getObjectAt(next) == null) {
                free.add(next);
            }
        }
        return free;
    }
    
    /**
     * Try to find a free location that is adjacent to the
     * given location. If there is none, return null.
     * The returned location will be within the valid bounds
     * of the field.
     * @param location The location from which to generate an adjacency, inside the field.
     * @return A valid location within the grid area or null.
     */
    public Location freeAdjacentLocation(Location location)
    {
        assert inside(location) : "Location not within field";

        // The available free ones.
        List<Location> free = getFreeAdjacentLocations(location);
        if(free.size() > 0) {
            return free.get(0);
        }
        else {
            return null;
        }
    }

    /**
     * Return a shuffled list of locations adjacent to the given one.
     * The list will not include the location itself.
     * All locations will lie within the grid.
     * @param location The location from which to generate adjacencies, inside the field.
     * @return A list of locations adjacent to that given.
     */
    public List<Location> adjacentLocations(Location location)
    {
        assert inside(location) : "Location not within field";
    
        // The list of locations to be returned.
        List<Location> locations = new LinkedList<Location>();
        if(location != null) {
            int row = location.getRow();
            int col = location.getCol();
            for(int roffset = -1; roffset <= 1; roffset++) {
                int nextRow = row + roffset;
                if(nextRow >= 0 && nextRow < depth) {
                    for(int coffset = -1; coffset <= 1; coffset++) {
                        int nextCol = col + coffset;
                        // Exclude invalid locations and the original location.
                        if(nextCol >= 0 && nextCol < width && (roffset != 0 || coffset != 0)) {
                            locations.add(new Location(nextRow, nextCol));
                        }
                    }
                }
            }
            
            // Shuffle the list. Several other methods rely on the list
            // being in a random order.
            Collections.shuffle(locations, rand);
        }
        return locations;
    }

    /**
     * Return the depth of the field.
     * @return The depth of the field.
     */
    public int getDepth()
    {
        return depth;
    }
    
    /**
     * Return the width of the field.
     * @return The width of the field.
     */
    public int getWidth()
    {
        return width;
    }
    
    /**
     * Check whether given location is within the field (which includes not being null).
     * @return The decision.
     */
    public boolean inside(Location location)
    {
        return location != null && location.getRow() < depth && location.getCol() < width;
    }
}
