package com.example.techexpoeventmanagementsystemjavafx;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import java.io.File;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class AwardWinningProjectsController {
    // TableView and TableColumn for displaying project details and their scores.
    @FXML
    private TableView<Project> awardWinningProjectsTable;
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
    @FXML
    private TableColumn<Project, Integer> scoreColumn;
    @FXML
    private TableColumn<Project, VBox> judgeScoresColumn;
    // referring project manager to manage project data
    private ProjectManager projectManager;
    // Sets the project manager to load the project details from RSS.
    public void setProjectManager(ProjectManager projectManager) {
        this.projectManager = projectManager;
        loadProjects();
    }
    // Loads random spotlight projects to calculate award winners.
    public void setSpotlightProjects(List<Project> spotlightProjects) {
        loadSpotlightProjects(spotlightProjects);
    }

    @FXML
    private void initialize() {  // Initialize the table and filling it with project details from the project manager.
        projectIDColumn.setCellValueFactory(new PropertyValueFactory<>("projectID"));
        projectNameColumn.setCellValueFactory(new PropertyValueFactory<>("projectName"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        teamMembersColumn.setCellValueFactory(new PropertyValueFactory<>("teamMembers"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        teamLogoColumn.setCellValueFactory(new PropertyValueFactory<>("teamLogo"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        judgeScoresColumn.setCellValueFactory(new PropertyValueFactory<>("judgeScoresVBox"));

        teamLogoColumn.setCellFactory(new Callback<>() {
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

    private void loadProjects() { // Load projects into the table, encapsulating data fetching and updating the GUI.
        if (projectManager != null) {
            projectManager.loadProjects("projects.txt");
            awardWinningProjectsTable.getItems().setAll(projectManager.getProjects());
        }
    }

    private void loadSpotlightProjects(List<Project> spotlightProjects) {
        ObservableList<Project> projectList = FXCollections.observableArrayList(spotlightProjects);
        awardWinningProjectsTable.setItems(projectList);

        for (Project project : spotlightProjects) {
            VBox judgeScoresVBox = new VBox(5);
            for (int i = 0; i < 4; i++) {
                HBox stars = handleStarRating();
                judgeScoresVBox.getChildren().add(stars);
            }
            project.setJudgeScoresVBox(judgeScoresVBox);
        }
    }

    private HBox handleStarRating() {
        HBox starBox = new HBox(2);
        ToggleGroup group = new ToggleGroup();
        for (int i = 1; i <= 5; i++) {
            RadioButton star = new RadioButton();
            star.setToggleGroup(group);
            star.setUserData(i);
            starBox.getChildren().add(star);
        }
        return starBox;
    }

    @FXML
    private void handleJudgeScores() { //Handles scoring based on judges input and updates the project scores.
        List<Project> projects = awardWinningProjectsTable.getItems();

        for (Project project : projects) {
            int totalScore = 0;
            VBox judgeScoresVBox = (VBox) project.getJudgeScoresVBox();
            for (int i = 0; i < 4; i++) {
                HBox starBox = (HBox) judgeScoresVBox.getChildren().get(i);
                for (javafx.scene.Node node : starBox.getChildren()) {
                    RadioButton star = (RadioButton) node;
                    if (star.isSelected()) {
                        totalScore += (int) star.getUserData();
                        break;
                    }
                }
            }
            project.setScore(totalScore);
            System.out.println("Updated Score for Project ID: " + project.getProjectID() + " is " + totalScore);
        }

        projectManager.saveProjects("projects.txt");
        awardWinningProjectsTable.refresh();
    }

    public List<Project> getAwardWinningProjects() {
        List<Project> projects = awardWinningProjectsTable.getItems();
        return projects.subList(0, Math.min(3, projects.size()));  // Returns only the top 3 projects.
    }

    @FXML
    public void calculateAwardWinners() {  // Calculates the award winners based on scores,showing sorting.
        List<Project> projects = awardWinningProjectsTable.getItems();
        projects.sort((p1, p2) -> Integer.compare(p2.getScore(), p1.getScore()));

        if (projects.size() > 0) {
            Project firstPlace = projects.get(0);
            firstPlace.setCategory("1st Place");
            System.out.println("1st Place: " + firstPlace.getProjectID() + " with Score: " + firstPlace.getScore());
        }
        if (projects.size() > 1) {
            Project secondPlace = projects.get(1);
            secondPlace.setCategory("2nd Place");
            System.out.println("2nd Place: " + secondPlace.getProjectID() + " with Score: " + secondPlace.getScore());
        }
        if (projects.size() > 2) {
            Project thirdPlace = projects.get(2);
            thirdPlace.setCategory("3rd Place");
            System.out.println("3rd Place: " + thirdPlace.getProjectID() + " with Score: " + thirdPlace.getScore());
        }

        projectManager.saveProjects("projects.txt");
        awardWinningProjectsTable.refresh();

        for (Project project : projects) {
            System.out.println("Project ID: " + project.getProjectID() + ", Score: " + project.getScore());
        }
    }

    @FXML
    private void handleBackToMainMenu() {   // Closes the current award-winning projects window.
        Stage stage = (Stage) awardWinningProjectsTable.getScene().getWindow();
        stage.close();
    }
}

