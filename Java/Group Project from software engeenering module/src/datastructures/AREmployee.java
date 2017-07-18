package datastructures;

import java.util.Date;

/**
 * Created by group 29d on 25/03/2017.
 */
public class AREmployee {
    private String staffId;
    private String name;
    private String rev1name;
    private String rev2name;
    private String section;
    private String jobTitle;
    private String summary;
    private String comments;
    private Date rev1sign;
    private Date rev2sign;
    private Date empsign;

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRev1name() {
        return rev1name;
    }

    public void setRev1name(String rev1name) {
        this.rev1name = rev1name;
    }

    public String getRev2name() {
        return rev2name;
    }

    public void setRev2name(String rev2name) {
        this.rev2name = rev2name;
    }

    public String getSection() {
        return section;
    }

    public void setSecttion(String section) {
        this.section = section;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getRev1sign() {
        return rev1sign;
    }

    public void setRev1sign(Date rev1sign) {
        this.rev1sign = rev1sign;
    }

    public Date getRev2sign() {
        return rev2sign;
    }

    public void setRev2sign(Date rev2sign) {
        this.rev2sign = rev2sign;
    }

    public Date getEmpsign() {
        return empsign;
    }

    public void setEmpsign(Date empsign) {
        this.empsign = empsign;
    }
}
