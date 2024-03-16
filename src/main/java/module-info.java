module org.example.sortinganimations {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.sortinganimations to javafx.fxml;
    exports org.example.sortinganimations;
}