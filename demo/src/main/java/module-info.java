module lab.example {
    requires transitive javafx.controls;
    requires javafx.fxml;

    opens lab.example to javafx.fxml;
    exports lab.example;
}
