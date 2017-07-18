package datastructures;

import java.util.Date;

/**
 * Created by Group 29D on 26/03/2017.
 */
public class Review {
    private int reviewID;
    private String StaffID;
    private String name;
    private String director;
    private String secondDirector;
    private String section;
    private String jobTitle;
    private String perfomanceSummary;
    private String reviewerComments;
    private Date revieweeSignature;
    private Date reviewerSignature;
    private Date secondReviewerSignature;

    public int getReviewID() {
        return reviewID;
    }

    public void setReviewID(int reviewID) {
        this.reviewID = reviewID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getSecondDirector() {
        return secondDirector;
    }

    public void setSecondDirector(String secondDirector) {
        this.secondDirector = secondDirector;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getPerfomanceSummary() {
        return perfomanceSummary;
    }

    public void setPerfomanceSummary(String perfomanceSummary) {
        this.perfomanceSummary = perfomanceSummary;
    }

    public String getReviewerComments() {
        return reviewerComments;
    }

    public void setReviewerComments(String reviewerComments) {
        this.reviewerComments = reviewerComments;
    }

    public Date getRevieweeSignature() {
        return revieweeSignature;
    }

    public void setRevieweeSignature(Date revieweeSignature) {
        this.revieweeSignature = revieweeSignature;
    }

    public Date getReviewerSignature() {
        return reviewerSignature;
    }

    public void setReviewerSignature(Date secondReviewerSignature) {
        this.reviewerSignature = reviewerSignature;
    }
    public Date getSecondReviewerSignature() {
        return secondReviewerSignature;
    }

    public void setSecondReviewerSignature(Date secondReviewerSignature) {
        this.secondReviewerSignature = secondReviewerSignature;
    }


    public String getStaffID() {
        return StaffID;
    }

    public void setStaffID(String  staffID) {
        StaffID = staffID;
    }
}
