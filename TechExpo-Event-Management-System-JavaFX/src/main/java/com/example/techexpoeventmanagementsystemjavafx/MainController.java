package com.example.techexpoeventmanagementsystemjavafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;
import javafx.application.Platform;

public class MainController {
    private final ProjectManager projectManager = new ProjectManager(); // Dependency injection of Project Manager object.

    //Controls for buttons that are defined in FXMLs
    @FXML
    private Button AddProjectDetailsButton;

    @FXML
    private Button UpdateProjectDetailsButton;

    @FXML
    private Button DeleteProjectDetailsButton;

    @FXML
    private Button ViewProjectDetailsButton;

    @FXML
    private Button SaveProjectDetailsButton;

    @FXML
    private Button RandomSpotlightSelectionButton;

    @FXML
    private Button AwardWinningProjectsButton;

    @FXML
    private Button VisualizeAwardWinningProjectsButton;

    @FXML
    private Button ExitButton;

    private List<Project> spotlightProjects; //A list to hold projects for random spotlight selection

    @FXML
    public void initialize() {
        projectManager.loadProjects("projects.txt"); //Method called to load existing projects

    }

    //opens a new window for each functionality.uses encapsulation and abstraction.each button does a different function showing polymorphism.
    @FXML
    public void handleAddProjectDetails(ActionEvent event) throws IOException {
        openNewWindow("AddingProjectDetails.fxml", "ADD PROJECT DETAILS", projectManager);
        System.out.println("You have Clicked the Adding Project Details Button");
    }

    @FXML
    private void handleUpdateProjectDetails(ActionEvent event) throws IOException{
        openNewWindow("UpdatingProjectDetails.fxml" ,"UPDATE PROJECT DETAILS", projectManager);
        System.out.println("You have Clicked the Updating Project Details Button");
    }

    @FXML
    private void handleDeleteProjectDetails(ActionEvent event) throws IOException {
        openNewWindow("DeletingProjectDetails.fxml" ,"DELETE PROJECT DETAILS", projectManager);
        System.out.println("You have Clicked the Deleting Project Details Button");
    }

    @FXML
    private void handleViewProjectDetails(ActionEvent event) throws IOException {    // Data Encapsulation is used to protect the project data and the access to data.
        openNewWindow("ViewingProjectDetails.fxml", "View Project Details", projectManager);
        System.out.println("You have Clicked the Viewing Project Details Button");
    }

    @FXML
    private void handleSaveProjectDetails(ActionEvent event){   //encapsulation-saves all project details to text file.
        projectManager.saveProjects("projects.txt");
        System.out.println("Projects details saved to file.");
    }

    @FXML
    private void handleRandomSpotlightSelection(ActionEvent event) throws IOException {  //showing the use of encapsulation and abstraction.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RandomSpotlightSelection.fxml"));
        Parent root = loader.load();

        RandomSpotlightSelectionController controller = loader.getController();
        controller.setProjectManager(projectManager);

        Stage stage = new Stage();
        stage.setTitle("RANDOM SPOTLIGHT SELECTION");
        stage.setScene(new Scene(root));
        stage.showAndWait();

        List<Project> selectedProjects = controller.getRandomSpotlightProjects();
        if (selectedProjects != null && !selectedProjects.isEmpty()) {
            projectManager.setRandomSpotlightProjects(selectedProjects);
            projectManager.setRandomSelectionDone(true);
        } else {
            projectManager.setRandomSelectionDone(false);
        }
    }

    @FXML
    private void handleAwardWinningProjects(ActionEvent event) throws IOException {  //uses inheritance and encapsulation
        if (!projectManager.isRandomSelectionDone()) {
            System.out.println("No projects selected from Random Spotlight Selection.");
            return;
        }

        FXMLLoader awardloader = new FXMLLoader(getClass().getResource("AwardWinningProjects.fxml"));
        Parent awardroot = awardloader.load();

        AwardWinningProjectsController awardController = awardloader.getController();
        awardController.setProjectManager(projectManager);
        awardController.setSpotlightProjects(projectManager.getRandomSpotlightProjects());

        Stage stage = new Stage();
        stage.setTitle("AWARD WINNING PROJECTS");
        stage.setScene(new Scene(awardroot));
        stage.show();

        projectManager.saveProjects("projects.txt");
    }

    @FXML
    private void handleVisualizeAwardWinningProjects(ActionEvent event) throws IOException {  //polymorphism to handle various project categories and encapsulation to manage data.
        if (!projectManager.isRandomSelectionDone()) {
            System.out.println("No projects selected from Random Spotlight Selection.");
            return;
        }

        List<Project> topProjects = projectManager.getTopProjects(3);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("VisualizingAwardWinningProjects.fxml"));
        Parent root = loader.load();

        VisualizingAwardWinningProjectsController controller = loader.getController();
        controller.setAwardWinningProjects(topProjects);

        Stage stage = new Stage();
        stage.setTitle("VISUALIZE AWARD WINNING PROJECTS");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void handleExit(ActionEvent event) {
        Platform.exit();
    }  //encapsulation and abstraction.

    private void openNewWindow(String fxmlFile, String title, ProjectManager projectManager) throws IOException {   //opens a new window(stage) for each functionality in the GUI displaying OOP concepts like abstraction and encapsulation.
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();

        Object controller = loader.getController();

        if (controller instanceof AddingProjectDetailsController) {
            ((AddingProjectDetailsController) controller).setProjectManager(projectManager);
        } else if (controller instanceof UpdatingProjectDetailsController) {
            ((UpdatingProjectDetailsController) controller).setProjectManager(projectManager);
        } else if (controller instanceof DeletingProjectDetailsController) {
            ((DeletingProjectDetailsController) controller).setProjectManager(projectManager);
        } else if (controller instanceof ViewingProjectDetailsController) {
            ((ViewingProjectDetailsController) controller).setProjectManager(projectManager);
        } else if (controller instanceof RandomSpotlightSelectionController) {
            ((RandomSpotlightSelectionController) controller).setProjectManager(projectManager);
        } else if (controller instanceof AwardWinningProjectsController) {
            ((AwardWinningProjectsController) controller).setProjectManager(projectManager);
        } else if (controller instanceof VisualizingAwardWinningProjectsController) {
            ((VisualizingAwardWinningProjectsController) controller).setAwardWinningProjects(projectManager.getProjects());
        }

        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.show();
    }
}