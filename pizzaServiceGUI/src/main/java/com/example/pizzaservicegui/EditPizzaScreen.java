package com.example.pizzaservicegui;


import com.example.pizzaservicegui.pizzaDelivery.SimpleOrder;
import com.example.pizzaservicegui.pizzaDelivery.SimplePizza;
import com.example.pizzaservicegui.pizzaDelivery.SimplePizzaDeliveryService;
import com.example.pizzaservicegui.pizzaDelivery.Topping;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


import java.io.IOException;

/**
 * Screen to edit the toppings on a pizza of the PizzaDeliveryService.
 */
public class EditPizzaScreen extends VBox {


    public static final String SCREEN_NAME = "EditPizzaScreen";
    Label pizzaSizeLabel;

    Label priceLabel;
    ChoiceBox<Topping> toppingChoiceBox;
    Button addToppingButton;
    ListView<Topping> toppingsOnPizzaListView;

    Button finishButton;
    SimplePizza pizza;

    public EditPizzaScreen(PizzaDeliveryScreenController screenController) {






        SingletonAttributeStore store = SingletonAttributeStore.getInstance();
        int pizzaId = (Integer) store.getAttribute("pizzaId");
        System.out.println("Pizza id = " + pizzaId);
        SimplePizzaDeliveryService service = (SimplePizzaDeliveryService) store.getAttribute("PizzaDeliveryService");
        int orderId = (int) store.getAttribute("orderId");
        SimpleOrder simpleOrder = (SimpleOrder) service.getOrder(orderId);

        System.out.println("Pizzas in order nr." + store.getAttribute("orderId"));
        service.getOrder(orderId).getPizzaList().stream().forEach(pizza1 -> System.out.println(pizza1.getPizzaId()));
        pizza = (SimplePizza) service.getOrder(orderId).getPizzaList().stream().filter(pizza1 -> pizza1.getPizzaId() == pizzaId).findAny().get();

        this.pizzaSizeLabel = new Label(pizza.getSize().toString());
        this.priceLabel = new Label();
        StringProperty priceProperty = new SimpleStringProperty(Integer.toString(pizza.getPrice()));

        priceLabel.textProperty().bindBidirectional(priceProperty);
        Bindings.bindBidirectional( priceLabel.textProperty(), priceProperty);

        this.toppingChoiceBox = new ChoiceBox<>();
        for (Topping topping: Topping.values())    {
            this.toppingChoiceBox.getItems().add(topping);
        }
        this.addToppingButton = new Button("Add Topping.");
        addToppingButton.setOnAction(event -> {
            if(pizza.getToppings().stream().noneMatch(topping -> topping == toppingChoiceBox.getValue()) &&
                    (long) pizza.getToppings().size() !=6 && toppingChoiceBox.getValue() != null)
                toppingsOnPizzaListView.getItems().add(toppingChoiceBox.getValue());
            System.out.println(pizza.getPrice());
            pizza.getToppings().forEach(topping -> System.out.println(topping.toString()));
            priceProperty.setValue(Integer.toString(pizza.getPrice())); // ????????????
        });
        ObservableList<Topping> observableList = FXCollections.observableList(pizza.getToppings());
        toppingsOnPizzaListView = new ListView<>();
        toppingsOnPizzaListView.setItems(observableList);
        toppingsOnPizzaListView.setCellFactory(list -> new ToppingListCell(observableList, priceProperty, store));

        this.finishButton = new Button("Finish");
        this.finishButton.setOnAction(event -> {
            store.removeAttribute("pizzaId");
            // store.removeAttribute("orderId");
            try {
                screenController.switchTo(SCREEN_NAME, ShowOrderScreen.SCREEN_NAME);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        HBox hBox = new HBox();
        hBox.getChildren().addAll(toppingsOnPizzaListView, finishButton);
        this.getChildren().addAll(pizzaSizeLabel, priceLabel, toppingChoiceBox, addToppingButton, hBox);
    }

    static class ToppingListCell extends ListCell<Topping>  {
        private final ObservableList<Topping> observableList;
        StringProperty priceProperty;
        SingletonAttributeStore store;
        public ToppingListCell(final ObservableList<Topping> observableList, StringProperty priceProperty, SingletonAttributeStore store) {
            this.priceProperty = priceProperty;
            this.observableList = observableList;
            this.store = store;
        }
        @Override
        protected void updateItem(final Topping topping, boolean empty) {
            super.updateItem(topping, empty);
            if (empty || topping == null) {
                textProperty().setValue(null);
                setGraphic(null);
            } else {
                HBox verticalBox = new HBox();
                Label name = new Label(topping.toString());
                Button removeButton = new Button("Remove");
                removeButton.setOnAction(actionEvent -> {observableList.remove(topping);
                            SimplePizzaDeliveryService service = (SimplePizzaDeliveryService) store.getAttribute("PizzaDeliveryService");
                            priceProperty.setValue(String.valueOf(service.getPizza((Integer) store.getAttribute("orderId"),
                                    (Integer) store.getAttribute("pizzaId")).getPrice()));
                        }
                );
                Pane spacer = new Pane();
                spacer.setMinSize(100, 1);
                verticalBox.getChildren().addAll(name, spacer, removeButton);
                setGraphic(verticalBox);

            }



        }
    }



}
