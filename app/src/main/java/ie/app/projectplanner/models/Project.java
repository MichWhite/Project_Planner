package ie.app.projectplanner.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by MichWhite on 28-Sep-16.
 */
public class Project {
    public String key;
    @JsonIgnore
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int ProjectId;
    public String ProjectName;
    public String typeOfConstruction;
    public String MemberNames;
    public int startDay;
    public int startMonth;
    public int startYear;
    public int finishDay;
    public int finishMonth;
    public int finishYear;

    public double ProjectLat;
    public double ProjectLong;
    public String ProjectAddress;

    public Project(){

    }



    public Project (int ProjectId, String ProjectName, String typeOfConstruction, String MemberNames,
                    int startDay, int startMonth, int startYear, int finishDay,
                    int finishMonth, int finishYear, String ProjectAddress, double ProjectLat, double ProjectLong)
    {
        this.ProjectId = ProjectId;
        this.ProjectName = ProjectName;
        this.startDay = startDay;
        this.startMonth = startMonth;
        this.startYear = startYear;
        this.finishDay = finishDay;
        this.finishMonth = finishMonth;
        this.finishYear = finishYear;
        this.typeOfConstruction = typeOfConstruction;
        this.MemberNames = MemberNames;

        this.ProjectAddress = ProjectAddress;
        this.ProjectLong = ProjectLong;
        this.ProjectLat = ProjectLat;
    }



//    public void setValues(Project tempProject) {
//        ProjectId;
//        ProjectName;
//        public int typeOfConstruction;
//        public String MemberNames;
//        public int startDay;
//        public int startMonth;
//        public int startYear;
//        public int finishDay;
//        public int finishMonth;
//        public int finishYear;
//
//        public double ProjectLat;
//        public double ProjectLong;
//        public String ProjectAddress;
//    }
}
