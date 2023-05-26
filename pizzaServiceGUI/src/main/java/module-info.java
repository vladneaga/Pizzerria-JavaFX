module com.example.pizzaservicegui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.pizzaservicegui to javafx.fxml;
    exports com.example.pizzaservicegui;
    exports com.example.pizzaservicegui.pizzaDelivery;
    opens com.example.pizzaservicegui.pizzaDelivery to javafx.fxml;
}