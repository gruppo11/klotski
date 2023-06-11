module klotski.klotski {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens klotski.klotski to javafx.fxml;
    exports klotski.klotski;
}