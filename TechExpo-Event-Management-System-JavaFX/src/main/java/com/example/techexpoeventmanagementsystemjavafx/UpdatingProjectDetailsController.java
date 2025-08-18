package com.example.techexpoeventmanagementsystemjavafx;


import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import java.io.File;
import javafx.scene.control.Alert;

public class UpdatingProjectDetailsController {
    @FXML
    private TextField projectIDField;
    @FXML
    private TextField projectNameField;
    @FXML
    private TextField categoryField;
    @FXML
    private TextField teamMembersField;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField countryField;
    @FXML
    private TextField teamLogoField;

    public ProjectManager projectManager; // Reference to manage projects.
    //Setter for dependency injection,showcasing encapsulation and inversion of control.
    public void setProjectManager(ProjectManager projectManager) {
        this.projectManager = projectManager;
        initializeProjectManager();
    }

    @FXML
    public void initializeProjectManager() { // Loads project details and updates the GUI.
        if (projectManager != null) {
            projectManager.loadProjects("projects.txt");
        }
    }

    @FXML
    private void handleLoadProject() { // Loads a specific project's details by projectID into the GUI,abstraction.
        String projectID = projectIDField.getText();
        Project project = projectManager.getProjectByID(projectID);
        if (project != null) {
            projectNameField.setText(project.getProjectName());
            categoryField.setText(project.getCategory());
            teamMembersField.setText(project.getTeamMembers());
            descriptionField.setText(project.getDescription());
            countryField.setText(project.getCountry());
            teamLogoField.setText(project.getTeamLogo());
        } else {
            showAlert("Error", "Project Not Found", "No existing project found with the projectID: " + projectID);
        }
    }

    @FXML
    private void handleSelectImage() {  //opens a dialog box to select an image file from the device.
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Team Logo");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        Stage stage = (Stage) projectIDField.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            teamLogoField.setText(file.getAbsolutePath());
        }
    }

    @FXML
    private void handleUpdateProject() {  // Updates the project details within the project manager,showcasing encapsulation.
        String projectID = projectIDField.getText();
        Project project = projectManager.getProjectByID(projectID);
        if (project != null) {
            project.setProjectName(projectNameField.getText());
            project.setCategory(categoryField.getText());
            project.setTeamMembers(teamMembersField.getText());
            project.setDescription(descriptionField.getText());
            project.setCountry(countryField.getText());
            project.setTeamLogo(teamLogoField.getText());
            projectManager.saveProjects("projects.txt");

            showAlert("Success", "Project has Updated", "Project updated successfully with the projectID: " + projectID);

            Stage stage = (Stage) projectIDField.getScene().getWindow();
            stage.close();
        } else {
            showAlert("Error", "Project Not Found", "No existing project/s found with the projectID: " + projectID);
        }
    }

    private void showAlert(String title, String header, String content) { // Displaying an alert to the user.
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void handleBackToMainMenu() { // Closes the updating project details window.
        Stage stage = (Stage) projectIDField.getScene().getWindow();
        stage.close();
    }
}

