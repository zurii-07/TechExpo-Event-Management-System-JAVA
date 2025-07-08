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

}