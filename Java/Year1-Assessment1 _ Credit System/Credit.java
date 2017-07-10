
/**
 * Write a description of class Credit here.
 * 
 * @author (Priyesh Rajeevkumar Patel) 
 * @version (Edit 4 : 26th October 2015)
 */
public class Credit
{
    private int availableCredit;
    
    //First Constructor to set credit to 1000 when object created
    
    public Credit()
    {
        availableCredit = 1000;
    }
    
    // Second Constructor:
    // Checks that the parameter is positive
    // if positive then value is assigned to availableCredit
    // else error message is printed and availableCredit is set to 0


    public Credit(int enterCredit)
    {
        if(enterCredit>=0)
        {
            availableCredit = enterCredit;
        }
        else
        {
            System.out.println("Credit cannot be used to initialised with a negative value : " + enterCredit);
            System.out.println("availableCredit has been defaulted to 0");
            availableCredit = 0;
        }
    }
    
    // Accessor Method to return current credit value

    public int getCredit()
    {
        return availableCredit;
    }
    
    // Mutator method 1
    // check parameter is positive
    // if positive the value is assigned to availableCredit
    // else error message is printed and no change occurs
    
    public void setCredit(int enterCredit)
    {
        if(enterCredit>=0)
        {
            availableCredit = enterCredit;
        }
        else
        {
            System.out.println("setCredit was called with a negative value : " + enterCredit);
        }
    }
   
    // Mutator method 2
    // check parameter is positive
    // if positive the value is added to availableCredit and result is assigned to availableCredit
    // else error message is printed and no change occurs
    
    public void topUp(int topUpValue)
    {
        if(topUpValue>=0)
        {
            availableCredit = availableCredit + topUpValue;
        }
        else
        {
            System.out.println("topUp was called with a negative value : " + topUpValue);
        }
    }
    
    // Mutator method 3
    // check if parameter is less than or equal to availableCredit to prevent availableCredit becoming negative
    // if test passes then check parameter is positive
    // if positive the value is subtracted from availableCredit and result is assigned to availableCredit
    // else error message is printed and no change occurs
    // if first test fails then error message is printed and no change occurs
    
    public void reduceCredit(int reductionValue)
    {
        if (reductionValue<=availableCredit)
        {    
            if(reductionValue>=0)
            {
                availableCredit = availableCredit - reductionValue;
            }
            else
            {
                System.out.println("reduceCredit was called with a negative value : " + reductionValue);
            }
        }
        else
        {
            System.out.println("reductionValue cannot be greater than availableCredit : " + reductionValue);
        }
    }
   }