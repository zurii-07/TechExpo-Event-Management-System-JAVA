package com.example.techexpoeventmanagementsystemjavafx;

import javafx.scene.layout.VBox;

public class Project {    // Fields to store project details.
    private String projectID;
    private String projectName;
    private String category;
    private String teamMembers;
    private String description;
    private String country;
    private String teamLogo;
    private int score;
    private VBox judgeScoresVBox; //VBox is used to input judges scores.used in the GUI.
    // Constructor used to initialize a project instance.
    public Project(String projectID, String projectName, String category, String teamMembers, String description, String country, String teamLogo) {
        this.projectID = projectID;
        this.projectName = projectName;
        this.category = category;
        this.teamMembers = teamMembers;
        this.description = description;
        this.country = country;
        this.teamLogo = teamLogo;
        this.score = 0;
    }

    // Getters and setters for each field, encapsulating and controlling access to the properties of the project.
    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(String teamMembers) {
        this.teamMembers = teamMembers;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTeamLogo() {
        return teamLogo;
    }

    public void setTeamLogo(String teamLogo) {
        this.teamLogo = teamLogo;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public VBox getJudgeScoresVBox() {
        return judgeScoresVBox;
    }

    public void setJudgeScoresVBox(VBox judgeScoresVBox) {
        this.judgeScoresVBox = judgeScoresVBox;
    }

}

