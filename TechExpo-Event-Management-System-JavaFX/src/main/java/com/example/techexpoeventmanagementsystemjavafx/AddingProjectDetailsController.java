package com.example.techexpoeventmanagementsystemjavafx;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import javafx.scene.control.Alert;

public class AddingProjectDetailsController {
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

    private ProjectManager projectManager ;  // Referring to manage projects
    // Setter for dependency injection of the project manager
    public void setProjectManager(ProjectManager projectManager) {
        this.projectManager = projectManager;
    }

    @FXML
    private void handleSelectImage() {  // Opens a dialog box to select an image file from the device,uses encapsulation and abstraction.
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Team Logo");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));

        Stage stage = (Stage) projectIDField.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            teamLogoField.setText(file.getAbsolutePath());
        }
    }

    @FXML
    private void handleAddProject() {  // Adds a new project to the project manager,showing data encapsulation and abstraction.
        String projectID = projectIDField.getText();
        String projectName = projectNameField.getText();
        String category = categoryField.getText();
        String teamMembers = teamMembersField.getText();
        String description = descriptionField.getText();
        String country = countryField.getText();
        String teamLogo = teamLogoField.getText();
        //validated all the fields as required.
        if (projectID.isEmpty() || projectName.isEmpty() || category.isEmpty() ||
                teamMembers.isEmpty() || description.isEmpty() || country.isEmpty()) {
            showAlert("Error", "Missing Information", "All fields must be filled out.");
            return;
        }

        // Validate image path
        File logoFile = new File(teamLogo);
        if (!logoFile.exists() || !(teamLogo.endsWith(".png") || teamLogo.endsWith(".jpg") || teamLogo.endsWith(".jpeg"))) {
            showAlert("Error", "Invalid Image Path", "Please select a valid image file.");
            return;
        }

        if (projectManager.projectExists(projectID)) {
            showAlert("Error", "Project ID already exists!", "Please use an un-entered Project ID.");
            return;
        }

        Project project = new Project(projectID, projectName, category, teamMembers, description, country, teamLogo);
        projectManager.addProject(project);
        projectManager.saveProjects("projects.txt");

        showAlert("Success", "Project Added", "Project Successfully added with the projectID: " + projectID);

        Stage stage = (Stage) projectIDField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String header, String content) { //displays alert messages on the GUI for different handling.
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void handleBackToMainMenu() { // Closes the current window or stage.
        Stage stage = (Stage) projectIDField.getScene().getWindow();
        stage.close();
    }
}