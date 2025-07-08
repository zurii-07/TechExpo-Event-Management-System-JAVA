package com.example.techexpoeventmanagementsystemjavafx;


import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.control.TableCell;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RandomSpotlightSelectionController {
    @FXML
    private TableView<Project> randomSpotlightSelectionDetailsTable;
    @FXML
    private TableColumn<Project, String> projectIDColumn;
    @FXML
    private TableColumn<Project, String> projectNameColumn;
    @FXML
    private TableColumn<Project, String> categoryColumn;
    @FXML
    private TableColumn<Project, String> teamMembersColumn;
    @FXML
    private TableColumn<Project, String> descriptionColumn;
    @FXML
    private TableColumn<Project, String> countryColumn;
    @FXML
    private TableColumn<Project, String> teamLogoColumn;
    // References to the project manager.
    private ProjectManager projectManager;
    // List to store selected random spotlight projects.
    private final List<Project> spotlightProjects = new ArrayList<>();
    // Dependency injection of the project manager.
    public void setProjectManager(ProjectManager projectManager) {
        this.projectManager = projectManager;
        initializeProjectManager();
    }


    @FXML
    private void initializeProjectManager() { // Loads projects from the text file.
        if (projectManager != null) {
            projectManager.loadProjects("projects.txt");
        }
    }

    @FXML
    private void initialize() {
        projectIDColumn.setCellValueFactory(new PropertyValueFactory<>("projectID"));
        projectNameColumn.setCellValueFactory(new PropertyValueFactory<>("projectName"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        teamMembersColumn.setCellValueFactory(new PropertyValueFactory<>("teamMembers"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        teamLogoColumn.setCellValueFactory(new PropertyValueFactory<>("teamLogo"));

        // Custom cell factory for team logo column
        teamLogoColumn.setCellFactory(new Callback<>() { //TableColumn<Project, String>, TableCell<Project, String>
            @Override
            public TableCell<Project, String> call(TableColumn<Project, String> param) {
                return new TableCell<>() {
                    private final ImageView imageView = new ImageView();

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setGraphic(null);
                        } else {
                            File logoFile = new File(item);
                            if (logoFile.exists()) {
                                Image image = new Image(logoFile.toURI().toString());
                                imageView.setImage(image);
                                imageView.setFitHeight(50);
                                imageView.setFitWidth(50);
                                setGraphic(imageView);
                            } else {
                                setGraphic(null);
                            }
                        }
                    }
                };
            }
        });
    }

    @FXML
    public void handleRandomSpotlightSelection() {  // Method to handle random selection of projects based on their categories.
        Map<String, List<Project>> categoryMap = new HashMap<>();

        for (Project project : projectManager.getProjects()) {
            categoryMap.computeIfAbsent(project.getCategory(), k -> new ArrayList<>()).add(project);
        }

        Random rand = new Random();
        spotlightProjects.clear();

        for (Map.Entry<String, List<Project>> entry : categoryMap.entrySet()) {
            List<Project> projects = entry.getValue();
            if (!projects.isEmpty()) {
                Project selectedProject = projects.get(rand.nextInt(projects.size()));
                spotlightProjects.add(selectedProject);
            }
        }

        randomSpotlightSelectionDetailsTable.getItems().setAll(spotlightProjects);
    }

    // Returns the list of randomly selected spotlight projects,data encapsulation.
    public List<Project> getRandomSpotlightProjects() {
        return spotlightProjects;
    }

    @FXML
    private void handleBackToMainMenu() { // Closes the random spotlight selection window.
        Stage stage = (Stage) randomSpotlightSelectionDetailsTable.getScene().getWindow();
        stage.close();
    }
}

