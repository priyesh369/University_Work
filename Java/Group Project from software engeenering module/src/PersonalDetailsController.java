import datastructures.PDEmployee;
import java.util.ArrayList;
import java.util.Date;

/**
 * handles all the logic that is used by the Personal Details GUI classes.
 * Created by Group 29D on 03-Mar-17.
 */
 class PersonalDetailsController {
    private HRDatabase hrDB;

    /**
     * constructor for the PersonalDetailsControler
     */
    PersonalDetailsController(){
        hrDB=new HRDatabase();
    }

    /**
     * adds the personal details of an employee
     * @param staffID- value of the staffID (6 digit value)
     * @param surname- value of surname of employee
     * @param name value of name of employee
     * @param dob- value of dob of employee
     * @param address- value of address of employee
     * @param town- value of town of employee
     * @param county- value of county of employee
     * @param postCode- value of postCode of employee
     * @param telNo- value of telNo of employee
     * @param mobileNo- value of mobileNo of employee
     * @param emergencyContact- value of emergencyContact of employee
     * @param emergencyContactNo- value of emergencyContactNo of employee
     */
    public boolean createPersonalDetails(Integer staffID, String surname, String name, Date dob, String address, String town, String county,
                                       String postCode, String telNo, String mobileNo, String emergencyContact, String emergencyContactNo){


         hrDB.addPDRecord(staffID, surname, name, dob, address, town, county,
                    postCode, telNo, mobileNo, emergencyContact, emergencyContactNo);
         return true;


    }

    public boolean amendPersonalDetails(Integer staffID, String surname, String name, Date dob, String address, String town, String county,
                                      String postCode, String telNo, String mobileNo, String emergencyContact, String emergencyContactNo){


        hrDB.updatePDRecord(staffID, surname, name, dob, address, town, county,
                postCode, telNo, mobileNo, emergencyContact, emergencyContactNo);
        return true;


    }

    public boolean deleteRecord(Integer StaffID){
        hrDB.deleteRecord(StaffID);
        return true;
    }

    public ArrayList<PDEmployee> getData(){
       return hrDB.getPersonalDetails();
    }

    public PDEmployee readPersonalDetails(String staffId){
        return hrDB.getPDRecord(staffId);
    }

    boolean staffIDFree(Integer staffID){
        return hrDB.staffIDFree(staffID);
    }




}
