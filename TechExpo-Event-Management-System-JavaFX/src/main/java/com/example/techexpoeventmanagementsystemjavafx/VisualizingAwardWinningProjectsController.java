package com.example.techexpoeventmanagementsystemjavafx;


import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import java.util.List;

public class VisualizingAwardWinningProjectsController {
    // BarChart and NumberAxis for displaying project scores graphically.
    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private NumberAxis numberAxis;
    // List of project details that are award winners.
    private List<Project> awardWinningProjects;
    // Setter to inject the list of award-winning projects.
    public void setAwardWinningProjects(List<Project> awardWinningProjects) {
        this.awardWinningProjects = awardWinningProjects;
        displayAwardWinningProjects();
    }
    // Displays projects in a bar chart, encapsulating data visualization.
    private void displayAwardWinningProjects() {
        if (awardWinningProjects == null || awardWinningProjects.isEmpty()) {
            System.out.println("No award-winning projects to display.");
            return;
        }

        barChart.getData().clear();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Visualizing Award Winning Projects");

        int maximumScore = 0;

        for (Project project : awardWinningProjects) {
            String label = project.getProjectID() + " (" + project.getCountry() + ")";
            int score = project.getScore();
            if (score > maximumScore) {
                maximumScore = score;
            }

            series.getData().add(new XYChart.Data<>(label, score));

            System.out.println("Project ID: " + project.getProjectID()  + ", Country: " + project.getCountry() + ", Score: " + project.getScore());
        }

        // Adds a new series to the bar chart.
        barChart.getData().add(series);

        // Set the y-axis to display scores and adjust the range based on the maximum score.
        numberAxis.setAutoRanging(false);
        numberAxis.setLowerBound(0);
        numberAxis.setUpperBound(maximumScore + 10); // Uses a higher value to make the chart graphically  clear.
        numberAxis.setTickUnit(20); //can adjust this with the range of score.
    }

    @FXML
    private void handleBackToMainMenu() {   // Closes the visualizing award-winning projects window.
        Stage stage = (Stage) barChart.getScene().getWindow();
        stage.close();
    }
}

