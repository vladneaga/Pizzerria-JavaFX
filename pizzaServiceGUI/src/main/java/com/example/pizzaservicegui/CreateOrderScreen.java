package com.example.pizzaservicegui;

import com.example.pizzaservicegui.pizzaDelivery.SimplePizzaDeliveryService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;



import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Screen to create an order in the PizzaDeliveryService.
 */

public class CreateOrderScreen extends AnchorPane implements Initializable {
    PizzaDeliveryScreenController screenController;

    public static final String SCREEN_NAME = "CreateOrderScreen";
    @FXML
    Button button;


    public CreateOrderScreen(PizzaDeliveryScreenController screenController) throws IOException {
        //viewModel erstellen;
        this.screenController = screenController;

    /*button.setOnAction(e -> {screenController.switchTo(CreateOrderScreen.SCREEN_NAME,
            ShowOrderScreen.SCREEN_NAME);});*/
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/pizzaservicegui/createOrder.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();


    }
    public void createOrder() throws IOException {
        SingletonAttributeStore store = SingletonAttributeStore.getInstance();
        SimplePizzaDeliveryService service = (SimplePizzaDeliveryService) store.getAttribute("PizzaDeliveryService");
        store.setAttribute("orderId", service.createOrder());

        System.out.println("Order id = " + store.getAttribute("orderId"));
        System.out.println(service.getOrder((Integer) store.getAttribute("orderId")).getValue());

        System.out.println();

        this.screenController.switchTo(CreateOrderScreen.SCREEN_NAME,
                ShowOrderScreen.SCREEN_NAME);


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        button.setOnAction(event -> {
            try {
                createOrder();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }
    // public CreateOrderScreen(){}
}