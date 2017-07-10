import shed.mbed.*;
/**
 * Write a description of class Program here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Program
{
    private int goal;
    private MBed mbed;
    private Potentiometer pot1;
    private Potentiometer pot2;
    /**
     * Open a connection to the MBED.
     */
    public Program()
    {
        mbed = new MBed();
        goal = 0;
    }

    /**
     * The starting point for the interactions.
     */
    public void run()
    {
        mbed.printLCDText(0, 0, "Welcome to FitBot");
        pot1 = mbed.getPotentiometer(1);
        pot2 = mbed.getPotentiometer(2);
        int goal = goalSetter(pot1, pot2);
        int currentSteps = 0;
        boolean goalNotMet = true;
        boolean stage = true;
        while(goalNotMet) {
            AccelerometerData acData = mbed.readShieldAccelerometer();
            mbed.printLCDText(0, 0, "Steps : " + currentSteps + "/" + goal);
            if(acData.getZ()>0.4 && stage == true){
                stage = false;
                currentSteps++;
            }
            else if(acData.getZ()<-0.4 && stage == false){
                stage = true;
                currentSteps++;
            }

            if(currentSteps == goal){
                goalNotMet = false;
                for(int hold = 0; hold<100; hold++){
                    mbed.printLCDText(0, 10, "You reached your goal!");
                    mbed.setShieldLED(false,true,false);
                    mbed.setBoardLED(false,true,false);
                    mbed.setPiezo(0.5,5000);
                    mbed.setPiezo(0.6,6090);
                    mbed.setPiezo(0.7,7070);
                    mbed.setPiezo(0.8,8000);
                }
                mbed.clearLCD();
                mbed.piezoOff();
                mbed.setShieldLED(false,false,false);
                mbed.setBoardLED(false,false,false);
            }

            if(currentSteps == goal/4){
                for(int hold = 0; hold<10; hold++){
                    mbed.setShieldLED(true,false,false);
                    mbed.setBoardLED(true,false,false);
                    mbed.setPiezo(0.5,500);
                    mbed.setPiezo(0.6,600);
                    mbed.setPiezo(0.7,700);
                    mbed.setPiezo(0.8,800);
                    mbed.printLCDText(0, 10, "keep going, 1/4 complete!");
                }
                mbed.clearLCD();
                mbed.piezoOff();
                mbed.setShieldLED(false,false,false);
                mbed.setBoardLED(false,false,false);
            }
            
            if(currentSteps == goal/2){
                for(int hold = 0; hold<10; hold++){
                    mbed.setShieldLED(true,true,false);
                    mbed.setBoardLED(true,true,false);
                    mbed.setPiezo(0.5,500);
                    mbed.setPiezo(0.6,600);
                    mbed.setPiezo(0.7,700);
                    mbed.setPiezo(0.8,800);
                    mbed.printLCDText(0, 10, "keep going, 1/2 complete!");
                }
                mbed.clearLCD();
                mbed.piezoOff();
                mbed.setShieldLED(false,false,false);
                mbed.setBoardLED(false,false,false);
            }
            
            if(currentSteps == (goal - goal/4)){
                for(int hold = 0; hold<10; hold++){
                    mbed.setShieldLED(false,true,true);
                    mbed.setBoardLED(false,true,true);
                    mbed.setPiezo(0.5,500);
                    mbed.setPiezo(0.6,600);
                    mbed.setPiezo(0.7,700);
                    mbed.setPiezo(0.8,800);
                    mbed.printLCDText(0, 10, "keep going, 3/4 complete!");
                }
                mbed.clearLCD();
                mbed.piezoOff();
                mbed.setShieldLED(false,false,false);
                mbed.setBoardLED(false,false,false);
            }
            
            boolean upPressed = mbed.getButton(ButtonID.UP).isPressed();
            if(upPressed){
                mbed.clearLCD();
                    for(int hold = 0; hold<40; hold++){
                        mbed.printLCDText(0, 0, "Total since last reset:");
                        mbed.printLCDText(0, 10, "" + currentSteps);
                    }
            }
        }
        finish();
    }

    public int goalSetter(Potentiometer pot1, Potentiometer pot2)
    {
        boolean running = true;
        int pot1Value = 0;
        int pot2Value = 0;
        int returnValue = 0;
        while(running){
            if(pot1.getValue()<0.1 && pot1.getValue()>0.0){
                pot1Value = 0;
            }
            else if(pot1.getValue()<0.2 && pot1.getValue()>0.1){
                pot1Value = 1000;
            }
            else if(pot1.getValue()<0.3 && pot1.getValue()>0.2){
                pot1Value = 2000;
            }
            else if(pot1.getValue()<0.4 && pot1.getValue()>0.3){
                pot1Value = 3000;
            }
            else if(pot1.getValue()<0.5 && pot1.getValue()>0.4){
                pot1Value = 4000;
            }
            else if(pot1.getValue()<0.6 && pot1.getValue()>0.5){
                pot1Value = 5000;
            }
            else if(pot1.getValue()<0.7 && pot1.getValue()>0.6){
                pot1Value = 6000;
            }
            else if(pot1.getValue()<0.8 && pot1.getValue()>0.7){
                pot1Value = 7000;
            }
            else if(pot1.getValue()<0.9 && pot1.getValue()>0.8){
                pot1Value = 8000;
            }
            else if(pot1.getValue()<1 && pot1.getValue()>0.9){
                pot1Value = 9000;
            }

            if(pot2.getValue()<0.1 && pot2.getValue()>0.0){
                pot2Value = 0;
            }
            else if(pot2.getValue()<0.2 && pot2.getValue()>0.1){
                pot2Value = 100;
            }
            else if(pot2.getValue()<0.3 && pot2.getValue()>0.2){
                pot2Value = 200;
            }
            else if(pot2.getValue()<0.4 && pot2.getValue()>0.3){
                pot2Value = 300;
            }
            else if(pot2.getValue()<0.5 && pot2.getValue()>0.4){
                pot2Value = 400;
            }
            else if(pot2.getValue()<0.6 && pot2.getValue()>0.5){
                pot2Value = 500;
            }
            else if(pot2.getValue()<0.7 && pot2.getValue()>0.6){
                pot2Value = 600;
            }
            else if(pot2.getValue()<0.8 && pot2.getValue()>0.7){
                pot2Value = 700;
            }
            else if(pot2.getValue()<0.9 && pot2.getValue()>0.8){
                pot2Value = 800;
            }
            else if(pot2.getValue()<1 && pot2.getValue()>0.9){
                pot2Value = 900;
            }
            returnValue = pot1Value + pot2Value;
            mbed.printLCDText(0, 10, "Goal : " + returnValue);
            boolean firePressed = mbed.getButton(ButtonID.FIRE).isPressed();
            if(firePressed == true)
            {
                if(returnValue == 0){
                    mbed.clearLCD();
                    for(int hold = 0; hold<40; hold++){
                        mbed.printLCDText(0, 0, "Welcome to FitBot");
                        mbed.printLCDText(0, 10, "you haven't set a goal yet...");
                    }
                    mbed.clearLCD();
                    mbed.printLCDText(0, 0, "Welcome to FitBot");
                    firePressed = false;
                }
                else{
                    running = false;
                    mbed.clearLCD();

                    for(int hold = 0; hold<40; hold++){
                        mbed.printLCDText(0, 0, "Welcome to FitBot");
                        mbed.printLCDText(0, 10, "Goal Set to : " + returnValue);
                        mbed.printLCDText(0, 20, "Get moving :)" + returnValue);
                    }
                    mbed.clearLCD();
                }
            }
            
        }
        return returnValue;
    }

    /**
     * Close the connection to the MBED.
     */
    public void finish()
    {
        mbed.close();
    }

    /**
     * A simple support method for sleeping the program.
     * @param millis The number of milliseconds to sleep for.
     */
    private void sleep(int millis)
    {
        try {
            Thread.sleep(millis);
        } 
        catch (InterruptedException ex) {
            // Nothing we can do.
        }
    }
}
