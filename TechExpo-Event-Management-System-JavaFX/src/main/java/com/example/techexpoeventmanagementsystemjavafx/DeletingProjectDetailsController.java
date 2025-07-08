package com.example.techexpoeventmanagementsystemjavafx;


import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DeletingProjectDetailsController {
    @FXML
    private TextField deleteProjectByIDField;
    // Reference to manage projects.
    private ProjectManager projectManager;
    // Setter for dependency injection,showing encapsulation.
    public void setProjectManager(ProjectManager projectManager) {
        this.projectManager = projectManager;
        initializeProjectManager();
    }

    @FXML
    private void initializeProjectManager() { //loads projects for management.data encapsulation and initialization.
        if (projectManager != null) {
            projectManager.loadProjects("projects.txt");
        }
    }

    @FXML
    public void handleDeleteProject() { // Handles the deletion of a project by projectID.
        String projectID = deleteProjectByIDField.getText().trim();
        if (projectManager.deleteProjectByID(projectID)) {
            projectManager.saveProjects("projects.txt");
            showAlert(AlertType.INFORMATION, "Success", "Project deleted successfully.");
            Stage stage = (Stage) deleteProjectByIDField.getScene().getWindow();
            stage.close();
        } else {
            // Alerts in the GUI when a project ID does not exist.
            showAlert(AlertType.ERROR, "Error", "Project with ID " + projectID + " does not exist.");
        }
    }

    private void showAlert(AlertType alertType, String title, String message) {  //method to show alerts.
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleBackToMainMenu() {  // Close the current deleting project details window.
        Stage stage = (Stage) deleteProjectByIDField.getScene().getWindow();
        stage.close();
    }
}

