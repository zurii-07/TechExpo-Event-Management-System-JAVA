package com.example.techexpoeventmanagementsystemjavafx;


import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;

public class ViewingProjectDetailsController {
    @FXML
    private TableView<Project> projectTable;
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
    // Refers project manager to access project data.
    private ProjectManager projectManager;
    // Injects project manager and initialize data, using encapsulation and dependency injection.
    public void setProjectManager(ProjectManager projectManager) {
        this.projectManager = projectManager;
        initializeProjectManager();
    }

    @FXML
    private void initializeProjectManager() {  //initializes and loads existing projects from the text file and displays those in a table.
        if (projectManager != null) {
            projectManager.loadProjects("projects.txt");
            projectManager.sortProjectsByID();
            ObservableList<Project> projectList = FXCollections.observableArrayList(projectManager.getProjects());

            projectIDColumn.setCellValueFactory(new PropertyValueFactory<>("projectID"));
            projectNameColumn.setCellValueFactory(new PropertyValueFactory<>("projectName"));
            categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
            teamMembersColumn.setCellValueFactory(new PropertyValueFactory<>("teamMembers"));
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
            countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
            teamLogoColumn.setCellValueFactory(new PropertyValueFactory<>("teamLogo"));

            teamLogoColumn.setCellFactory(new Callback<>() {  //<TableColumn<Project, String>, TableCell<Project, String>>
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
                                Image image = new Image(new File(item).toURI().toString());
                                imageView.setImage(image);
                                imageView.setFitHeight(50);
                                imageView.setFitWidth(50);
                                setGraphic(imageView);
                            }
                        }
                    };
                }
            });


            projectTable.setItems(projectList);
        }
    }

    @FXML
    private void handleBackToMainMenu() {  // Closes the current viewing project details Window.
        Stage stage = (Stage) projectTable.getScene().getWindow();
        stage.close();
    }
}

