package com.example.techexpoeventmanagementsystemjavafx;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class ProjectManager {
    //list to manage all the projects
    private final List<Project> projects;
    private List<Project> randomSpotlightProjects;
    private boolean randomSelectionDone;

    public ProjectManager() { //constructor initializes project manager with empty lists
        this.projects = new ArrayList<>();
        this.randomSpotlightProjects = new ArrayList<>();
        this.randomSelectionDone = false;
    }

    public boolean projectExists(String projectID) { //method to check if a projectID already exists.
        for (Project project : projects) {
            if (project.getProjectID().equals(projectID)) {
                return true;
            }
        }
        return false;
    }

    public void addProject(Project project) {
        projects.add(project);
    }  //addition of a new project to the list.
    //gets a project by ID,by using encapsulation and data abstraction
    public Project getProjectByID(String projectID) {
        for (Project project : projects) {
            if (project.getProjectID().equals(projectID)) {
                return project;
            }
        }
        return null;
    }
    //Removes a project by ID,showing encapulation by making changes to the list.
    public boolean deleteProjectByID(String projectID) {
        Iterator<Project> iterator = projects.iterator();
        while (iterator.hasNext()) {
            Project project = iterator.next();
            if (project.getProjectID().equals(projectID)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    // Getter for all projects,encapsulation used
    public List<Project> getProjects() {
        return projects;
    }

    public List<Project> getRandomSpotlightProjects() {
        return randomSpotlightProjects;
    }

    public void setRandomSpotlightProjects(List<Project> randomSpotlightProjects) {
        this.randomSpotlightProjects = randomSpotlightProjects;
    }

    public void setRandomSelectionDone(boolean randomSelectionDone) {
        this.randomSelectionDone = randomSelectionDone;
    }

    public boolean isRandomSelectionDone() {
        return randomSelectionDone;
    }

    //sorting methods to sort projects by projectID and score.
    public void sortProjectsByID(){
        for (int i=0; i<projects.size()-1 ; i++){
            for (int j=0; j<projects.size()-i-1 ; j++){
                if (projects.get(j).getProjectID().compareTo(projects.get(j+1).getProjectID())>0){
                    Project temp = projects.get(j);
                    projects.set(j, projects.get(j+1));
                    projects.set(j+1, temp);
                }

            }
        }
    }

    public void sortProjectsByScore(){
        for (int i=0; i<projects.size()-1 ; i++){
            for (int j=0; j<projects.size()-i-1 ; j++){
                if (projects.get(j).getScore()>projects.get(j+1).getScore()){
                    Project temp = projects.get(j);
                    projects.set(j, projects.get(j+1));
                    projects.set(j+1, temp);
                }
            }
        }
    }
    // Saves projects to a text file.
    public void saveProjects(String fileName) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))){
            for (Project project : projects) {
                writer.write(project.getProjectID() + "," + project.getProjectName() + "," + project.getCategory() + "," + project.getTeamMembers() + "," + project.getDescription() + "," + project.getCountry() + "," + project.getTeamLogo() + "," + project.getScore());
                writer.newLine();
            }

        }
        catch (IOException e){
            System.out.println("Error saving projects: " + e.getMessage());
        }
    }
    // Loads projects from the text file,encapsulating file reading.
    public void loadProjects(String fileName) {
        projects.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                if (details.length == 8) {
                    Project project = new Project(details[0], details[1], details[2], details[3], details[4], details[5], details[6]);
                    project.setScore(Integer.parseInt(details[7])); // Set the score from the file
                    projects.add(project);
                }
            }
        }
        catch (IOException e) {
            System.out.println("Error loading projects: " + e.getMessage());
        }

    }

    //getter for loading the top projects by score.
    public List<Project> getTopProjects(int topN) {
        List<Project> sortedProjects = new ArrayList<>(projects);
        sortedProjects.sort((p1, p2) -> Integer.compare(p2.getScore(), p1.getScore()));
        return sortedProjects.subList(0, Math.min(topN, sortedProjects.size()));
    }

}
