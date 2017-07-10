/**
 * Represent a location in a rectangular grid.
 * 
 * @author David J. Barnes, Michael Kolling and Olaf Chitil
 * @version 2016.02.25
 */
public class Location
{
    // Row and column positions.
    private final int row;
    private final int col;

    /**
     * Represent a row and column.
     * @param row The row, >= 0.
     * @param col The column, >= 0.
     */
    public Location(int row, int col)
    {
        assert row >= 0 : "Row is negative";
        assert col >= 0 : "Column is negative";
        
        this.row = row;
        this.col = col;
    }
    
    /**
     * Implement content equality.
     */
    public boolean equals(Object obj)
    {
        if(obj instanceof Location) {
            Location other = (Location) obj;
            return row == other.getRow() && col == other.getCol();
        }
        else {
            return false;
        }
    }
    
    /**
     * Return a string of the form row,column
     * @return A string representation of the location.
     */
    public String toString()
    {
        return row + "," + col;
    }
    
    /**
     * Use the top 16 bits for the row value and the bottom for
     * the column. Except for very big grids, this should give a
     * unique hash code for each (row, col) pair.
     * @return A hashcode for the location.
     */
    public int hashCode()
    {
        return (row << 16) + col;
    }
    
    /**
     * @return The row.
     */
    public int getRow()
    {
        return row;
    }
    
    /**
     * @return The column.
     */
    public int getCol()
    {
        return col;
    }
}
