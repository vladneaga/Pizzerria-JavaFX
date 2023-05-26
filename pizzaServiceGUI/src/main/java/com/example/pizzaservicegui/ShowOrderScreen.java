package com.example.pizzaservicegui;

import com.example.pizzaservicegui.pizzaDelivery.PizzaSize;
import com.example.pizzaservicegui.pizzaDelivery.SimplePizza;
import com.example.pizzaservicegui.pizzaDelivery.SimplePizzaDeliveryService;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * Screen to show the list of pizzas of an order of the PizzaDeliveryService. It is also possible
 * to add, change and remove pizzas.
 */
public class ShowOrderScreen extends VBox implements Initializable {


    public static final String SCREEN_NAME = "ShowOrderScreen";
    @FXML
    Label orderLabel;
    IntegerProperty orderProperty;
    @FXML
    Label priceLabel;
    IntegerProperty priceProperty;
    @FXML
    Button cancelButton;
    @FXML
    ChoiceBox<PizzaSize> sizeChoiceBox;
    @FXML
    Button addPizzaButton;
    @FXML
    ListView<SimplePizza> pizzaListView;

    ObservableList<SimplePizza> pizzaObservableList;
    @FXML
    Button orderButton;

    PizzaDeliveryScreenController screenController;
    SimplePizzaDeliveryService service;
    SingletonAttributeStore store;
    int orderId;
//public ShowOrderScreen(){}

    public ShowOrderScreen(PizzaDeliveryScreenController screenController) {


        this.screenController = screenController;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/pizzaservicegui/showOrder.fxml"));
        loader.setController(this);
        loader.setRoot(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public void test(){
        orderId = (int) store.getAttribute("orderId");
        //pizzaObservableList.clear();
        pizzaObservableList = FXCollections.observableList(service.getOrder(orderId).getPizzaList());

        pizzaListView.setItems(pizzaObservableList);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.service =  (SimplePizzaDeliveryService) SingletonAttributeStore.getInstance().getAttribute("PizzaDeliveryService");

        store = SingletonAttributeStore.getInstance();
        test();
        orderId = (int) store.getAttribute("orderId");
        orderProperty = new SimpleIntegerProperty();
        orderProperty.setValue(service.getOrder(orderId).getOrderId());
        orderLabel.textProperty().bind(orderProperty.asString());


        priceProperty = new SimpleIntegerProperty();
        priceProperty.setValue(service.getOrder(orderId).getValue());
        priceLabel.textProperty().bind(priceProperty.asString());


        cancelButton.setOnAction(e -> {
            try {

                store.removeAttribute("orderId");
                store.removeAttribute("pizzaId");

                pizzaListView.getItems().clear();
                pizzaObservableList.clear();
                priceProperty.setValue(0);
                orderProperty.setValue(0);

                this.screenController.switchTo(SCREEN_NAME, CreateOrderScreen.SCREEN_NAME);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });


        sizeChoiceBox.setId("sizeChoiceBox");
        for (PizzaSize size : PizzaSize.values()) {
            this.sizeChoiceBox.getItems().add(size);
        }


        addPizzaButton.setOnAction(this::addPizzaAction);

        pizzaObservableList = FXCollections.observableList(service.getOrder(orderId).getPizzaList());
        pizzaListView.setItems(pizzaObservableList);
        pizzaListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(SimplePizza item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    HBox hBox = new HBox();
                    Button changeButton = new Button("change");
                    changeButton.setOnAction(e-> {
                        try {
                            store.setAttribute("pizzaId", item.getPizzaId());
                            orderId = (int) store.getAttribute("orderId");
                            System.out.println("Order id when click change = " + orderId);
                            System.out.println("Pizza id when click change = " + item.getPizzaId());
                            System.out.println("Pizza id from store = " + store.getAttribute("pizzaId"));
                            screenController.switchTo(SCREEN_NAME, EditPizzaScreen.SCREEN_NAME);

                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    });
                    Button removeButton = new Button("remove");
                    removeButton.setOnAction(e->{
                        SimplePizza pizza = getItem();
                        //service.getOrder(orderId).getPizzaList().remove(pizza);
                        //pizzaObservableList.remove(pizza);
                        priceProperty.setValue(service.getOrder(orderId).getValue());
                        orderProperty.setValue(service.getOrder(orderId).getOrderId());

                        pizzaListView.getItems().remove(pizza);
                        sync();



                    } );

                    Label text = new Label();
                    text.setText(item.getSize().toString() + ", " + (long) item.getToppings().size() + " Toppings    " );
                    hBox.getChildren().addAll(text, changeButton, removeButton);
                    setText(null);
                    setGraphic(hBox);


                }
            }
        });

        orderButton.setOnAction(event ->  {store.removeAttribute("orderId");
            try {
                screenController.switchTo(SCREEN_NAME, CreateOrderScreen.SCREEN_NAME);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        this.sync();
    }
    public void addPizzaAction (ActionEvent event) {
        if (sizeChoiceBox.getValue() != null) {


            orderId = (int) store.getAttribute("orderId");
            int pizzaId = service.addPizza(orderId, sizeChoiceBox.getValue());
            //pizzaObservableList.add(service.getPizza(orderId, pizzaId));
            System.out.println("showOrd pizzaid=" + pizzaId);

            priceProperty.setValue(service.getOrder(orderId).getValue());
            orderProperty.setValue(service.getOrder(orderId).getOrderId());

            store.setAttribute("pizzaId", pizzaId);

            System.out.println(service.getOrder(orderId).getValue());

            try {
                screenController.switchTo(SCREEN_NAME, EditPizzaScreen.SCREEN_NAME);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void sync() {
        orderId = (int) store.getAttribute("orderId");
        System.out.println("sync : order id = " + orderId);
        priceProperty.setValue(service.getOrder(orderId).getValue());
        orderProperty.setValue(service.getOrder(orderId).getOrderId());

        pizzaObservableList = FXCollections.observableArrayList(service.getOrder((Integer) store.getAttribute("orderId")).getPizzaList());


        System.out.print("Pizzas in Observable List: ");
        pizzaObservableList.stream().forEach(pizza -> System.out.print(pizza.getPizzaId() + " "));
        System.out.println();





    }
}

