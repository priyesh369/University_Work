import datastructures.AREmployee;
import datastructures.Review;
import datastructures.Reviewer;

import java.util.ArrayList;

/**
 * Created by Priyesh on 18/03/2017.
 */
public class ReviewController {
    private HRDatabase hrDatabase;
    private  AuthenticationController authenticator;

    public ReviewController(){
        hrDatabase = new HRDatabase();
        this.authenticator=new AuthenticationController();
    }

    void createReviewRecord(int staffID, String name, String rev1name, String rev2name, String sector, String jobTitle, String summary, String comments){
        hrDatabase.addReviewRecord(staffID,name,rev1name,rev2name,sector,jobTitle,summary,comments);
    }

    public AREmployee readReviewRecord(){
        return hrDatabase.getReviewRecord(authenticator.getSession().getStaffID());
    }

    void addReviewGoal(int staffID, String goal){
        hrDatabase.addReviewGoal(staffID,goal);
    }
    void ammendReviewRecord(/*multiple parameters*/){}
    void signOffReview(/*multiple parameters*/){}
    public ArrayList<Reviewer> getReviewers(){
        return hrDatabase.getReviewers();
    }
    void getPastReviewRecords(int staffID){}

    private Integer reviewerID = null;
    private Integer reviewerID2 = null;

    boolean saveReviewer(int reviewerID){
        if(this.reviewerID==null){
            this.reviewerID = reviewerID;
            return false;
        }
        else if(this.reviewerID2==null){
            this.reviewerID2 = reviewerID;
            return true;
        }
        return true;

    }

    void allocateReviewers(int reviewID){
        hrDatabase.allocateReviewer(reviewID,reviewerID,reviewerID2);
    }
/*
get reviews not allocated by hr
 */
    ArrayList<Review> getReviews(){
       return hrDatabase.getReviews();
    }

    /*
    get reviews allocated a reviewer
     */
    ArrayList<Review> getAllocatedReviews(){
        return hrDatabase.getAllocatedReviews(authenticator.getSession().getStaffID());
    }

    boolean checkForReviews(){
        return hrDatabase.checkForReviews(authenticator.getSession().getStaffID());
    }

}
