module com.example.techexpoeventmanagementsystemjavafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.techexpoeventmanagementsystemjavafx to javafx.fxml;
    exports com.example.techexpoeventmanagementsystemjavafx;
}