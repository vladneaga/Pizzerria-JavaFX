package com.example.pizzaservicegui;

import com.example.pizzaservicegui.pizzaDelivery.SimplePizzaDeliveryService;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(final Stage stage) throws Exception {

        TabPane root = new TabPane();
        Tab welcomeTab = new Tab("Welcome", new Label("Welcome to our pizzeria!!!"));
        root.getTabs().add(welcomeTab);

        // System.out.println("here");

        SingletonAttributeStore attributeStore = SingletonAttributeStore.getInstance();
        SimplePizzaDeliveryService service = new SimplePizzaDeliveryService();
        attributeStore.setAttribute("PizzaDeliveryService", service);
        Pane paneToBeFilled = new Pane();
        //System.out.println(88);
        paneToBeFilled.getChildren().add(new Label("Your advertisement could be here ..."));
        Tab pizzaDeliveryTab = new Tab("PizzaDeliveryService", paneToBeFilled);
        PizzaDeliveryScreenController controller = new PizzaDeliveryScreenController(paneToBeFilled);
        controller.switchTo(null, CreateOrderScreen.SCREEN_NAME);
        root.getTabs().add(pizzaDeliveryTab);

        stage.setTitle("Pizzeria GUI");
        stage.setScene(new Scene(root));
        stage.show();

    }



    public static void main(String[] args) {
        launch();
    }
}